package vendi.rest.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import io.dropwizard.hibernate.UnitOfWork;
import vendi.exception.VendiException;
import vendi.request.ChangePasswordRequest;
import vendi.request.UserLoginRequest;
import vendi.request.VendorSignupRequest;
import vendi.response.ApplicationResponse;
import vendi.response.FELoginResponse;
import vendi.response.VendorLoginResponse;
import vendi.rest.dao.entity.Vendor;
import vendi.rest.resources.manager.ILoginManager;
import vendi.rest.util.logging.Logged;
import vendi.util.BeanValidator;

@Path("vendi")
@Api(value = "login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResources {

	@Inject
	protected ILoginManager loginManager;

	private static final Logger logger = LoggerFactory.getLogger(LoginResources.class);

	@POST
	@Logged
	@Timed
	@Path("/vendors/signup")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Vendor Signup Request", response = Vendor.class)
	public Response vendorSignup(
			@ApiParam(value = "Vendor Signup Request ", required = true) VendorSignupRequest vendorSignupRequest) throws VendiException {
		BeanValidator.validate(vendorSignupRequest);
		logger.info("Vendor Signup Request: " + vendorSignupRequest);
		Vendor response = loginManager.vendorSignup(vendorSignupRequest);
		return Response.ok().entity(response).build();
	}

	@POST
	@Logged
	@Timed
	@Path("/vendors/login")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Vendor Login", response = VendorLoginResponse.class)
	public Response vendorLogin(
			@ApiParam(value = "Vendor Login Request ", required = true) UserLoginRequest userLoginRequest) throws VendiException {
		BeanValidator.validate(userLoginRequest);
		logger.info("Vendor Login Request: " + userLoginRequest);
		VendorLoginResponse response = loginManager.vendorLogin(userLoginRequest);
		return Response.ok().entity(response).build();
	}

	@POST
	@Logged
	@Timed
	@Path("/fieldexecutives/login")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "FE Login", response = FELoginResponse.class)
	public Response feLogin(@ApiParam(value = "FE Login Request ", required = true) UserLoginRequest userLoginRequest) throws VendiException {
		BeanValidator.validate(userLoginRequest);
		logger.info("FieldExecutive Login Request: " + userLoginRequest);
		FELoginResponse response = loginManager.feLogin(userLoginRequest);
		return Response.ok().entity(response).build();
	}

	@POST
	@Logged
	@Timed
	@Path("/fieldexecutives/{fieldExecuitveId}/changepassword")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "FE Login", response = ApplicationResponse.class)
	public Response feChangePassword(
			@ApiParam(value = "FE Change Password Request ", required = true) ChangePasswordRequest changePasswordRequest, @NotNull @PathParam("fieldExecuitveId") Long fieldExecuitveId) throws VendiException {
		BeanValidator.validate(changePasswordRequest);
		logger.info("FE Login Request: " + changePasswordRequest);
		ApplicationResponse response = loginManager.feChangePassword(changePasswordRequest);
		return Response.ok().entity(response).build();
	}

}
