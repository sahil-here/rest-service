package vendi.entity;

import java.util.ArrayList;
import java.util.List;

public enum ReasonCode {

	CUSTOMER_NO_RESPONSE("Customer No Response"),
	COD_NOT_READY("COD Not Ready"),
	RESCHEDULE_REQUESTED("Request To Reschedule"),
	ADDRESS_RELATED_ISSUE("Address Related Issue"),
	CONSIGNEE_SHIFTED("Consignee Shifted Issue"),
	DISPUTE("Dispute"),
	MISROUTE("Misroute"),
	SELF_COLLECT("Self-Collect"),
	OUT_OF_DELIVERY_AREA("Out Of Delivery Area"),
	CUSTOMER_REJECTION("Customer Rejection"),
	DELIVERY_AREA_INACCESSIBLE("Delivery Area Not Accessible"),
	DELAY("Delay Beyond Control"),
	NSS("No Service Zone"),
	CUSTOMER_CANCELLATION("Customer Cancellation");
	
	private final String name;

	private ReasonCode(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static List<String> getReasonCode(SelfShipEvent event) {
		switch (event) {
		case UNDELIVERED_ATTEMPTED:
			return new ArrayList<String>() {
				private static final long serialVersionUID = -7672012572938596348L;

				{
					add(CUSTOMER_NO_RESPONSE.getName());
					add(COD_NOT_READY.getName());
					add(RESCHEDULE_REQUESTED.getName());
					add(ADDRESS_RELATED_ISSUE.getName());
					add(CONSIGNEE_SHIFTED.getName());
					add(DISPUTE.getName());
					add(MISROUTE.getName());
					add(SELF_COLLECT.getName());
					add(OUT_OF_DELIVERY_AREA.getName());
					add(CUSTOMER_REJECTION.getName());
					add(DELIVERY_AREA_INACCESSIBLE.getName());
					add(DELAY.getName());
					add(NSS.getName());
				}
			};
		case CANCEL:
			return new ArrayList<String>() {
				private static final long serialVersionUID = 4185107144425794351L;

				{
					add(COD_NOT_READY.getName());
					add(ADDRESS_RELATED_ISSUE.getName());
					add(CONSIGNEE_SHIFTED.getName());
					add(DISPUTE.getName());
					add(MISROUTE.getName());
					add(OUT_OF_DELIVERY_AREA.getName());
					add(CUSTOMER_CANCELLATION.getName());
					add(DELIVERY_AREA_INACCESSIBLE.getName());
					add(DELAY.getName());
					add(NSS.getName());
				}
			};
		default:
			return null;

		}
	}
}
