package vendi.entity;

public enum SelfShipEvent {
	
	RECEIVED("PICKUP_COMPLETED"),
	BOOKED("INSCAN_AT_PICKUP_HUB"),
	FE_ASSIGNED("PENDING"),
	OUT_FOR_DELIVERY("OUT_FOR_DELIVERY"),
	DELIVERED("DELIVERED"),
	CANCEL("RETURN_DELIVERED"),
	UNDELIVERED_ATTEMPTED("PENDING");
	
	private String event;
	
	SelfShipEvent(String event){
		this.event = event;
	}

	public String getEvent() {
		return event;
	}
	
}
