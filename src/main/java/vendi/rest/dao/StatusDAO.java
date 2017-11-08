package vendi.rest.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import io.dropwizard.hibernate.AbstractDAO;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.StatusUpdateRequest;
import vendi.rest.dao.entity.StatusHistory;

public class StatusDAO extends AbstractDAO<StatusHistory> implements IStatusDAO {

	@Inject
	public StatusDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public StatusHistory createOrUpdate(StatusHistory status) throws VendiException {
		try{
			return persist(checkNotNull(status));
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public List<StatusHistory> getStatusHistoryByTrackingId(String trackingId) throws VendiException {
		try{
			Query query = namedQuery("findAllStatusByTrackingId");
			query.setParameter("tracking_id", trackingId);
			return (List<StatusHistory>) query.list();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public List<StatusHistory> getStatusHistoryByReferenceId(String externalReferenceId) throws VendiException {
		try{
			Query query = namedQuery("findAllStatusByReferenceId");
			query.setParameter("external_reference_id", externalReferenceId);
			return (List<StatusHistory>) query.list();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public Boolean idempotencyCheck(StatusUpdateRequest statusUpdateRequest) throws VendiException {
		try{
			Query query = namedQuery("idempotencyCheck");
			query.setParameter("shipment_id", statusUpdateRequest.getShipmentId());
			query.setParameter("status", statusUpdateRequest.getSelfShipEvent());
			query.setParameter("status_time", statusUpdateRequest.getStatusTime());
			List<StatusHistory> list = query.list();
			if(0 != list.size()){
				return false;
			}
			return true;
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

}
