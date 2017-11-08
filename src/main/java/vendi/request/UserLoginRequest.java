package vendi.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserLoginRequest {

	@NotEmpty(message = "field is missing")
	@JsonProperty("username")
	private String username;
	
	@NotEmpty(message = "field is missing")
	@JsonProperty("password")
	private String password;

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

	@Override
	public String toString() {
		return "UserLoginRequest [username=" + username + "]";
	}

	
	
}
