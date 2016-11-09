package com.bbytes.plutus.enums;

public enum TimePeriod {

	Today(0), Yesterday(1), Weekly(7), BiWeekly(14), Monthly(30), Quaterly(90),HalfYearly(180), Annually(365);

	int days;

	TimePeriod(int days) {
		this.days = days;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

}
