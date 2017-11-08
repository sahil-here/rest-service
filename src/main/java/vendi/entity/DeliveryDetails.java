package vendi.entity;

import java.sql.Date;

public class DeliveryDetails {
	
	private Date deliveryDate;
	
	private String deliverySlot;
	
	public DeliveryDetails() {
		super();
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliverySlot() {
		return deliverySlot;
	}

	public void setDeliverySlot(String deliverySlot) {
		this.deliverySlot = deliverySlot;
	}

	@Override
	public String toString() {
		return "DeliveryDetails [deliveryDate=" + deliveryDate + ", deliverySlot=" + deliverySlot + "]";
	}

}
