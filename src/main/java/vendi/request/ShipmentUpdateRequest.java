package vendi.request;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ShipmentUpdateRequest {
	
	@NotNull(message = "field is missing")
	@JsonProperty("deliveryDate")
	private Date deliveryDate;
	
	@NotNull(message = "field is missing")
	@JsonProperty("shipmentId")
	private Long shipmentId;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("deliverySlot")
	private String deliverySlot;
	
	@NotNull(message = "field is missing")
	@JsonProperty("fieldExecutiveId")
	private Long fieldExecutiveId;
	
	@JsonIgnore
	private Timestamp requestTime;

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getDeliverySlot() {
		return deliverySlot;
	}

	public void setDeliverySlot(String deliverySlot) {
		this.deliverySlot = deliverySlot;
	}

	public Long getFieldExecutiveId() {
		return fieldExecutiveId;
	}

	public void setFieldExecutiveId(Long fieldExecutiveId) {
		this.fieldExecutiveId = fieldExecutiveId;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp updatedAt) {
		this.requestTime = updatedAt;
	}

	@Override
	public String toString() {
		return "ShipmentUpdateRequest [deliveryDate=" + deliveryDate + ", shipmentId="
				+ shipmentId + ", deliverySlot=" + deliverySlot + ", fieldExecutiveId=" + fieldExecutiveId
				+ "]";
	}
	
}
