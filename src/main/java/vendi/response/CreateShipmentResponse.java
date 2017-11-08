package vendi.response;

public class CreateShipmentResponse {
	
	private Boolean success;
	
	private String message;
	
	private Long shipmentId;
	
	private String trackingId;
	
	private String externalReferenceId;

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
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

	@Override
	public String toString() {
		return "CreateShipmentResponse [success=" + success + ", message=" + message 
				+ ", shipmentId=" + shipmentId + ", trackingId=" + trackingId + ", externalReferenceId="
				+ externalReferenceId + "]";
	}

}
