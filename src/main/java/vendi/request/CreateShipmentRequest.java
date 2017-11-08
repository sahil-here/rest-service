package vendi.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import vendi.entity.CustomerDetails;
import vendi.entity.PaymentDetails;
import vendi.entity.PickupDetails;
import vendi.entity.ReturnDetails;
import vendi.entity.ShipmentDetails;

public class CreateShipmentRequest {

	@NotEmpty(message = "field is missing")
	private String orderId;
	
	@NotEmpty(message = "field is missing")
	private String externalReferenceId;
	
	@NotEmpty(message = "field is missing")
	private String trackingId;
	
	private String productDescription;
	
	private String productCategory;
	
	@NotEmpty(message = "field is missing")
	private String vendorId;
	
	@NotNull(message = "field is missing")
	private CustomerDetails customerDetails;
	
	private PickupDetails pickupDetails;
	
	@NotNull(message = "field is missing")
	private PaymentDetails paymentDetails;
	
	@NotNull(message = "field is missing")
	private ShipmentDetails shipmentDetails;
	
	private ReturnDetails returnDetails;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExternalReferenceId() {
		return externalReferenceId;
	}

	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public ShipmentDetails getShipmentDetails() {
		return shipmentDetails;
	}

	public void setShipmentDetails(ShipmentDetails shipmentDetails) {
		this.shipmentDetails = shipmentDetails;
	}

	public ReturnDetails getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(ReturnDetails returnDetails) {
		this.returnDetails = returnDetails;
	}

	public PickupDetails getPickupDetails() {
		return pickupDetails;
	}

	public void setPickupDetails(PickupDetails pickupDetails) {
		this.pickupDetails = pickupDetails;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	@Override
	public String toString() {
		return "CreateShipmentRequest [orderId=" + orderId + ", externalReferenceId=" + externalReferenceId
				+ ", trackingId=" + trackingId + ", productDescription=" + productDescription + ", productCategory="
				+ productCategory + ", vendorId=" + vendorId + ", customerDetails=" + customerDetails
				+ ", pickupDetails=" + pickupDetails + ", paymentDetails=" + paymentDetails + ", shipmentDetails="
				+ shipmentDetails + ", returnDetails=" + returnDetails + "]";
	}
	
}
