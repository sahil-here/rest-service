package vendi.statemachine;

import java.util.HashMap;
import java.util.Map;

public class StateTransitionData {
    Map<String,Object> context;
    State currentState;
    Event event;

    public StateTransitionData(State current,Event event){
        this.currentState = current;
        this.event = event;
        context = new HashMap<>();
    }

    public Object get(String contextKey){
        return context.get(contextKey);
    }

    public void putToContext(String key, Object value){
        context.put(key,value);
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
