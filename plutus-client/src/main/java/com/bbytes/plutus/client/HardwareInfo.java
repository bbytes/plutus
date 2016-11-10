package com.bbytes.plutus.client;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class HardwareInfo {

	private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public String getHardwareId() {
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		String hardwareId = hal.getProcessor().getSystemSerialNumber() + ":" + hal.getProcessor().getLogicalProcessorCount() + ":"
				+ hal.getProcessor().getIdentifier();
		return byteArray2Hex(hardwareId.getBytes());
	}

	public String byteArray2Hex(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (final byte b : bytes) {
			sb.append(hex[(b & 0xF0) >> 4]);
			sb.append(hex[b & 0x0F]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(new HardwareInfo().getHardwareId());

	}
}
