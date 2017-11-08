package vendi.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class Address {

	@NotEmpty(message = "field is missing")
	private String address1;
	
	private String address2;
	
	private String landmark;
	
	@NotEmpty(message = "field is missing")
	private String pincode;
	
	@NotEmpty(message = "field is missing")
	private String city;
	
	@NotEmpty(message = "field is missing")
	private String state;
	
	private Float geoLat;
	
	private Float geoLong;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Float getGeoLat() {
		return geoLat;
	}

	public void setGeoLat(Float geoLat) {
		this.geoLat = geoLat;
	}

	public Float getGeoLong() {
		return geoLong;
	}

	public void setGeoLong(Float geoLong) {
		this.geoLong = geoLong;
	}

	@Override
	public String toString() {
		return "Address [address1=" + address1 + ", address2=" + address2 + ", landmark=" + landmark + ", pincode="
				+ pincode + ", city=" + city + ", state=" + state + ", geoLat=" + geoLat + ", geoLong=" + geoLong + "]";
	}
	
}
