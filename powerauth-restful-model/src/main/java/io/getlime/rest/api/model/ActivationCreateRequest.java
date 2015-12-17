package io.getlime.rest.api.model;

public class ActivationCreateRequest {
	
	private String activationIdShort;
	private String activationNonce;
	private String cDevicePublicKey;
	private String activationName;
	private String extras;
	
	public String getActivationIdShort() {
		return activationIdShort;
	}
	
	public void setActivationIdShort(String activationIdShort) {
		this.activationIdShort = activationIdShort;
	}
	
	public String getActivationNonce() {
		return activationNonce;
	}
	
	public void setActivationNonce(String activationNonce) {
		this.activationNonce = activationNonce;
	}
	
	public String getcDevicePublicKey() {
		return cDevicePublicKey;
	}
	
	public void setcDevicePublicKey(String cDevicePublicKey) {
		this.cDevicePublicKey = cDevicePublicKey;
	}
	
	public String getActivationName() {
		return activationName;
	}
	
	public void setActivationName(String activationName) {
		this.activationName = activationName;
	}
	
	public String getExtras() {
		return extras;
	}
	
	public void setExtras(String extras) {
		this.extras = extras;
	}
	
}
