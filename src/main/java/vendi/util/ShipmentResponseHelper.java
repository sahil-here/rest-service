package vendi.util;

import vendi.entity.Address;
import vendi.entity.CustomerDetails;
import vendi.entity.DeliveryDetails;
import vendi.entity.FieldExecutiveDetails;
import vendi.entity.PaymentDetails;
import vendi.entity.PickupDetails;
import vendi.entity.ReturnDetails;
import vendi.entity.ShipmentDetails;
import vendi.entity.ShipmentResponseDetails;
import vendi.rest.dao.entity.Shipment;

public class ShipmentResponseHelper {

	public static void addCustomerDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipment && null!=shipmentResponseDetails){
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setName(shipment.getCustomerName());
			customerDetails.setContact(shipment.getCustomerContact());
			Address address = new Address();
			address.setAddress1(shipment.getCustomerAddress1());
			address.setAddress2(shipment.getCustomerAddress2());
			address.setCity(shipment.getCustomerCity());
			address.setState(shipment.getCusomterState());
			address.setGeoLat(shipment.getCustomerGeoLat());
			address.setGeoLong(shipment.getCustomerGeoLong());
			address.setLandmark(shipment.getCustomerLandmark());
			address.setPincode(shipment.getCustomerPincode());
			customerDetails.setAddress(address);
			shipmentResponseDetails.setCustomerDetails(customerDetails);
		}
	}
	
	public static void addPickupDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipment && null!=shipmentResponseDetails){
			PickupDetails pickupDetails = new PickupDetails();
			pickupDetails.setName(shipment.getPickupName());
			pickupDetails.setContact(shipment.getPickupContact());
			Address address = new Address();
			address.setAddress1(shipment.getPickupAddress1());
			address.setAddress2(shipment.getPickupAddress2());
			address.setCity(shipment.getPickupCity());
			address.setState(shipment.getPickupState());
			address.setGeoLat(shipment.getPickupGeoLat());
			address.setGeoLong(shipment.getPickupGeoLong());
			address.setPincode(shipment.getPickupPincode());
			pickupDetails.setAddress(address);
			shipmentResponseDetails.setPickupDetails(pickupDetails);
		}
	}
	
	public static void addDeliveryDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipment && null!=shipmentResponseDetails){
			DeliveryDetails deliveryDetails = new DeliveryDetails();
			deliveryDetails.setDeliveryDate(shipment.getDeliveryDate());
			deliveryDetails.setDeliverySlot(shipment.getDeliverySlot());
			shipmentResponseDetails.setDeliveryDetails(deliveryDetails);
		}
	}
	
	public static void addFieldExecutiveDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipmentResponseDetails && null!=shipment && null!=shipment.getFieldExecutive()){
			FieldExecutiveDetails feDetails = new FieldExecutiveDetails();
			feDetails.setId(shipment.getFieldExecutive().getId());
			feDetails.setName(shipment.getFieldExecutive().getName());
			feDetails.setUsername(shipment.getFieldExecutive().getUsername());
			feDetails.setIsEnabled(shipment.getFieldExecutive().getIsEnabled());
			feDetails.setContact(shipment.getFieldExecutive().getContact());
			shipmentResponseDetails.setFieldExecutiveDetails(feDetails);
		}
	}
	
	public static void addReturnDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipment && null!=shipmentResponseDetails){
			ReturnDetails returnDetails =  new ReturnDetails();
			returnDetails.setName(shipment.getReturnName());
			returnDetails.setContact(shipment.getReturnContact());
			Address address = new Address();
			address.setAddress1(shipment.getReturnAddress1());
			address.setAddress2(shipment.getReturnAddress2());
			address.setCity(shipment.getReturnCity());
			address.setState(shipment.getReturnState());
			address.setPincode(shipment.getReturnPincode());
			returnDetails.setAddress(address);
			shipmentResponseDetails.setReturnDetails(returnDetails);
		}
	}
	
	public static void addPaymentDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipment && null!=shipmentResponseDetails){
			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails.setPaymentMode(shipment.getPaymentMode());
			paymentDetails.setCollectableValue(shipment.getColleactableValue());
			paymentDetails.setDeclaredValue(shipment.getDeclaredValue());
			shipmentResponseDetails.setPaymentDetails(paymentDetails);
		}
	}
	
	public static void addShipmentDetails(ShipmentResponseDetails shipmentResponseDetails,Shipment shipment){
		if(null!=shipment && null!=shipmentResponseDetails){
			ShipmentDetails shipmentDetails = new ShipmentDetails();
			shipmentDetails.setActualWeight(shipment.getActualWeight());
			shipmentDetails.setVolumetricWeight(shipment.getVolumetricWeight());
			shipmentDetails.setLength(shipment.getShipmentLength());
			shipmentDetails.setBreadth(shipment.getShipmentBreadth());
			shipmentDetails.setHeight(shipment.getShipmentHeight());
			shipmentDetails.setHandling(shipment.getHandling());
			shipmentDetails.setPieces(shipment.getPieces());
			shipmentDetails.setPromisedDeliveryDate(shipment.getPromisedDeliveryDate());
			shipmentResponseDetails.setShipmentDetails(shipmentDetails);
		}
	}
}
