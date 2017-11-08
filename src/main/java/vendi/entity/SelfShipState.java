package vendi.entity;

import java.util.HashMap;
import java.util.Map;

public enum SelfShipState {
	
	RECEIVED("PICKUP_COMPLETED"),
	BOOKED("INSCAN_AT_PICKUP_HUB"),
	FE_ASSIGNED("PENDING"),
	OUT_FOR_DELIVERY("OUT_FOR_DELIVERY"),
	DELIVERED("DELIVERED"),
	CANCELLED("RETURN_DELIVERED");
	
	private String state;
	
	SelfShipState(String state){
		this.state = state;
	}

	public String getState() {
		return state;
	}
	
	public static Map<String,SelfShipState> STATE_MAPPINGS ;
	static
    {
		STATE_MAPPINGS = new HashMap<String,SelfShipState>();
		STATE_MAPPINGS.put("PICKUP_COMPLETED", SelfShipState.RECEIVED);
		STATE_MAPPINGS.put("INSCAN_AT_PICKUP_HUB", SelfShipState.BOOKED);
		STATE_MAPPINGS.put("PENDING", SelfShipState.FE_ASSIGNED);
		STATE_MAPPINGS.put("OUT_FOR_DELIVERY",SelfShipState.OUT_FOR_DELIVERY);
		STATE_MAPPINGS.put("DELIVERED", SelfShipState.DELIVERED);
		STATE_MAPPINGS.put("RETURN_DELIVERED", SelfShipState.CANCELLED);
    }

}
