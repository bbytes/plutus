package com.bbytes.plutus.enums;

public enum AppProfile {

	enterprise, saas, enterpriceSaas;

	public boolean isSaasMode() {
		return this.equals(AppProfile.saas) ? true : false;
	}

	public boolean isEnterpriseMode() {
		return (this.equals(AppProfile.enterprise) || this.equals(AppProfile.enterpriceSaas)) ? true : false;
	}
}
