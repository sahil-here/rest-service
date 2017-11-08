package vendi.rest.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import vendi.entity.FieldExecutiveDetails;
import vendi.exception.VendiException;
import vendi.request.CreateFieldExecutiveRequest;
import vendi.request.EditFieldExecutiveRequest;
import vendi.response.ApplicationResponse;
import vendi.response.GetFieldExecutivesResponse;
import vendi.rest.resources.manager.IFieldExecutiveManager;
import vendi.rest.util.logging.Logged;
import vendi.util.BeanValidator;

@Path("vendi")
@Api(value = "fe")
@Produces(MediaType.APPLICATION_JSON)
public class FieldExecutiveResources {

	@Inject
	protected IFieldExecutiveManager fieldExecutiveManager;

	private static final Logger logger = LoggerFactory.getLogger(FieldExecutiveResources.class);

	@POST
	@Logged
	@Timed
	@Path("/fieldexecutives")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a field executive", response = FieldExecutiveDetails.class)
	public Response createFieldExecuitve(
			@ApiParam(value = "Service Request to create a new field executive", required = true) CreateFieldExecutiveRequest feRequest) throws VendiException {
		BeanValidator.validate(feRequest);
		logger.info("Create FieldExecutive Request: " + feRequest);
		FieldExecutiveDetails response = fieldExecutiveManager.createFieldExecutive(feRequest);
		return Response.ok().entity(response).build();
	}
	
	@GET
	@Logged
	@Timed
	@Path("/fieldexecutives")
	@UnitOfWork
	@ApiOperation(value = "Get fieldExecutives for a vendor ", response = GetFieldExecutivesResponse.class)
	public Response getFeForVendor(
			@ApiParam(value = "Service Request for fetching field executives for a vendor", required = true)@NotNull @QueryParam("vendorId") String vendorId, @QueryParam("isEnabled") Boolean isEnabled) throws VendiException {
		logger.info("Get fieldExecutives for vendor with vendorId : " + vendorId);
		GetFieldExecutivesResponse fieldExecutives = new GetFieldExecutivesResponse();
		fieldExecutives.setFieldExecutives(fieldExecutiveManager.getFieldExecutivesForVendor(vendorId,isEnabled));
		return Response.ok().entity(fieldExecutives).build();
	}

	@PUT
	@Logged
	@Timed
	@Path("/fieldexecutives/{fieldExecutiveId}")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Edit field executive details", response = ApplicationResponse.class)
	public Response editFieldExecutive(
			@ApiParam(value = "Service Request to edit field executive details", required = true) EditFieldExecutiveRequest feRequest, @NotNull @PathParam("fieldExecutiveId") String fieldExecutiveId) throws VendiException {
		BeanValidator.validate(feRequest);
		logger.info("Edit FieldExecutive Request: " + feRequest);
		ApplicationResponse response = fieldExecutiveManager.editFieldExecutive(feRequest);
		return Response.ok().entity(response).build();
	}
}
