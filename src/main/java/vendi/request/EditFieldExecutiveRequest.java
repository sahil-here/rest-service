package vendi.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import vendi.entity.UserType;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EditFieldExecutiveRequest {
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("isEnabled")
	private Boolean isEnabled;
	
	@JsonProperty("password")
	private String password;
	
	@NotNull(message = "field is missing")
	@JsonProperty("userType")
	private UserType userType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "EditFieldExecutiveRequest [username=" + username + ", contact=" + contact + ", name=" + name
				+ ", isEnabled=" + isEnabled + ", userType=" + userType + "]";
	}
	
}
