package com.bbytes.plutus.util;

import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyUtil {

	private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);

	private static String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789";

	public static String getSubscriptionKey() {
		return getRandomKey(20);
	}
	
	public static String getSubscriptionSecret() {
		return getRandomKey(75);
	}
	
	private static String getRandomKey(int length) {
		Random secureRandomProvider = new SecureRandom();
		char[] buffer = new char[length];
		for (int idx = 0; idx < buffer.length; ++idx)
			buffer[idx] = symbols.charAt(secureRandomProvider.nextInt(symbols.length()));
		logger.info("key : " + new String(buffer));
		return new String(buffer);
	}
}
