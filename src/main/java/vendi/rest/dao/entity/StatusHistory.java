package vendi.rest.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonBackReference;

import vendi.entity.UserType;
import vendi.statemachine.State;

@Entity
@Table(name = "StatusHistory", indexes = { @Index(columnList = "shipment_id", name = "shipment_id_index_on_statushistory") })

@NamedQueries({
		@NamedQuery(name = "findAllStatusByTrackingId", query = "Select sts FROM StatusHistory sts inner join sts.shipment shp WHERE shp.trackingId = :tracking_id"),
		@NamedQuery(name = "findAllStatusByReferenceId", query = "Select sts FROM StatusHistory sts inner join sts.shipment shp WHERE shp.externalReferenceId = :external_reference_id"),
		@NamedQuery(name = "idempotencyCheck", query = "FROM StatusHistory WHERE shipment_id = :shipment_id and status = :status and status_time = :status_time") })

public class StatusHistory {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statusId;
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name="shipment_id")
	private Shipment shipment;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private State status;
	
	@Column(name = "geoLat")
	private Float geoLat;
	
	@Column(name = "geoLong")
	private Float geoLong;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	@Enumerated(EnumType.STRING)
	private UserType updatedBy;

	@Column(name = "message")
	private String message;
	
	@Column(name="status_time")
	private Timestamp statusTime;

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipmentId(Shipment shipment) {
		this.shipment = shipment;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
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
		return "StatusHistory [statusId=" + statusId + ", shipment=" + shipment 
				+ ", status=" + status + ", geoLat=" + geoLat + ", geoLong=" + geoLong + ", updatedAt=" + updatedAt
				+ ", updatedBy=" + updatedBy + ", message=" + message + ", statusTime=" + statusTime + "]";
	}

}
