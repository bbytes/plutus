package com.bbytes.plutus.enums;

public enum Currency {

	USD("$ "), INR("Rs ");

	private String symbol;

	Currency(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
