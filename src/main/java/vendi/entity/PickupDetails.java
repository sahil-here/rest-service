package vendi.entity;

public class PickupDetails {

	private String name;
	
	private String contact;
	
	private Address address;

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
		return "PickupDetails [name=" + name + ", contact=" + contact + ", address=" + address + "]";
	}
	
}
