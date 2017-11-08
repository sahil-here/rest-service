package vendi.rest.resources.manager;

import java.util.List;

import vendi.entity.SelfShipState;
import vendi.entity.ShipmentResponseDetails;
import vendi.entity.StatusHistoryDetails;
import vendi.entity.UserType;
import vendi.entity.ValidEvent;
import vendi.exception.VendiException;
import vendi.request.CreateShipmentRequest;
import vendi.request.ShipmentUpdateRequest;
import vendi.request.StatusUpdateRequest;
import vendi.response.ApplicationResponse;
import vendi.response.CreateShipmentResponse;
import vendi.response.StatusUpdateResponse;

public interface IShipmentManager {

	List<ShipmentResponseDetails> getShipmentsForVendor(String vendorId, SelfShipState status) throws VendiException;

	List<ShipmentResponseDetails> getShipmentsForFE(Long feId, SelfShipState status) throws VendiException;

	ApplicationResponse updateShipment(ShipmentUpdateRequest shipmentUpdateRequest) throws VendiException;

	List<ValidEvent> getNextValidStatuses(Long shipmentId, UserType userType) throws VendiException;

	CreateShipmentResponse createShipment(CreateShipmentRequest request) throws VendiException;

	StatusUpdateResponse updateShipmentStatus(StatusUpdateRequest statusUpdateRequest) throws VendiException;

	List<StatusHistoryDetails> getStatusHistoryForShipmentByTrackingId(String trackingId) throws VendiException;

	List<StatusHistoryDetails> getStatusHistoryForShipmentByReferenceId(String externalReferenceId)
			throws VendiException;

}