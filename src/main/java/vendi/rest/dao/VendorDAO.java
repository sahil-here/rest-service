package vendi.rest.dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import vendi.exception.VendiErrorCode;
import vendi.exception.VendiException;
import vendi.request.VendorSignupRequest;
import vendi.rest.dao.entity.FieldExecutive;
import vendi.rest.dao.entity.Vendor;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sahil.bansal on 21/07/16.
 */
public class VendorDAO extends AbstractDAO<Vendor> implements IVendorDAO{

    @Inject
    public VendorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Vendor createOrUpdate(Vendor vendor) throws VendiException {
        try{
            return persist(checkNotNull(vendor));
        }catch(Exception ex){
            throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Vendor idempotencyCheck(VendorSignupRequest vendorSignupRequest) throws VendiException {
        try{
            Query query = namedQuery("idempotencyCheckForVendorCreation");
            query.setParameter("name", vendorSignupRequest.getName());
            query.setParameter("email", vendorSignupRequest.getEmail());
            query.setParameter("contact", vendorSignupRequest.getContact());
            return (Vendor) query.uniqueResult();
        }catch(Exception ex){
            throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public String authenticateVendor(String email) throws VendiException {
        try{
            Query query = namedQuery("findVendorByEmail");
            query.setParameter("email", email);
            List<Vendor> list = query.list();
            if (0 != list.size()) {
                return list.get(0).getPassword();
            }
            return null;
        }catch(Exception ex){
            throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Vendor findVendorByEmail(String email) throws VendiException {
        try{
            Query query = namedQuery("findVendorByEmail");
            query.setParameter("email", email);
            List<Vendor> list = query.list();
            if (0 != list.size()) {
                return list.get(0);
            }
            return null;
        }catch(Exception ex){
            throw new VendiException(500, VendiErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

}
