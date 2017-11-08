package vendi.response;

import java.util.List;

import vendi.entity.StatusHistoryDetails;

public class StatusHistoryResponse {

	List<StatusHistoryDetails> statusHistory;

	public List<StatusHistoryDetails> getStatusHistoryDetails() {
		return statusHistory;
	}

	public void setStatusHistoryDetails(List<StatusHistoryDetails> statusHistoryDetails) {
		this.statusHistory = statusHistoryDetails;
	}
	
}
