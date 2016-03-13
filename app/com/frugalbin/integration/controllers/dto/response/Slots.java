package com.frugalbin.integration.controllers.dto.response;

import java.util.Calendar;
import java.util.Date;

public enum Slots
{
	SEVEN_AM_TO_SEVEN_PM,
	SEVEN_PM_TO_SEVEN_AM;

	public static Slots getSlot(Date departureTime)
	{
		// TODO: need impl
		return SEVEN_AM_TO_SEVEN_PM;
	}
}
