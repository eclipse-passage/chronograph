/*******************************************************************************
 *	Copyright (c) 2020 ArSysOp
 *
 *	This program and the accompanying materials are made available under the
 *	terms of the Eclipse Public License 2.0 which is available at
 *	http://www.eclipse.org/legal/epl-2.0.
 *
 *	SPDX-License-Identifier: EPL-2.0
 *
 *	Contributors:
 *	Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - 
 *												initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.base;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UnitConverter {
	public static final LocalDate HISTORICAL_TIME = LocalDate.of(2019, 1, 1);

	public static int localDatetoUnits(LocalDate target) {
		return (int) ChronoUnit.DAYS.between(HISTORICAL_TIME, target);
	}

	public static int localDatetoUnits(Date target) {
		Instant instant = Instant.ofEpochMilli(target.getTime());
		LocalDateTime targetDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDate targetDate = targetDateTime.toLocalDate();
		return (int) ChronoUnit.DAYS.between(HISTORICAL_TIME, targetDate);
	}

	public static LocalDate unitsToLocalDate(long units) {
		return ChronoUnit.DAYS.addTo(HISTORICAL_TIME, units);
	}

	public static Calendar unitsToCalendar(int units) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.clear();
		calendar.set(Calendar.YEAR, HISTORICAL_TIME.getYear());
		calendar.set(Calendar.MONTH, HISTORICAL_TIME.getMonthValue() - 1);
		calendar.set(Calendar.DAY_OF_MONTH, HISTORICAL_TIME.getDayOfMonth());
		// move to units
		calendar.add(Calendar.DATE, units);
		return calendar;
	}
}
