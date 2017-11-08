package vendi.entity;

public enum UserType {

	LITE,
	FE,
	VENDOR;
	
	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
