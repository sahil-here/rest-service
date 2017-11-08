package vendi.entity;

public class PaymentDetails {
	
	private PaymentMode paymentMode;
	
	private Float collectableValue;
	
	private Float declaredValue;

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Float getCollectableValue() {
		return collectableValue;
	}

	public void setCollectableValue(Float collectableValue) {
		this.collectableValue = collectableValue;
	}

	public Float getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Float declaredValue) {
		this.declaredValue = declaredValue;
	}

	@Override
	public String toString() {
		return "PaymentDetails [paymentMode=" + paymentMode + ", collectableValue=" + collectableValue
				+ ", declaredValue=" + declaredValue + "]";
	}

}
