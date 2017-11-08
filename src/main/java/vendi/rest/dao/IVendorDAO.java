package vendi.rest.dao;

import vendi.exception.VendiException;
import vendi.request.VendorSignupRequest;
import vendi.rest.dao.entity.Vendor;

/**
 * Created by sahil.bansal on 21/07/16.
 */
public interface IVendorDAO {

    Vendor createOrUpdate(Vendor vendor) throws VendiException;

    Vendor idempotencyCheck(VendorSignupRequest vendorSignupRequest) throws VendiException;

    String authenticateVendor(String email) throws VendiException;

    Vendor findVendorByEmail(String email) throws VendiException;

}
