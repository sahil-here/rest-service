package vendi.rest.resources.manager;


import java.util.List;

import vendi.entity.FieldExecutiveDetails;
import vendi.exception.VendiException;
import vendi.request.CreateFieldExecutiveRequest;
import vendi.request.EditFieldExecutiveRequest;
import vendi.response.ApplicationResponse;

public interface IFieldExecutiveManager {

	FieldExecutiveDetails createFieldExecutive(CreateFieldExecutiveRequest feRequest)
			throws VendiException;

	ApplicationResponse editFieldExecutive(EditFieldExecutiveRequest feRequest) throws VendiException;

	List<FieldExecutiveDetails> getFieldExecutivesForVendor(String vendorId, Boolean isEnabled)
			throws VendiException;

}