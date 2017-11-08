package vendi.request;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CreateFieldExecutiveRequest {

	@NotEmpty(message = "field is missing")
	@JsonProperty("username")
	private String username;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("password")
	private String password;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("contact")
	private String contact;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("vendorId")
	private String vendorId;
	
	@NotNull(message = "field is missing")
	@JsonProperty("createTime")
	private Timestamp createTime;
	
	@JsonProperty("name")
	private String name;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CreateFieldExecutiveRequest [username=" + username + ", contact=" + contact + ", vendorId=" + vendorId
				+ ", createTime=" + createTime + ", name=" + name + "]";
	}
	
}
