package vendi.rest.dao;

import java.util.List;

import vendi.exception.VendiException;
import vendi.request.CreateFieldExecutiveRequest;
import vendi.rest.dao.entity.FieldExecutive;

public interface IFieldExecutiveDAO {

	FieldExecutive createOrUpdate(FieldExecutive fieldExecutive) throws VendiException;

	FieldExecutive findFieldExecutiveById(Long fieldExecutiveId) throws VendiException;

	FieldExecutive findFieldExecutiveByUsername(String username) throws VendiException;

	List<FieldExecutive> findAllFieldExecutiveByVendorId(String vendorId, Boolean isEnabled) throws VendiException;

	String authenticateFieldExecutive(String username) throws VendiException;

	Boolean feChangePassword(String username, String password) throws VendiException;

	FieldExecutive idempotencyCheck(CreateFieldExecutiveRequest feCreateRequest) throws VendiException;

}