package vendi.response;

import java.util.List;

import vendi.entity.FieldExecutiveDetails;

public class GetFieldExecutivesResponse {

	List<FieldExecutiveDetails> fieldExecutives;

	public List<FieldExecutiveDetails> getFieldExecutives() {
		return fieldExecutives;
	}

	public void setFieldExecutives(List<FieldExecutiveDetails> fieldExecutives) {
		this.fieldExecutives = fieldExecutives;
	}
}
