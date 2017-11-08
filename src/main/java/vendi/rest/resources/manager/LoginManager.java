package vendi.rest.resources.manager;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import vendi.entity.FieldExecutiveDetails;
import vendi.entity.VendorDetails;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.ChangePasswordRequest;
import vendi.request.UserLoginRequest;
import vendi.request.VendorSignupRequest;
import vendi.response.ApplicationResponse;
import vendi.response.FELoginResponse;
import vendi.response.VendorLoginResponse;
import vendi.rest.dao.IFieldExecutiveDAO;
import vendi.rest.dao.IVendorDAO;
import vendi.rest.dao.entity.FieldExecutive;
import vendi.rest.dao.entity.Vendor;
import vendi.util.PasswordHash;

public class LoginManager implements ILoginManager {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginManager.class);

	@Inject
	protected IFieldExecutiveDAO fieldExecutiveDAO;

	@Inject
	protected IVendorDAO vendorDAO;

	@Override
	public Vendor vendorSignup(VendorSignupRequest vendorSignupRequest) throws VendiException {
		Vendor vendor = new Vendor();
		try{
			Vendor existing = vendorDAO.idempotencyCheck(vendorSignupRequest);
			if(existing==null){
				vendor.setName(vendorSignupRequest.getName());
				vendor.setContact(vendorSignupRequest.getContact());
				vendor.setEmail(vendorSignupRequest.getEmail());
				vendor.setPassword(PasswordHash.createHash(vendorSignupRequest.getPassword()));
				return vendorDAO.createOrUpdate(vendor);
			}else{
				return existing;
			}
		}catch(NoSuchAlgorithmException | InvalidKeySpecException ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public VendorLoginResponse vendorLogin(UserLoginRequest request) throws VendiException {
		VendorLoginResponse response = new VendorLoginResponse();
		try{
			Boolean isValid = false;
			String passwordHash = vendorDAO.authenticateVendor(request.getUsername());
			if(!StringUtils.isEmpty(passwordHash)){
				isValid = PasswordHash.validatePassword(request.getPassword(), passwordHash);
			}
			if(!isValid){
				logger.info("Username or Password is invalid");
				response.setSuccess(false);
				response.setMessage("Username or Password is invalid");
				return response;
			}
			Vendor vendor = vendorDAO.findVendorByEmail(request.getUsername());
			VendorDetails vendorDetails = new VendorDetails();
			vendorDetails.setId(vendor.getId());
			vendorDetails.setName(vendor.getName());
			vendorDetails.setEmail(vendor.getEmail());
			vendorDetails.setContact(vendor.getContact());
			response.setVendorDetails(vendorDetails);
			response.setSuccess(Boolean.TRUE);
			response.setMessage("Username and Password are valid");
			return response;
		}catch(NoSuchAlgorithmException | InvalidKeySpecException ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public FELoginResponse feLogin(UserLoginRequest request) throws VendiException {
		FELoginResponse response = new FELoginResponse();
		try{
			Boolean isValid = false;
			String passwordHash = fieldExecutiveDAO.authenticateFieldExecutive(request.getUsername());
			if(!StringUtils.isEmpty(passwordHash)){
				isValid = PasswordHash.validatePassword(request.getPassword(), passwordHash);
			}
			if(!isValid){
				logger.info("Username or Password is invalid");
				response.setSuccess(false);
				response.setMessage("Username or Password is invalid");
				return response;
			}
			FieldExecutive fe = fieldExecutiveDAO.findFieldExecutiveByUsername(request.getUsername());
			FieldExecutiveDetails feDetail = new FieldExecutiveDetails();
			BeanUtils.copyProperties(feDetail, fe);
			response.setFeDetails(feDetail);
			response.setSuccess(true);
			response.setMessage("Username and Password are valid");
			return response;
		}catch(NoSuchAlgorithmException | InvalidKeySpecException | IllegalAccessException |  InvocationTargetException ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public ApplicationResponse feChangePassword(ChangePasswordRequest request) throws VendiException {
		ApplicationResponse response = new ApplicationResponse();
		try {
			Boolean isRetryRequest = PasswordHash.validatePassword(request.getNewPassword(), fieldExecutiveDAO.authenticateFieldExecutive(request.getUsername()));

			if (!isRetryRequest) {
				Boolean isValid = PasswordHash.validatePassword(request.getOldPassword(), fieldExecutiveDAO.authenticateFieldExecutive(request.getUsername()));
	
				if (isValid) {
					Boolean isSuccess = fieldExecutiveDAO.feChangePassword(request.getUsername(), PasswordHash.createHash(request.getNewPassword()));
					if (isSuccess) {
						response.setSuccess(true);
						response.setMessage("Password changed successfully.");
						return response;
					}
					logger.info("Password Change is not successful.");
					response.setSuccess(false);
					response.setMessage("Password Change is not successful.");
					return response;
				}
				logger.info("Invalid Old Password");
				response.setSuccess(false);
				response.setMessage("Invalid Old Password");
				return response;
			}
			logger.info("Password already updated to new password.");
			response.setSuccess(false);
			response.setMessage("Password already updated to new password.");
			return response;
		}catch(NoSuchAlgorithmException | InvalidKeySpecException ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
}
