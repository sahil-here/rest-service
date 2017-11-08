package vendi.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CustomerDetails {

	@NotEmpty(message = "field is missing")
	private String name;
	
	@NotEmpty(message = "field is missing")
	private String contact;
	
	@NotNull(message = "field is missing")
	private Address address;
	
	public CustomerDetails() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CustomerDetails [name=" + name + ", contact=" + contact + ", address=" + address + "]";
	}
}
