package vendi.entity;

import java.sql.Timestamp;

public class StateMachineData {

	private Long shipmentId;
	
	private String currentState;
	
	private SelfShipEvent selfShipEvent;
	
	private String event;
	
	private String message;
	
	private Float geoLat;
	
	private Float geoLong;
	
	private Timestamp statusTime;
	
	private UserType userType;

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public SelfShipEvent getSelfShipEvent() {
		return selfShipEvent;
	}

	public void setSelfShipEvent(SelfShipEvent selfShipEvent) {
		this.selfShipEvent = selfShipEvent;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Float getGeoLat() {
		return geoLat;
	}

	public void setGeoLat(Float geoLat) {
		this.geoLat = geoLat;
	}

	public Float getGeoLong() {
		return geoLong;
	}

	public void setGeoLong(Float geoLong) {
		this.geoLong = geoLong;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}
	
}
