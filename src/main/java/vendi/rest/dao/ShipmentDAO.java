package vendi.rest.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import io.dropwizard.hibernate.AbstractDAO;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.CreateShipmentRequest;
import vendi.rest.dao.entity.Shipment;
import vendi.statemachine.State;

public class ShipmentDAO extends AbstractDAO<Shipment> implements IShipmentDAO {

	@Inject
	public ShipmentDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Shipment createOrUpdate(Shipment shipment) throws VendiException {
		try{
			return persist(checkNotNull(shipment));
		}
		catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public Shipment idempotencyCheck(CreateShipmentRequest createShipmentRequest) throws VendiException {
		try{
			Query query = namedQuery("idempotencyCheckForShipment");
			query.setParameter("vendor_id", createShipmentRequest.getVendorId());
			query.setParameter("order_id", createShipmentRequest.getOrderId());
			query.setParameter("external_reference_id", createShipmentRequest.getExternalReferenceId());
			query.setParameter("tracking_id", createShipmentRequest.getTrackingId());
			return (Shipment) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public Shipment getShipmentByShipmentId(Long shipmentId) throws VendiException {
		try{
			Query query = namedQuery("findShipmentByShipmentId");
			query.setParameter("id", shipmentId);
			return (Shipment) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public Shipment getShipmentByTrackingId(String trackingId) throws VendiException {
		try{
			Query query = namedQuery("findShipmentByTrackingId");
			query.setParameter("tracking_id",trackingId );
			return (Shipment) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public Shipment getShipmentByReferenceId(String referenceId) throws VendiException {
		try{
			Query query = namedQuery("findShipmentByReferenceId");
			query.setParameter("external_reference_id",referenceId );
			return (Shipment) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public List<Shipment> getShipmentsForVendor(String vendorId, State status) throws VendiException {
		try{
			Query query = null;

			if (StringUtils.isNotBlank(vendorId) && (null != status)) {
				query = namedQuery("findAllShipmentByVendorIdAndStatus");
				query.setParameter("vendor_id", vendorId);
				query.setParameter("status", status);
			} else if (StringUtils.isNotBlank(vendorId) && (null == status)) {
				query = namedQuery("findAllShipmentByVendorId");
				query.setParameter("vendor_id", vendorId);
			} else {
				return null;
			}
			return (List<Shipment>) query.list();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public List<Shipment> getShipmentsForFE(Long feId, State status) throws VendiException {
		try{
			Query query = null;
			if ((null != feId) && (null != status)) {
				query = namedQuery("findAllShipmentByFeIdAndStatus");
				query.setParameter("fieldExecutive_id", feId);
				query.setParameter("status", status);
			} else if ((null != feId) && (null == status)) {
				query = namedQuery("findAllShipmentByFeId");
				query.setParameter("fieldExecutive_id", feId);
			} else {
				return null;
			}
			return (List<Shipment>) query.list();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

}
