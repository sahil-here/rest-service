package vendi.entity;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StatusHistoryDetails {

	private Long shipmentId;

	private String referenceId;

	private String trackingId;

	private String status;

	private String statusMessage;

	private Timestamp statusTime;

	private Float statusGeoLat;

	private Float statusGeoLong;

	@JsonIgnore
	private Timestamp updatedAt;

	@JsonIgnore
	private UserType updatedBy;

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserType getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserType updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}

	public Float getStatusGeoLat() {
		return statusGeoLat;
	}

	public void setStatusGeoLat(Float statusGeoLat) {
		this.statusGeoLat = statusGeoLat;
	}

	public Float getStatusGeoLong() {
		return statusGeoLong;
	}

	public void setStatusGeoLong(Float statusGeoLong) {
		this.statusGeoLong = statusGeoLong;
	}

	@Override
	public String toString() {
		return "StatusHistoryResponse [shipmentId=" + shipmentId + ", referenceId=" + referenceId + ", status=" + status
				+ ", statusMessage=" + statusMessage + ", statusTime=" + statusTime + ", statusGeoLat=" + statusGeoLat
				+ ", statusGeoLong=" + statusGeoLong + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}

}
