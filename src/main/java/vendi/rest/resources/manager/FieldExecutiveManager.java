package vendi.rest.resources.manager;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import vendi.entity.FieldExecutiveDetails;
import vendi.entity.UserType;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.CreateFieldExecutiveRequest;
import vendi.request.EditFieldExecutiveRequest;
import vendi.response.ApplicationResponse;
import vendi.rest.dao.IFieldExecutiveDAO;
import vendi.rest.dao.entity.FieldExecutive;
import vendi.util.PasswordHash;

public class FieldExecutiveManager implements IFieldExecutiveManager {
	
	private static final Logger logger = LoggerFactory.getLogger(FieldExecutiveManager.class);

	@Inject
	protected IFieldExecutiveDAO fieldExecutiveDAO;

	@Override
	public FieldExecutiveDetails createFieldExecutive(CreateFieldExecutiveRequest feRequest) throws VendiException {
		FieldExecutiveDetails feDetail = new FieldExecutiveDetails();
		try{
			FieldExecutive feAlreadyCreated = fieldExecutiveDAO.idempotencyCheck(feRequest);
			if(null == feAlreadyCreated){
				FieldExecutive fieldExecutive = fieldExecutiveDAO.findFieldExecutiveByUsername(feRequest.getUsername());
				if(null == fieldExecutive){
					FieldExecutive fe = new FieldExecutive();
					fe.setContact(feRequest.getContact());
					fe.setName(feRequest.getName());
					fe.setPassword(PasswordHash.createHash(feRequest.getPassword()));
					fe.setVendorId(feRequest.getVendorId());
					fe.setUsername(feRequest.getUsername());
					fe.setIsEnabled(Boolean.TRUE);
					fe.setUpdatedBy(UserType.VENDOR);
					fe.setCreatedAt(feRequest.getCreateTime());
					fe.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
					BeanUtils.copyProperties(feDetail, fieldExecutiveDAO.createOrUpdate(fe));
					return feDetail;
				}else{
					logger.error("Username " + fieldExecutive.getUsername() + " already exists.");
					throw new VendiException(400,"Username " + fieldExecutive.getUsername() + " already exists.");
				}
			}
			BeanUtils.copyProperties(feDetail, feAlreadyCreated);
			return feDetail;
		}catch(NoSuchAlgorithmException | InvalidKeySpecException | IllegalAccessException | InvocationTargetException ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public ApplicationResponse editFieldExecutive(EditFieldExecutiveRequest feRequest) throws VendiException {
		ApplicationResponse response = new ApplicationResponse();
		FieldExecutive fieldExecutive = fieldExecutiveDAO.findFieldExecutiveByUsername(feRequest.getUsername());
		if(null != fieldExecutive){
			if(StringUtils.isNotEmpty(feRequest.getContact()))
				fieldExecutive.setContact(feRequest.getContact());
			if(null != feRequest.getIsEnabled() && UserType.VENDOR.equals(feRequest.getUserType()))
				fieldExecutive.setIsEnabled(feRequest.getIsEnabled());
			if(StringUtils.isNotEmpty(feRequest.getName()))
				fieldExecutive.setName(feRequest.getName());
			if(StringUtils.isNotEmpty(feRequest.getPassword()) && UserType.VENDOR.equals(feRequest.getUserType())){
				try {
					fieldExecutive.setPassword(PasswordHash.createHash(feRequest.getPassword()));
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),e);
				}
			}
			fieldExecutive.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
			fieldExecutive.setUpdatedBy(feRequest.getUserType());
			fieldExecutiveDAO.createOrUpdate(fieldExecutive);
			response.setSuccess(true);
			response.setMessage("Successfully updated FieldExecutive Details.");
			return response;
		}
		logger.info("FieldExecutive with username " + feRequest.getUsername() + " not found");
		response.setSuccess(false);
		response.setMessage("FieldExecutive with username " + feRequest.getUsername() + " not found");
		return response;
	}

	@Override
	public List<FieldExecutiveDetails> getFieldExecutivesForVendor(String vendorId, Boolean isEnabled) throws VendiException {
		try{
			List<FieldExecutive> fieldExecutives =  fieldExecutiveDAO.findAllFieldExecutiveByVendorId(vendorId, isEnabled);
			List<FieldExecutiveDetails> feDetails = new ArrayList<FieldExecutiveDetails>();
			for(FieldExecutive fe : fieldExecutives){
				FieldExecutiveDetails feDetail = new FieldExecutiveDetails();
				BeanUtils.copyProperties(feDetail, fe);
				feDetails.add(feDetail);
			}
			return feDetails;
		}catch(IllegalAccessException | InvocationTargetException ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}	
	}
}