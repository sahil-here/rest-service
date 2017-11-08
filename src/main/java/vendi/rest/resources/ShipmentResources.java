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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import io.dropwizard.hibernate.UnitOfWork;
import vendi.entity.SelfShipState;
import vendi.entity.UserType;
import vendi.exception.VendiException;
import vendi.request.CreateShipmentRequest;
import vendi.request.ShipmentUpdateRequest;
import vendi.request.StatusUpdateRequest;
import vendi.response.ApplicationResponse;
import vendi.response.CreateShipmentResponse;
import vendi.response.GetShipmentsResponse;
import vendi.response.StatusHistoryResponse;
import vendi.response.StatusUpdateResponse;
import vendi.response.ValidEventsResponse;
import vendi.rest.resources.manager.IShipmentManager;
import vendi.rest.util.logging.Logged;
import vendi.util.BeanValidator;

@Path("vendi")
@Api(value = "shipment")
@Produces(MediaType.APPLICATION_JSON)
public class ShipmentResources {

	@Inject
	protected IShipmentManager shipmentManager;

	private static final Logger logger = LoggerFactory.getLogger(ShipmentResources.class);
	
	@GET
	@Logged
	@Timed
	@Path("/shipments/getforvendor/{vendorId}")
	@UnitOfWork
	@ApiOperation(value = "Get all Shipments for a vendor", response = GetShipmentsResponse.class)
	public Response getShipmentsForVendor(
			@ApiParam(value = "Service Requests for fetching list of shipments for a vendor", required = true) @NotNull @PathParam("vendorId") String vendorId, @QueryParam("status") SelfShipState status) throws VendiException {
			logger.info("Get shipments for a vendor with vendorId " + vendorId);
			GetShipmentsResponse shipments = new GetShipmentsResponse();
			shipments.setShipments(shipmentManager.getShipmentsForVendor(vendorId, status));
			return Response.ok().entity(shipments).build();
	}
	
	@GET
	@Logged
	@Timed
	@Path("/shipments/getforfe/{fieldExecutiveId}")
	@UnitOfWork
	@ApiOperation(value = "Get Shipments for a FE", response = GetShipmentsResponse.class)
	public Response getShipmentsForFE(
			@ApiParam(value = "Service Requests for fetching list of shipments for a fe", required = true) @NotNull @PathParam("fieldExecutiveId") Long fieldExecutiveId, @QueryParam("status") SelfShipState status) throws VendiException {
		logger.info("Get shipments for a fieldExecutive with fieldExecutiveId " + fieldExecutiveId);
		GetShipmentsResponse shipments = new GetShipmentsResponse();
		shipments.setShipments(shipmentManager.getShipmentsForFE(fieldExecutiveId, status));
		return Response.ok().entity(shipments).build();
	}
	
	@PUT
	@Logged
	@Timed
	@Path("/shipments/{shipmentId}")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates the shipment with delivery date, delivery slot and field executive", response = ApplicationResponse.class)
	public Response updateShipment(@ApiParam(value = "Service Request to update the shipment", required = true) ShipmentUpdateRequest shipmentUpdateRequest, @NotNull @PathParam("shipmentId") Long shipmentId) throws VendiException {
		BeanValidator.validate(shipmentUpdateRequest);
		logger.info("Shipment Update Request: " + shipmentUpdateRequest);
		ApplicationResponse response = shipmentManager.updateShipment(shipmentUpdateRequest);
		return Response.ok().entity(response).build();
	}
	
	@GET
	@Logged
	@Timed
	@Path("/shipments/statusHistory")
	@UnitOfWork
	@ApiOperation(value = "Gets the status history for a shipment", response = StatusHistoryResponse.class)
	public Response getStatusHistoryForShipment(
			@ApiParam(value = "Service Requests to get status history for the shipment", required = true) @QueryParam("trackingId") String trackingId, @QueryParam("externalReferenceId") String externalReferenceId) throws VendiException {
		StatusHistoryResponse statusHistory = new StatusHistoryResponse();
		if(StringUtils.isNotBlank(externalReferenceId)){
			logger.info("Get the status history for the shipment with externalReferenceId " + externalReferenceId);
			statusHistory.setStatusHistoryDetails(shipmentManager.getStatusHistoryForShipmentByReferenceId(externalReferenceId));
			return Response.ok().entity(statusHistory).build();
		}else if(StringUtils.isNotBlank(trackingId)){
			logger.info("Get the status history for the shipment with trackingId " + trackingId);
			statusHistory.setStatusHistoryDetails(shipmentManager.getStatusHistoryForShipmentByTrackingId(trackingId));
			return Response.ok().entity(statusHistory).build();
		}else {
			throw new VendiException(400, "Both trackingId and externalReferenceId are empty");
		}
	}
	
	@POST
	@Logged
	@Timed
	@Path("/shipments/{shipmentId}/updatestatus")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the status for a shipment ", response = StatusUpdateResponse.class)
	public Response updateStatusByVendor(@ApiParam(value = "Service Request to update the status of a shipment by vendor", required = true) StatusUpdateRequest statusUpdateRequest, @NotNull @PathParam("shipmentId") Long shipmentId) throws VendiException {
		BeanValidator.validate(statusUpdateRequest);
		logger.info("Status Update Request: " + statusUpdateRequest);
		StatusUpdateResponse statusUpdateResponse = shipmentManager.updateShipmentStatus(statusUpdateRequest);
		return Response.ok().entity(statusUpdateResponse).build();
	}
	
	@GET
	@Logged
	@Timed
	@Path("/shipments/{shipmentId}/validStatus")
	@UnitOfWork
	@ApiOperation(value = "Gets the next valid statuses for a shipment", response = ValidEventsResponse.class)
	public Response getNextValidStatuses(
			@ApiParam(value = "Service Requests to get next valid statuses for a shipment", required = true)@NotNull @QueryParam("userType") UserType userType, @NotNull @PathParam("shipmentId") Long shipmentId) throws VendiException {
			logger.info("Get next valid status for shipment " + shipmentId);
			ValidEventsResponse validEvents =  new ValidEventsResponse();
			validEvents.setValidEvents(shipmentManager.getNextValidStatuses(shipmentId,userType));
			return Response.ok().entity(validEvents).build();
	}
	
	@POST
	@Logged
	@Timed
	@Path("/shipments")
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a new shipment ", response = CreateShipmentResponse.class)
	public Response createNewShipment(@ApiParam(value = "Service Request to update the status of a shipment by vendor", required = true) CreateShipmentRequest createShipmentResquest) throws VendiException {
		BeanValidator.validate(createShipmentResquest);
		logger.info("Create Shipment Request: " + createShipmentResquest);
		CreateShipmentResponse response = shipmentManager.createShipment(createShipmentResquest);
		return Response.ok().entity(response).build();
	}
	
}
