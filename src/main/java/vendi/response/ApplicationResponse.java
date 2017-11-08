package vendi.response;

public class ApplicationResponse {
	
	protected Boolean isSuccess;
	
	protected String message;

	public Boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LoginResponse [isSuccess=" + isSuccess + ", message=" + message + "]";
	}

}
