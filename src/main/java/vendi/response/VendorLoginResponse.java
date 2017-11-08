package vendi.response;


import vendi.entity.VendorDetails;

public class VendorLoginResponse extends ApplicationResponse{

	private VendorDetails vendorDetails;

	public VendorDetails getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(VendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	@Override
	public String toString() {
		return "VendorLoginResponse [isSuccess=" + isSuccess + ", message="
				+ message + " vendorDetails=" + vendorDetails.toString() +  "]";
	}
}
