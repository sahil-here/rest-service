package vendi.statemachine;

public enum Event {

	SHIPMENT_CREATED,
	OUT_FOR_PICKUP,
	PICKUP_COMPLETED,
	INSCAN_AT_PICKUP_HUB,
	IN_TRANSIT,
	PENDING,
	OUT_FOR_DELIVERY,
	DELIVERED,
	CANCELLATION_REQUESTED,
	CANCELLED,
	RETURN_PENDING,
	RETURN_OUT_FOR_DELIVERY,
	RETURN_DELIVERED;

}
