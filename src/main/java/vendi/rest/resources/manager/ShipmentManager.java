package vendi.rest.resources.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import vendi.entity.ReasonCode;
import vendi.entity.SelfShipEvent;
import vendi.entity.SelfShipState;
import vendi.entity.ShipmentResponseDetails;
import vendi.entity.StateMachineData;
import vendi.entity.StatusHistoryDetails;
import vendi.entity.UserType;
import vendi.entity.ValidEvent;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.CreateShipmentRequest;
import vendi.request.ShipmentUpdateRequest;
import vendi.request.StatusUpdateRequest;
import vendi.response.ApplicationResponse;
import vendi.response.CreateShipmentResponse;
import vendi.response.StatusUpdateResponse;
import vendi.rest.dao.IFieldExecutiveDAO;
import vendi.rest.dao.IShipmentDAO;
import vendi.rest.dao.IStatusDAO;
import vendi.rest.dao.entity.FieldExecutive;
import vendi.rest.dao.entity.Shipment;
import vendi.rest.dao.entity.StatusHistory;
import vendi.statemachine.SelfShipStateMachine;
import vendi.statemachine.State;
import vendi.util.ShipmentResponseHelper;

public class ShipmentManager implements IShipmentManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentManager.class);

	@Inject
	protected IShipmentDAO shipmentDAO;

	@Inject
	protected IFieldExecutiveDAO fieldExecutiveDAO;

	@Inject
	protected IStatusDAO statusDAO;

	@Override
	public CreateShipmentResponse createShipment(CreateShipmentRequest request) throws VendiException {
		CreateShipmentResponse createShipmentResponse = new CreateShipmentResponse();
		Shipment shipmentByIdempotencyCheck = shipmentDAO.idempotencyCheck(request);
		if(shipmentByIdempotencyCheck != null){
			logger.info("Duplicate Request : Shipment has already been created");
			createShipmentResponse.setExternalReferenceId(shipmentByIdempotencyCheck.getExternalReferenceId());
			createShipmentResponse.setTrackingId(shipmentByIdempotencyCheck.getTrackingId());
			createShipmentResponse.setShipmentId(shipmentByIdempotencyCheck.getShipmentId());
			createShipmentResponse.setMessage("Shipment has already been created");
			createShipmentResponse.setSuccess(false);
			return createShipmentResponse;
		}
		Shipment shipmentByTrackingId = shipmentDAO.getShipmentByTrackingId(request.getTrackingId());
		if(shipmentByTrackingId != null){
			logger.info("Duplicate Tracking Id : Shipment with given trackingId is already present");
			createShipmentResponse.setExternalReferenceId(request.getExternalReferenceId());
			createShipmentResponse.setTrackingId(request.getTrackingId());
			createShipmentResponse.setShipmentId(shipmentByTrackingId.getShipmentId());
			createShipmentResponse.setMessage("Shipment with given trackingId is already present");
			createShipmentResponse.setSuccess(false);
			return createShipmentResponse;
		}
		Shipment shipmentByReferenceId = shipmentDAO.getShipmentByReferenceId(request.getExternalReferenceId());
		if(shipmentByReferenceId != null){
			logger.info("Duplicate External Reference Id : Shipment with given externalReferenceId is already present");
			createShipmentResponse.setExternalReferenceId(request.getExternalReferenceId());
			createShipmentResponse.setTrackingId(request.getTrackingId());
			createShipmentResponse.setShipmentId(shipmentByReferenceId.getShipmentId());
			createShipmentResponse.setMessage("Shipment with given externalReferenceId is already present");
			createShipmentResponse.setSuccess(false);
			return createShipmentResponse;
		}
		
		Shipment shipment = new Shipment();
		shipment.setExternalReferenceId(request.getExternalReferenceId());
		shipment.setTrackingId(request.getTrackingId());
		shipment.setOrderId(request.getOrderId());
		shipment.setVendorId(request.getVendorId());
		shipment.setProductDescription(request.getProductDescription());
		shipment.setProductCategory(request.getProductCategory());
		shipment.addCustomerDetails(request.getCustomerDetails());
		shipment.addPaymentDetails(request.getPaymentDetails());
		shipment.addPickupDetails(request.getPickupDetails());
		shipment.addReturnDetails(request.getReturnDetails());
		shipment.addShipmentDetails(request.getShipmentDetails());
		shipment.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
		shipment.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
		shipment.setStatus(State.SHIPMENT_CREATED);
		shipment = setDefaultStatusForShipment(shipment);
		Shipment persisted = shipmentDAO.createOrUpdate(shipment);
		try {
			raiseShipmentReceivedEvent(shipment);
		} catch (Exception e) {
			logger.error("State Transition Exception Occurred while shipment creation");
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(), e);
		}
		createShipmentResponse.setSuccess(true);
		createShipmentResponse.setMessage("Shipment has been created.");
		createShipmentResponse.setShipmentId(persisted.getShipmentId());
		createShipmentResponse.setTrackingId(persisted.getTrackingId());
		createShipmentResponse.setExternalReferenceId(persisted.getExternalReferenceId());
		return createShipmentResponse;
	}

	@Override
	public List<ShipmentResponseDetails> getShipmentsForVendor(String vendorId, SelfShipState status) throws VendiException {
		List<Shipment> shipments = shipmentDAO.getShipmentsForVendor(vendorId, status==null?null:(status.getState()==null?null:State.valueOf(status.getState())));
		return convertToShipmentResponse(shipments);
	}

	@Override
	public List<ShipmentResponseDetails> getShipmentsForFE(Long feId, SelfShipState status) throws VendiException {
		List<Shipment> shipments = shipmentDAO.getShipmentsForFE(feId, status==null?null:(status.getState()==null?null:State.valueOf(status.getState())));
		return convertToShipmentResponse(shipments);
	}

	@Override
	public ApplicationResponse updateShipment(ShipmentUpdateRequest shipmentUpdateRequest) throws VendiException {
		ApplicationResponse response = new ApplicationResponse();
		FieldExecutive fieldExecutive = fieldExecutiveDAO
				.findFieldExecutiveById(shipmentUpdateRequest.getFieldExecutiveId());
		Shipment shipment = shipmentDAO.getShipmentByShipmentId(shipmentUpdateRequest.getShipmentId());
		if(shipment!=null && fieldExecutive!=null){
			
			shipment.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
			shipment.setUpdatedBy(UserType.VENDOR);
			shipment.setDeliveryDate(shipmentUpdateRequest.getDeliveryDate());
			shipment.setDeliverySlot(shipmentUpdateRequest.getDeliverySlot());
			if(shipment.getFieldExecutive()==null || (shipment.getFieldExecutive()!=null && !shipment.getFieldExecutive().getId().equals(shipmentUpdateRequest.getFieldExecutiveId()))){
				try {
					updateShipmentStatusOnFEAssignment(shipment);
				} catch (Exception e) {
					logger.error("State Transition Exception Occurred while field executive assignment");
					throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(), e);
				}
			}
			shipment.setFieldExecutive(fieldExecutive);
			shipmentDAO.createOrUpdate(shipment);
			response.setSuccess(true);
			response.setMessage("Successfully updated Shipment.");
			return response;
			
		}
		logger.info("Updating Shipment failed as "
				+ (null == fieldExecutive ? "FieldExecutive not found"
						: (null == shipment ? "Shipment not found"
								: "some error occurred while updating shipment")));
		response.setSuccess(false);
		response.setMessage("Updating Shipment failed as "
				+ (null == fieldExecutive ? "FieldExecutive not found"
						: (null == shipment ? "Shipment not found"
								: "some error occurred while updating shipment")));
		return response;
	}

	@Override
	public List<StatusHistoryDetails> getStatusHistoryForShipmentByTrackingId(String trackingId) throws VendiException {
		List<StatusHistory> statusHistory = statusDAO.getStatusHistoryByTrackingId(trackingId);
		if(statusHistory != null && !statusHistory.isEmpty()){
			return convertToStatusHistoryResponse(statusHistory);
		}else{
			throw new VendiException(404, "TrackingId: " + trackingId + " not found");
		}
	}
	
	@Override
	public List<StatusHistoryDetails> getStatusHistoryForShipmentByReferenceId(String externalReferenceId) throws VendiException {
		List<StatusHistory> statusHistory = statusDAO.getStatusHistoryByReferenceId(externalReferenceId);
		if(statusHistory != null && !statusHistory.isEmpty()){
			return convertToStatusHistoryResponse(statusHistory);
		}else{
			throw new VendiException(404, "ExternalReferenceId: " + externalReferenceId + " not found");
		}
	}

	@Override
	public StatusUpdateResponse updateShipmentStatus(StatusUpdateRequest statusUpdateRequest) throws VendiException {
		StatusUpdateResponse statusUpdateResponse = new StatusUpdateResponse();
		if (statusDAO.idempotencyCheck(statusUpdateRequest)) {
			Shipment shipment = shipmentDAO.getShipmentByShipmentId(statusUpdateRequest.getShipmentId());
			if (null != shipment) {
				StateMachineData stateMachineData = new StateMachineData();
				stateMachineData.setShipmentId(statusUpdateRequest.getShipmentId());
				stateMachineData.setCurrentState(shipment.getStatus().toString());
				stateMachineData.setEvent(statusUpdateRequest.getSelfShipEvent().getEvent().toString());
				stateMachineData.setGeoLat(statusUpdateRequest.getGeoLat());
				stateMachineData.setGeoLong(statusUpdateRequest.getGeoLong());
				stateMachineData.setMessage(statusUpdateRequest.getMessage());
				stateMachineData.setStatusTime(statusUpdateRequest.getStatusTime());
				stateMachineData.setUserType(statusUpdateRequest.getUserType());
				try{
					SelfShipStateMachine.INSTANCE.execute(statusUpdateRequest.getSelfShipEvent(),stateMachineData);
					statusUpdateResponse.setShipmentId(shipment.getShipmentId());
					statusUpdateResponse.setReferenceId(shipment.getExternalReferenceId());
					statusUpdateResponse.setTrackingId(shipment.getTrackingId());
					statusUpdateResponse.setSuccess(true);
					statusUpdateResponse.setRemarks("Successfully Updated");
					return statusUpdateResponse;
				}catch(Exception exception){
					logger.info("Shipment in " + shipment.getStatus() + " state. It cannot be moved to " + statusUpdateRequest.getSelfShipEvent());
					statusUpdateResponse.setShipmentId(shipment.getShipmentId());
					statusUpdateResponse.setReferenceId(shipment.getExternalReferenceId());
					statusUpdateResponse.setTrackingId(shipment.getTrackingId());
					statusUpdateResponse.setSuccess(false);
					statusUpdateResponse.setRemarks("Shipment in " + shipment.getStatus() + " state. It could not be moved to " + statusUpdateRequest.getSelfShipEvent() + " Exception: " + exception.getMessage());
					return statusUpdateResponse;
				}
			} else {
				logger.error("Shipment not found for shipmentId : " + statusUpdateRequest.getShipmentId());
				throw new VendiException(404, VendiErrorCode.SHIPMENT_NOT_FOUND.getName());
			}
		} else {
			logger.error("Duplicate Request for status update");
			throw new VendiException(400, VendiErrorCode.DUPLICATE_REQUEST.getName());
		}
	}

	@Override
	public List<ValidEvent> getNextValidStatuses(Long shipmentId,UserType userType) throws VendiException {
		List<ValidEvent> validStatuses = new ArrayList<ValidEvent>();
		Shipment shipment = shipmentDAO.getShipmentByShipmentId(shipmentId);
		if(null != shipment){
			List<SelfShipEvent> events = SelfShipStateMachine.INSTANCE.getNextValidEvents(shipment.getStatus(), userType);
			for(SelfShipEvent event : events){
				validStatuses.add(new ValidEvent(event.name(),ReasonCode.getReasonCode(event)));
			}
		}
		return validStatuses;
	}
	
	private List<StatusHistoryDetails> convertToStatusHistoryResponse(List<StatusHistory> statusList){
		List<StatusHistoryDetails> statusHistory = new ArrayList<StatusHistoryDetails>();
		for (StatusHistory status : statusList) {
			StatusHistoryDetails statusHistoryResponse = new StatusHistoryDetails();
			statusHistoryResponse.setShipmentId(status.getShipment()==null ? null : status.getShipment().getShipmentId());
			statusHistoryResponse.setTrackingId(status.getShipment()==null ? null : status.getShipment().getTrackingId());
			statusHistoryResponse.setReferenceId(status.getShipment()==null ? null : status.getShipment().getExternalReferenceId());
			statusHistoryResponse.setStatus(status.getStatus().toString());
			statusHistoryResponse.setStatusMessage(status.getMessage());
			statusHistoryResponse.setStatusTime(status.getStatusTime());
			statusHistoryResponse.setStatusGeoLat(status.getGeoLat());
			statusHistoryResponse.setStatusGeoLong(status.getGeoLong());
			statusHistoryResponse.setUpdatedAt(status.getUpdatedAt());
			statusHistoryResponse.setUpdatedBy(status.getUpdatedBy());
			statusHistory.add(statusHistoryResponse);
		}
		return statusHistory;
	}

	private List<ShipmentResponseDetails> convertToShipmentResponse(List<Shipment> shipments){
		List<ShipmentResponseDetails> shipmentList = new ArrayList<ShipmentResponseDetails>();
		for (Shipment shipment : shipments) {
			ShipmentResponseDetails shipmentResponse = new ShipmentResponseDetails();
			ShipmentResponseHelper.addCustomerDetails(shipmentResponse, shipment);
			ShipmentResponseHelper.addDeliveryDetails(shipmentResponse, shipment);
			ShipmentResponseHelper.addFieldExecutiveDetails(shipmentResponse, shipment);
			ShipmentResponseHelper.addPaymentDetails(shipmentResponse, shipment);
			ShipmentResponseHelper.addPickupDetails(shipmentResponse, shipment);
			ShipmentResponseHelper.addReturnDetails(shipmentResponse, shipment);
			ShipmentResponseHelper.addShipmentDetails(shipmentResponse, shipment);
			shipmentResponse.setExternalReferenceId(shipment.getExternalReferenceId());
			shipmentResponse.setOrderId(shipment.getOrderId());
			shipmentResponse.setTrackingId(shipment.getTrackingId());
			shipmentResponse.setProductCategory(shipment.getProductCategory());
			shipmentResponse.setProductDesccription(shipment.getProductDescription());
			shipmentResponse.setShipmentId(shipment.getShipmentId());
			shipmentResponse.setStatus(SelfShipState.STATE_MAPPINGS.get(shipment.getStatus().toString()));
			shipmentList.add(shipmentResponse);
		}
		return shipmentList;
	}
	
	private void raiseShipmentReceivedEvent(Shipment shipment) throws Exception{
		StateMachineData stateMachineData = new StateMachineData();
		stateMachineData.setShipmentId(shipment.getShipmentId());
		stateMachineData.setCurrentState(shipment.getStatus().toString());
		stateMachineData.setSelfShipEvent(SelfShipEvent.RECEIVED);
		stateMachineData.setMessage("Shipment Creation Request");
		stateMachineData.setStatusTime(new java.sql.Timestamp(System.currentTimeMillis()));
		stateMachineData.setUserType(UserType.LITE);
		SelfShipStateMachine.INSTANCE.execute(SelfShipEvent.RECEIVED, stateMachineData);
	}
	
	private Shipment setDefaultStatusForShipment(Shipment shipment){
		List<StatusHistory> statusHistory = new ArrayList<StatusHistory>();
		StatusHistory status = new StatusHistory();
		status.setShipment(shipment);
		status.setUpdatedBy(UserType.LITE);
		status.setStatus(State.SHIPMENT_CREATED);
		status.setMessage("Shipment Creation Request");
		status.setStatusTime(new java.sql.Timestamp(System.currentTimeMillis()));
		status.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
		statusHistory.add(status);
		shipment.setStatusHistory(statusHistory);
		return shipment;
	}
	
	private void updateShipmentStatusOnFEAssignment(Shipment shipment) throws VendiException, Exception{
		StateMachineData stateMachineData = new StateMachineData();
		stateMachineData.setShipmentId(shipment.getShipmentId());
		stateMachineData.setCurrentState(shipment.getStatus().toString());
		stateMachineData.setSelfShipEvent(SelfShipEvent.FE_ASSIGNED);
		stateMachineData.setMessage("Field Executive Assignment");
		stateMachineData.setStatusTime(new java.sql.Timestamp(System.currentTimeMillis()));
		stateMachineData.setUserType(UserType.VENDOR);
		SelfShipStateMachine.INSTANCE.execute(SelfShipEvent.FE_ASSIGNED, stateMachineData);
	}

}
