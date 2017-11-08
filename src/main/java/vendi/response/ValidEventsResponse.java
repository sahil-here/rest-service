package vendi.response;

import java.util.List;

import vendi.entity.ValidEvent;

public class ValidEventsResponse {

	List<ValidEvent> validEvents;

	public List<ValidEvent> getValidEvents() {
		return validEvents;
	}

	public void setValidEvents(List<ValidEvent> validEvents) {
		this.validEvents = validEvents;
	}
}
