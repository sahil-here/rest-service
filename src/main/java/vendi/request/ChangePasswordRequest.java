package vendi.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ChangePasswordRequest {

	@NotEmpty(message = "field is missing")
	@JsonProperty("username")
	private String username;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("oldPassword")
	private String oldPassword;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("newPassword")
	private String newPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordRequest [username=" + username + "]";
	}
	
}
