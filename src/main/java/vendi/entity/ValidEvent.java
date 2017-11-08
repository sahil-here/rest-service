package vendi.entity;

import java.util.List;

public class ValidEvent {
	
	private String status;
	
	private List<String> reason;

	public ValidEvent(String status, List<String> reason) {
		super();
		this.status = status;
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getReason() {
		return reason;
	}

	public void setReason(List<String> reason) {
		this.reason = reason;
	}

}
