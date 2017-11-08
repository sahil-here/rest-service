package vendi.statemachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import vendi.entity.StateMachineData;

import java.util.EnumSet;

public class GeneralStateMachine {

    private static GeneralStateMachine instance = null;

    private GeneralStateMachine() throws Exception{
        stateMachine = buildMachine();
    }
    public static GeneralStateMachine getInstance() throws Exception{
        if(instance == null) {
            instance = new GeneralStateMachine();
        }
        return instance;
    }

    StateMachine<State,Event> stateMachine;

    public StateMachine<State, Event> buildMachine() throws Exception {
        StateMachineBuilder.Builder<State, Event> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(State.START_STATE_MACHINE)
                .states(EnumSet.allOf(State.class));

        builder.configureTransitions()
                .withExternal()
                .source(State.START_STATE_MACHINE).target(State.SHIPMENT_CREATED)
                .event(Event.SHIPMENT_CREATED)
                .and()
                .withExternal()
                .source(State.SHIPMENT_CREATED).target(State.OUT_FOR_PICKUP)
                .event(Event.OUT_FOR_PICKUP)
                .and()
                .withExternal()
                .source(State.OUT_FOR_PICKUP).target(State.PICKUP_COMPLETED)
                .event(Event.PICKUP_COMPLETED)
                .and()
                .withExternal()
                .source(State.PICKUP_COMPLETED).target(State.INSCAN_AT_PICKUP_HUB)
                .event(Event.INSCAN_AT_PICKUP_HUB)
                .and()
                .withExternal()
                .source(State.INSCAN_AT_PICKUP_HUB).target(State.IN_TRANSIT)
                .event(Event.IN_TRANSIT)
                .and()
                .withExternal()
                .source(State.IN_TRANSIT).target(State.PENDING)
                .event(Event.PENDING)
                .and()
                .withExternal()
                .source(State.PENDING).target(State.OUT_FOR_DELIVERY)
                .event(Event.OUT_FOR_DELIVERY)
                .and()
                .withExternal()
                .source(State.OUT_FOR_DELIVERY).target(State.DELIVERED)
                .event(Event.DELIVERED)
                .and()
                .withExternal()
                .source(State.OUT_FOR_DELIVERY).target(State.PENDING)
                .event(Event.PENDING)
                .and()
                .withExternal()
                .source(State.PENDING).target(State.PENDING)
                .event(Event.PENDING)
                .and()
                .withExternal()
                .source(State.SHIPMENT_CREATED).target(State.CANCELLATION_REQUESTED)
                .event(Event.CANCELLATION_REQUESTED)
                .and()
                .withExternal()
                .source(State.OUT_FOR_PICKUP).target(State.CANCELLATION_REQUESTED)
                .event(Event.CANCELLATION_REQUESTED)
                .and()
                .withExternal()
                .source(State.PICKUP_COMPLETED).target(State.CANCELLATION_REQUESTED)
                .event(Event.CANCELLATION_REQUESTED)
                .and()
                .withExternal()
                .source(State.INSCAN_AT_PICKUP_HUB).target(State.CANCELLATION_REQUESTED)
                .event(Event.CANCELLATION_REQUESTED)
                .and()
                .withExternal()
                .source(State.IN_TRANSIT).target(State.CANCELLATION_REQUESTED)
                .event(Event.CANCELLATION_REQUESTED)
                .and()
                .withExternal()
                .source(State.PENDING).target(State.CANCELLATION_REQUESTED)
                .event(Event.CANCELLATION_REQUESTED)
                .and()
                .withExternal()
                .source(State.CANCELLATION_REQUESTED).target(State.CANCELLED)
                .event(Event.CANCELLED)
                .and()
                .withExternal()
                .source(State.CANCELLED).target(State.RETURN_PENDING)
                .event(Event.RETURN_PENDING)
                .and()
                .withExternal()
                .source(State.RETURN_PENDING).target(State.RETURN_PENDING)
                .event(Event.RETURN_PENDING)
                .and()
                .withExternal()
                .source(State.RETURN_PENDING).target(State.RETURN_OUT_FOR_DELIVERY)
                .event(Event.RETURN_OUT_FOR_DELIVERY)
                .and()
                .withExternal()
                .source(State.RETURN_OUT_FOR_DELIVERY).target(State.RETURN_DELIVERED)
                .event(Event.RETURN_DELIVERED)
                .and()
                .withExternal()
                .source(State.RETURN_OUT_FOR_DELIVERY).target(State.RETURN_PENDING)
                .event(Event.RETURN_PENDING)
        ;
        return builder.build();
    }
    
    public void execute(StateMachineData stateMachineData) throws Exception {
        StateTransitionData stateTransitionData = new StateTransitionData(State.valueOf(stateMachineData.getCurrentState()),Event.valueOf(stateMachineData.getEvent()));
        stateTransitionData.putToContext("statusMessage", stateMachineData.getMessage());
        stateTransitionData.putToContext("shipmentId", stateMachineData.getShipmentId());
        stateTransitionData.putToContext("userType", stateMachineData.getUserType());
        stateTransitionData.putToContext("statusTime", stateMachineData.getStatusTime());
        stateTransitionData.putToContext("geoLat", stateMachineData.getGeoLat());
        stateTransitionData.putToContext("geoLong", stateMachineData.getGeoLong());
        StateMachine stateMachine = GeneralStateMachine.getInstance().stateMachine;
        stateMachine.sendEvent(Event.valueOf(stateMachineData.getEvent()));
    }
    
}
