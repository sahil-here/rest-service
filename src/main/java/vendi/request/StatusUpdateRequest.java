package vendi.request;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import vendi.entity.SelfShipEvent;
import vendi.entity.UserType;

@JsonIgnoreProperties(ignoreUnknown=true)
public class StatusUpdateRequest {
	
	@JsonProperty("shipmentId")
	private Long shipmentId;
	
	@JsonProperty("referenceId")
	private String referenceId;
	
	@NotNull(message = "field is missing")
	@JsonProperty("status")
	private SelfShipEvent selfShipEvent;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("geoLat")
	private Float geoLat;
	
	@JsonProperty("geoLong")
	private Float geoLong;
	
	@NotNull(message = "field is missing")
	@JsonProperty("statusTime")
	private Timestamp statusTime;
	
	@NotNull(message = "field is missing")
	@JsonProperty("userType")
	private UserType userType;

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public SelfShipEvent getSelfShipEvent() {
		return selfShipEvent;
	}

	public void setSelfShipEvent(SelfShipEvent selfShipEvent) {
		this.selfShipEvent = selfShipEvent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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

	@Override
	public String toString() {
		return "StatusUpdateRequest [shipmentId=" + shipmentId + ", referenceId=" + referenceId + ", status=" + selfShipEvent
				+ ", message=" + message + ", geoLat=" + geoLat + ", geoLong=" + geoLong + ", statusTime=" + statusTime
				+ ", userType=" + userType + "]";
	}

}
