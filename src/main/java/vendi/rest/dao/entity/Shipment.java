package vendi.rest.dao.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import vendi.entity.CustomerDetails;
import vendi.entity.Handling;
import vendi.entity.PaymentDetails;
import vendi.entity.PaymentMode;
import vendi.entity.PickupDetails;
import vendi.entity.ReturnDetails;
import vendi.entity.ShipmentDetails;
import vendi.entity.UserType;
import vendi.statemachine.State;

@Entity
@Table(name = "Shipment", indexes = { @Index(columnList = "id", name = "shipment_id_index_on_shipment"),
		@Index(columnList = "fieldExecutive_id", name = "fe_id_index_on_shipment"),
		@Index(columnList = "vendor_id", name = "vendor_id_index_on_shipment") })

@NamedQueries({
		@NamedQuery(name = "idempotencyCheckForShipment", query = "FROM Shipment where vendor_id = :vendor_id and order_id = :order_id and tracking_id = :tracking_id and external_reference_id = :external_reference_id"),
		@NamedQuery(name = "findAllShipmentByFeId", query = "FROM Shipment where fieldExecutive_id = :fieldExecutive_id"),
		@NamedQuery(name = "findShipmentByShipmentId", query = "FROM Shipment where id = :id"),
		@NamedQuery(name = "findShipmentByTrackingId", query = "FROM Shipment where tracking_id = :tracking_id"),
		@NamedQuery(name = "findShipmentByReferenceId", query = "FROM Shipment where external_reference_id = :external_reference_id"),
		@NamedQuery(name = "findShipmentByShipmentIdAndVendor", query = "FROM Shipment where id = :id and vendor_id = :vendor_id"),
		@NamedQuery(name = "findAllShipmentByVendorId", query = "FROM Shipment where vendor_id = :vendor_id"),
		@NamedQuery(name = "findAllShipmentByFeIdAndStatus", query = "FROM Shipment where fieldExecutive_id = :fieldExecutive_id and status = :status"),
		@NamedQuery(name = "findAllShipmentByVendorIdAndStatus", query = "FROM Shipment where vendor_id = :vendor_id and status = :status") })

public class Shipment {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shipmentId;
	
