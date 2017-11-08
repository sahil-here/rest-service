package vendi.rest.dao;

import java.util.List;

import vendi.exception.VendiException;
import vendi.request.CreateShipmentRequest;
import vendi.rest.dao.entity.Shipment;
import vendi.statemachine.State;

public interface IShipmentDAO {

	Shipment createOrUpdate(Shipment shipment) throws VendiException;

	Shipment getShipmentByShipmentId(Long shipmentId) throws VendiException;

	List<Shipment> getShipmentsForVendor(String vendorId, State status) throws VendiException;

	List<Shipment> getShipmentsForFE(Long feId, State status) throws VendiException;

	Shipment idempotencyCheck(CreateShipmentRequest createShipmentRequest) throws VendiException;

	Shipment getShipmentByTrackingId(String trackingId) throws VendiException;

	Shipment getShipmentByReferenceId(String referenceId) throws VendiException;

}