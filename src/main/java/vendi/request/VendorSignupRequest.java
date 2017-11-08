package vendi.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sahil.bansal on 21/07/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class VendorSignupRequest {

    @NotEmpty(message = "field is missing")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "field is missing")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "field is missing")
    @JsonProperty("contact")
    private String contact;

    @NotEmpty(message = "field is missing")
    @JsonProperty("password")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "VendorSignupRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
