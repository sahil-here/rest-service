package vendi.statemachine;

import java.util.ArrayList;
import java.util.List;

public enum State {

	START_STATE_MACHINE,
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
	
	public static List<String> getAllStates(){
		return new ArrayList<String>(){
			private static final long serialVersionUID = 4346238887864025822L;

		{
			add(START_STATE_MACHINE.name());
		    add(SHIPMENT_CREATED.name());
		    add(OUT_FOR_PICKUP.name());
		    add(PICKUP_COMPLETED.name());
		    add(INSCAN_AT_PICKUP_HUB.name());
		    add(IN_TRANSIT.name());
		    add(PENDING.name());
		    add(OUT_FOR_DELIVERY.name());
		    add(DELIVERED.name());
		    add(CANCELLATION_REQUESTED.name());
		    add(CANCELLED.name());
		    add(RETURN_PENDING.name());
		    add(RETURN_OUT_FOR_DELIVERY.name());
		    add(RETURN_DELIVERED.name());
		    
		}};
	}
}
