package com.bbytes.plutus.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class DateUtil {

	public static boolean isNotToday(DateTime dateTime) {
		if (dateTime == null)
			return true;

		return (dateTime.toLocalDate()).equals(new LocalDate()) ? false : true;
	}

	public static boolean isSameDay(DateTime dateTime1, DateTime dateTime2) {
		if (dateTime1 == null || dateTime2 == null)
			return false;

		return (dateTime1.toLocalDate()).equals(dateTime2.toLocalDate()) ? true : false;
	}
}
