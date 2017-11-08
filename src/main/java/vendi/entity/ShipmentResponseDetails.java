package vendi.entity;

public class ShipmentResponseDetails {
	
	private Long shipmentId;
	
	private String orderId;
	
	private String trackingId;
	
	private String externalReferenceId;
	
	private String productDesccription;
	
	private String productCategory;
	
	private CustomerDetails customerDetails;
	
	private PickupDetails pickupDetails;
	
	private DeliveryDetails deliveryDetails;
	
	private FieldExecutiveDetails fieldExecutiveDetails;
	
	private ReturnDetails returnDetails;
	
	private PaymentDetails paymentDetails;
	
	private ShipmentDetails shipmentDetails;
	
	private SelfShipState status;

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getExternalReferenceId() {
		return externalReferenceId;
	}

	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}

	public String getProductDesccription() {
		return productDesccription;
	}

	public void setProductDesccription(String productDesccription) {
		this.productDesccription = productDesccription;
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

	public PickupDetails getPickupDetails() {
		return pickupDetails;
	}

	public void setPickupDetails(PickupDetails pickupDetails) {
		this.pickupDetails = pickupDetails;
	}

	public DeliveryDetails getDeliveryDetails() {
		return deliveryDetails;
	}

	public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}

	public FieldExecutiveDetails getFieldExecutiveDetails() {
		return fieldExecutiveDetails;
	}

	public void setFieldExecutiveDetails(FieldExecutiveDetails fieldExecutiveDetails) {
		this.fieldExecutiveDetails = fieldExecutiveDetails;
	}

	public ReturnDetails getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(ReturnDetails returnDetails) {
		this.returnDetails = returnDetails;
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

	public SelfShipState getStatus() {
		return status;
	}

	public void setStatus(SelfShipState status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ShipmentResponseDetails [shipmentId=" + shipmentId + ", orderId=" + orderId + ", trackingId="
				+ trackingId + ", externalReferenceId=" + externalReferenceId + ", productDesccription="
				+ productDesccription + ", productCategory=" + productCategory + ", customerDetails=" + customerDetails
				+ ", pickupDetails=" + pickupDetails + ", deliveryDetails=" + deliveryDetails
				+ ", fieldExecutiveDetails=" + fieldExecutiveDetails + ", returnDetails=" + returnDetails
				+ ", paymentDetails=" + paymentDetails + ", shipmentDetails=" + shipmentDetails + ", status=" + status
				+ "]";
	}
	
}
