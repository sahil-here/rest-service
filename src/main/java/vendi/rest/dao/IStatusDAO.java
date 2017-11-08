package vendi.rest.dao;

import java.util.List;

import vendi.exception.VendiException;
import vendi.request.StatusUpdateRequest;
import vendi.rest.dao.entity.StatusHistory;

public interface IStatusDAO {

	StatusHistory createOrUpdate(StatusHistory status) throws VendiException;
	
	Boolean idempotencyCheck(StatusUpdateRequest statusUpdateRequest) throws VendiException;

	List<StatusHistory> getStatusHistoryByTrackingId(String trackingId) throws VendiException;

	List<StatusHistory> getStatusHistoryByReferenceId(String externalReferenceId) throws VendiException;

}