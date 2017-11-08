package vendi.entity;

public class FieldExecutiveDetails {

	private Long id;
	
	private String name;
	
	private String contact;
	
	private String username;
	
	private Boolean isEnabled;

	public FieldExecutiveDetails() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "FieldExecutiveDetails [id=" + id + ", name=" + name + ", contact=" + contact + ", username=" + username
				+ ", isEnabled=" + isEnabled + "]";
	}
}