	@Column(name = "vendor_id", nullable = false)
	private String vendorId;
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name="fieldExecutive_id")
	private FieldExecutive fieldExecutive;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private State status;
	
	@Column(name = "external_reference_id", nullable = false, unique=true)
	private String externalReferenceId;
	
	@Column(name = "tracking_id", nullable = false, unique=true)
	private String trackingId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "product_desc")
	private String productDescription;
	
	@Column(name = "product_cat")
	private String productCategory;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_contact")
	private String customerContact;
	
	@Column(name = "customer_address1")
	private String customerAddress1;
	
	@Column(name = "customer_address2")
	private String customerAddress2;
	
	@Column(name = "customer_landmark")
	private String customerLandmark;
	
	@Column(name = "customer_city")
	private String customerCity;
	
	@Column(name = "customer_state")
	private String cusomterState;
	
	@Column(name = "customer_pincode")
	private String customerPincode;
	
	@Column(name = "customer_geolat")
	private Float customerGeoLat;
	
	@Column(name = "customer_geolong")
	private Float customerGeoLong;
	
	@Column(name = "pickup_name")
	private String pickupName;
	
	@Column(name = "pickup_contact")
	private String pickupContact;
	
	@Column(name = "pickup_address1")
	private String pickupAddress1;
	
	@Column(name = "pickup_address2")
	private String pickupAddress2;
	
	@Column(name = "pickup_city")
	private String pickupCity;
	
	@Column(name = "pickup_state")
	private String pickupState;
	
	@Column(name = "pickup_geolat")
	private Float pickupGeoLat;
	
	@Column(name = "pickup_geolong")
	private Float pickupGeoLong;
	
	@Column(name = "pickup_pincode")
	private String pickupPincode;
	
	@Column(name = "return_name")
	private String returnName;
	
	@Column(name = "return_contact")
	private String returnContact;
	
	@Column(name = "return_address1")
	private String returnAddress1;
	
	@Column(name = "return_address2")
	private String returnAddress2;
	
	@Column(name = "return_city")
	private String returnCity;
	
	@Column(name = "return_state")
	private String returnState;
	
	@Column(name = "return_pincode")
	private String returnPincode;
	
	@Column(name = "promised_delivery_date")
	private Date promisedDeliveryDate;
	
	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "delivery_slot")
	private String deliverySlot;
	
	@Column(name = "pieces")
	private Integer pieces;
	
	@Column(name = "colleactable_value")
	private Float colleactableValue;
	
	@Column(name = "declared_value")
	private Float declaredValue;
	
	@Column(name = "volumetric_weight")
	private Float volumetricWeight;
	
	@Column(name = "actual_weight")
	private Float actualWeight;
	
	@Column(name = "shipment_length")
	private Float shipmentLength;
	
	@Column(name = "shipment_breadth")
	private Float shipmentBreadth;
	
	@Column(name = "shipment_height")
	private Float shipmentHeight;
	
	@Column(name = "payment_mode")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	@Column(name = "handling")
	@Enumerated(EnumType.STRING)
	private Handling handling;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_by")
	@Enumerated(EnumType.STRING)
	private UserType updatedBy;
	
	@OneToMany( mappedBy="shipment",cascade = CascadeType.ALL)
	@JsonManagedReference
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<StatusHistory> statusHistory;

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public FieldExecutive getFieldExecutive() {
		return fieldExecutive;
	}

	public void setFieldExecutive(FieldExecutive fieldExecutive) {
		this.fieldExecutive = fieldExecutive;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}

	public String getExternalReferenceId() {
		return externalReferenceId;
	}

	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getCustomerPincode() {
		return customerPincode;
	}

	public void setCustomerPincode(String customerPincode) {
		this.customerPincode = customerPincode;
	}

	public Float getCustomerGeoLat() {
		return customerGeoLat;
	}

	public void setCustomerGeoLat(Float customerGeoLat) {
		this.customerGeoLat = customerGeoLat;
	}

	public Float getCustomerGeoLong() {
		return customerGeoLong;
	}

	public void setCustomerGeoLong(Float customerGeoLong) {
		this.customerGeoLong = customerGeoLong;
	}

	public String getPickupName() {
		return pickupName;
	}

	public void setPickupName(String pickupName) {
		this.pickupName = pickupName;
	}

	public String getPickupContact() {
		return pickupContact;
	}

	public void setPickupContact(String pickupContact) {
		this.pickupContact = pickupContact;
	}

	public Float getPickupGeoLat() {
		return pickupGeoLat;
	}

	public void setPickupGeoLat(Float pickupGeoLat) {
		this.pickupGeoLat = pickupGeoLat;
	}

	public Float getPickupGeoLong() {
		return pickupGeoLong;
	}

	public void setPickupGeoLong(Float pickupGeoLong) {
		this.pickupGeoLong = pickupGeoLong;
	}

	public String getPickupPincode() {
		return pickupPincode;
	}

	public void setPickupPincode(String pickupPincode) {
		this.pickupPincode = pickupPincode;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public String getReturnContact() {
		return returnContact;
	}

	public void setReturnContact(String returnContact) {
		this.returnContact = returnContact;
	}

	public String getReturnPincode() {
		return returnPincode;
	}

	public void setReturnPincode(String returnPincode) {
		this.returnPincode = returnPincode;
	}

	public Date getPromisedDeliveryDate() {
		return promisedDeliveryDate;
	}

	public void setPromisedDeliveryDate(Date promisedDeliveryDate) {
		this.promisedDeliveryDate = promisedDeliveryDate;
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

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	public Float getColleactableValue() {
		return colleactableValue;
	}

	public void setColleactableValue(Float colleactableValue) {
		this.colleactableValue = colleactableValue;
	}

	public Float getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Float declaredValue) {
		this.declaredValue = declaredValue;
	}

	public Float getVolumetricWeight() {
		return volumetricWeight;
	}

	public void setVolumetricWeight(Float volumetricWeight) {
		this.volumetricWeight = volumetricWeight;
	}

	public Float getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Float actualWeight) {
		this.actualWeight = actualWeight;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Handling getHandling() {
		return handling;
	}

	public void setHandling(Handling handling) {
		this.handling = handling;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public UserType getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserType updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<StatusHistory> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<StatusHistory> statusHistory) {
		this.statusHistory = statusHistory;
	}
	
	public String getCustomerAddress1() {
		return customerAddress1;
	}

	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}

	public String getCustomerAddress2() {
		return customerAddress2;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}

	public String getCustomerLandmark() {
		return customerLandmark;
	}

	public void setCustomerLandmark(String customerLandmark) {
		this.customerLandmark = customerLandmark;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCusomterState() {
		return cusomterState;
	}

	public void setCusomterState(String cusomterState) {
		this.cusomterState = cusomterState;
	}

	public String getPickupAddress1() {
		return pickupAddress1;
	}

	public void setPickupAddress1(String pickupAddress1) {
		this.pickupAddress1 = pickupAddress1;
	}

	public String getPickupAddress2() {
		return pickupAddress2;
	}

	public void setPickupAddress2(String pickupAddress2) {
		this.pickupAddress2 = pickupAddress2;
	}

	public String getPickupCity() {
		return pickupCity;
	}

	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}

	public String getPickupState() {
		return pickupState;
	}

	public void setPickupState(String pickupState) {
		this.pickupState = pickupState;
	}

	public String getReturnAddress1() {
		return returnAddress1;
	}

	public void setReturnAddress1(String returnAddress1) {
		this.returnAddress1 = returnAddress1;
	}

	public String getReturnAddress2() {
		return returnAddress2;
	}

	public void setReturnAddress2(String returnAddress2) {
		this.returnAddress2 = returnAddress2;
	}

	public String getReturnCity() {
		return returnCity;
	}

	public void setReturnCity(String returnCity) {
		this.returnCity = returnCity;
	}

	public String getReturnState() {
		return returnState;
	}

	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}

	public Float getShipmentLength() {
		return shipmentLength;
	}

	public void setShipmentLength(Float shipmentLength) {
		this.shipmentLength = shipmentLength;
	}

	public Float getShipmentBreadth() {
		return shipmentBreadth;
	}

	public void setShipmentBreadth(Float shipmentBreadth) {
		this.shipmentBreadth = shipmentBreadth;
	}

	public Float getShipmentHeight() {
		return shipmentHeight;
	}

	public void setShipmentHeight(Float shipmentHeight) {
		this.shipmentHeight = shipmentHeight;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public void addCustomerDetails(CustomerDetails customerDetails){
		if(null!=customerDetails){
			this.customerName = customerDetails.getName();
			this.customerContact = customerDetails.getContact();
			if(null!=customerDetails.getAddress()){
				this.customerAddress1 = customerDetails.getAddress().getAddress1();
				this.customerAddress2 = customerDetails.getAddress().getAddress2();
				this.customerLandmark = customerDetails.getAddress().getLandmark();
				this.customerCity = customerDetails.getAddress().getCity();
				this.cusomterState = customerDetails.getAddress().getState();
				this.customerPincode = customerDetails.getAddress().getPincode();
				this.customerGeoLat = customerDetails.getAddress().getGeoLat();
				this.customerGeoLong = customerDetails.getAddress().getGeoLong();
			}
		}
	}
	
	public void addShipmentDetails(ShipmentDetails shipmentDetails){
		if(null!=shipmentDetails){
			this.actualWeight = shipmentDetails.getActualWeight();
			this.volumetricWeight = shipmentDetails.getVolumetricWeight();
			this.shipmentLength = shipmentDetails.getLength();
			this.shipmentBreadth = shipmentDetails.getBreadth();
			this.shipmentHeight = shipmentDetails.getHeight();
			this.pieces = shipmentDetails.getPieces();
			this.handling = shipmentDetails.getHandling();
			this.promisedDeliveryDate = shipmentDetails.getPromisedDeliveryDate();
		}
	}
	
	public void addPickupDetails(PickupDetails pickupDetails){
		if(null != pickupDetails){
			this.pickupName = pickupDetails.getName();
			this.pickupContact = pickupDetails.getContact();
			if(null != pickupDetails.getAddress()){
				this.pickupAddress1 = pickupDetails.getAddress().getAddress1();
				this.pickupAddress2 = pickupDetails.getAddress().getAddress2();
				this.pickupCity = pickupDetails.getAddress().getCity();
				this.pickupState = pickupDetails.getAddress().getState();
				this.pickupPincode = pickupDetails.getAddress().getPincode();
				this.pickupGeoLat = pickupDetails.getAddress().getGeoLat();
				this.pickupGeoLong = pickupDetails.getAddress().getGeoLong();
			}
		}
	}
	
	public void addReturnDetails(ReturnDetails returnDetails){
		if(null!=returnDetails){
			this.returnName = returnDetails.getName();
			this.returnContact = returnDetails.getContact();
			if(null!=returnDetails.getAddress()){
				this.returnAddress1 = returnDetails.getAddress().getAddress1();
				this.returnAddress2 = returnDetails.getAddress().getAddress2();
				this.returnCity = returnDetails.getAddress().getCity();
				this.returnState = returnDetails.getAddress().getState();
				this.returnPincode = returnDetails.getAddress().getPincode();
			}
		}
	}
	
	public void addPaymentDetails(PaymentDetails paymentDetails){
		if(null!=paymentDetails){
			this.paymentMode = paymentDetails.getPaymentMode();
			this.colleactableValue = paymentDetails.getCollectableValue();
			this.declaredValue = paymentDetails.getDeclaredValue();
		}
	}

	@Override
	public String toString() {
		return "Shipment [shipmentId=" + shipmentId + ", vendorId=" + vendorId + ", fieldExecutive=" + fieldExecutive
				+ ", status=" + status + ", externalReferenceId=" + externalReferenceId + ", trackingId=" + trackingId
				+ ", orderId=" + orderId + ", productDescription=" + productDescription + ", productCategory="
				+ productCategory + ", customerName=" + customerName + ", customerContact=" + customerContact
				+ ", customerAddress1=" + customerAddress1 + ", customerAddress2=" + customerAddress2
				+ ", customerLandmark=" + customerLandmark + ", customerCity=" + customerCity + ", cusomterState="
				+ cusomterState + ", customerPincode=" + customerPincode + ", customerGeoLat=" + customerGeoLat
				+ ", customerGeoLong=" + customerGeoLong + ", pickupName=" + pickupName + ", pickupContact="
				+ pickupContact + ", pickupAddress1=" + pickupAddress1 + ", pickupAddress2=" + pickupAddress2
				+ ", pickupCity=" + pickupCity + ", pickupState=" + pickupState + ", pickupGeoLat=" + pickupGeoLat
				+ ", pickupGeoLong=" + pickupGeoLong + ", pickupPincode=" + pickupPincode + ", returnName=" + returnName
				+ ", returnContact=" + returnContact + ", returnAddress1=" + returnAddress1 + ", returnAddress2="
				+ returnAddress2 + ", returnCity=" + returnCity + ", returnState=" + returnState + ", returnPincode="
				+ returnPincode + ", promisedDeliveryDate=" + promisedDeliveryDate + ", deliveryDate=" + deliveryDate
				+ ", deliverySlot=" + deliverySlot + ", pieces=" + pieces + ", colleactableValue=" + colleactableValue
				+ ", declaredValue=" + declaredValue + ", volumetricWeight=" + volumetricWeight + ", actualWeight="
				+ actualWeight + ", shipmentLength=" + shipmentLength + ", shipmentBreadth=" + shipmentBreadth
				+ ", shipmentHeight=" + shipmentHeight + ", paymentMode=" + paymentMode + ", handling=" + handling
				+ ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy
				+ ", statusHistory=" + statusHistory + "]";
	}

}
