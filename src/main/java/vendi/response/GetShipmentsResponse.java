package vendi.response;

import java.util.List;

import vendi.entity.ShipmentResponseDetails;

public class GetShipmentsResponse {
	
	List<ShipmentResponseDetails> shipments;

	public List<ShipmentResponseDetails> getShipments() {
		return shipments;
	}

	public void setShipments(List<ShipmentResponseDetails> shipments) {
		this.shipments = shipments;
	}

}
