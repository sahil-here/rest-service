package vendi.statemachine;

import java.util.ArrayList;
import java.util.List;


import vendi.entity.SelfShipEvent;
import vendi.entity.StateMachineData;
import vendi.entity.UserType;

public enum SelfShipStateMachine {
	INSTANCE;
	
	public void execute(SelfShipEvent event, StateMachineData stateMachineData) throws Exception{
		stateMachineData.setEvent(event.getEvent().toString());
		if(event==SelfShipEvent.RECEIVED && State.SHIPMENT_CREATED.toString().equals(stateMachineData.getCurrentState())){
			stateMachineData.setEvent(Event.OUT_FOR_PICKUP.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.OUT_FOR_PICKUP.toString());
			stateMachineData.setEvent(Event.PICKUP_COMPLETED.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.PICKUP_COMPLETED.toString());
		}else if(event==SelfShipEvent.FE_ASSIGNED && State.PICKUP_COMPLETED.toString().equals(stateMachineData.getCurrentState())){
			stateMachineData.setEvent(Event.INSCAN_AT_PICKUP_HUB.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.INSCAN_AT_PICKUP_HUB.toString());
			stateMachineData.setEvent(Event.IN_TRANSIT.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.IN_TRANSIT.toString());
			stateMachineData.setEvent(Event.PENDING.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.PENDING.toString());
		}else if(event==SelfShipEvent.CANCEL){
			stateMachineData.setEvent(Event.CANCELLATION_REQUESTED.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.CANCELLATION_REQUESTED.toString());
			stateMachineData.setEvent(Event.CANCELLED.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.CANCELLED.toString());
			stateMachineData.setEvent(Event.RETURN_PENDING.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.RETURN_PENDING.toString());
			stateMachineData.setEvent(Event.RETURN_OUT_FOR_DELIVERY.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.RETURN_OUT_FOR_DELIVERY.toString());
			stateMachineData.setEvent(Event.RETURN_DELIVERED.toString());
			GeneralStateMachine.getInstance().execute(stateMachineData);
			stateMachineData.setCurrentState(State.RETURN_DELIVERED.toString());
		}else{
			GeneralStateMachine.getInstance().execute(stateMachineData);
		}
	}
	
	public List<SelfShipEvent> getNextValidEvents(State initialState,UserType userType){
		return userType == UserType.VENDOR ? getNextValidEventsForVendor(initialState) : getNextValidEventsForFE(initialState);
	}
	
	public List<SelfShipEvent> getNextValidEventsForVendor(State initialState){
		List<SelfShipEvent> validEvents = new ArrayList<SelfShipEvent>();
		switch(initialState){
		case PICKUP_COMPLETED : 
			validEvents.add(SelfShipEvent.CANCEL);
			break;
		case PENDING :
			validEvents.add(SelfShipEvent.OUT_FOR_DELIVERY);
			validEvents.add(SelfShipEvent.CANCEL);
			break;
		case OUT_FOR_DELIVERY :
			validEvents.add(SelfShipEvent.DELIVERED);
			validEvents.add(SelfShipEvent.UNDELIVERED_ATTEMPTED);
			break;
		default:
			break;
		}
    	return validEvents;
    }
	
	public List<SelfShipEvent> getNextValidEventsForFE(State initialState){
		List<SelfShipEvent> validEvents = new ArrayList<SelfShipEvent>();
		switch(initialState){
		case PENDING :
			validEvents.add(SelfShipEvent.OUT_FOR_DELIVERY);
			break;
		case OUT_FOR_DELIVERY :
			validEvents.add(SelfShipEvent.DELIVERED);
			validEvents.add(SelfShipEvent.UNDELIVERED_ATTEMPTED);
			break;
		default:
			break;
		}
    	return validEvents;
	}

}
