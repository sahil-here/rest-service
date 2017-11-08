package vendi.response;

import vendi.entity.FieldExecutiveDetails;

public class FELoginResponse extends ApplicationResponse{

	private FieldExecutiveDetails feDetails;

	public FieldExecutiveDetails getFeDetails() {
		return feDetails;
	}

	public void setFeDetails(FieldExecutiveDetails feDetails) {
		this.feDetails = feDetails;
	}

	@Override
	public String toString() {
		return "FELoginResponse [isSuccess=" + isSuccess + ", message=" + message + ", feDetails=" + feDetails + "]";
	}
}
