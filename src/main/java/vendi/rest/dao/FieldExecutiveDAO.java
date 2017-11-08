package vendi.rest.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import io.dropwizard.hibernate.AbstractDAO;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.CreateFieldExecutiveRequest;
import vendi.rest.dao.entity.FieldExecutive;

public class FieldExecutiveDAO extends AbstractDAO<FieldExecutive> implements IFieldExecutiveDAO  {

	@Inject
	public FieldExecutiveDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FieldExecutive createOrUpdate(FieldExecutive fieldExecutive) throws VendiException {
		try{
			return persist(fieldExecutive);
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public FieldExecutive findFieldExecutiveById(Long fieldExecutiveId) throws VendiException {
		try{
			Query query = namedQuery("findFieldExecutiveById");
			query.setParameter("id", fieldExecutiveId);
			return (FieldExecutive) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}	
	}

	@Override
	public FieldExecutive findFieldExecutiveByUsername(String username) throws VendiException {
		try{
			Query query = namedQuery("findFieldExecutiveByUsername");
			query.setParameter("username", username);
			return (FieldExecutive) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public List<FieldExecutive> findAllFieldExecutiveByVendorId(String vendorId, Boolean isEnabled) throws VendiException {
		try{
			Query query = null;

			if (StringUtils.isNotBlank(vendorId) && null != isEnabled) {
				query = namedQuery("findAllEnabledFieldExecutiveByVendorId");
				query.setParameter("vendor_id", vendorId);
				query.setParameter("is_enabled", isEnabled);
			} else if (StringUtils.isNotBlank(vendorId) && null == isEnabled) {
				query = namedQuery("findAllFieldExecutiveByVendorId");
				query.setParameter("vendor_id", vendorId);
			} else {
				return null;
			}
			return (List<FieldExecutive>) query.list();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public String authenticateFieldExecutive(String username) throws VendiException {
		try{
			Query query = namedQuery("findFieldExecutiveByUsername");
			query.setParameter("username", username);
			List<FieldExecutive> list = query.list();
			if (0 != list.size()) {
				return list.get(0).getPassword();
			}
			return null;
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

	@Override
	public Boolean feChangePassword(String username, String password) throws VendiException {
		try{
			Query query = namedQuery("changePasswordFieldExecutive");
			query.setParameter("username", username);
			query.setParameter("password", password);

			int affectedRows = query.executeUpdate();

			if (0 != affectedRows) {
				return true;
			}
			return false;
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}
	
	@Override
	public FieldExecutive idempotencyCheck(CreateFieldExecutiveRequest feCreateRequest) throws VendiException {
		try{
			Query query = namedQuery("idempotencyCheckForFECreation");
			query.setParameter("username", feCreateRequest.getUsername());
			query.setParameter("vendor_id", feCreateRequest.getVendorId());
			query.setParameter("created_at", feCreateRequest.getCreateTime());
			return (FieldExecutive) query.uniqueResult();
		}catch(Exception ex){
			throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
		}
	}

}
