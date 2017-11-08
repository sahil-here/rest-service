package vendi.rest.resources.manager;

import vendi.exception.VendiException;
import vendi.request.ChangePasswordRequest;
import vendi.request.UserLoginRequest;
import vendi.request.VendorSignupRequest;
import vendi.response.ApplicationResponse;
import vendi.response.FELoginResponse;
import vendi.response.VendorLoginResponse;
import vendi.rest.dao.entity.Vendor;

public interface ILoginManager {

	VendorLoginResponse vendorLogin(UserLoginRequest request) throws VendiException;

	FELoginResponse feLogin(UserLoginRequest request) throws VendiException;

	ApplicationResponse feChangePassword(ChangePasswordRequest request) throws VendiException;

	Vendor vendorSignup(VendorSignupRequest vendorSignupRequest) throws VendiException;

}