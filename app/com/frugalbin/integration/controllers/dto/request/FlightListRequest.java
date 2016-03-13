package com.frugalbin.integration.controllers.dto.request;

import java.util.Date;

public class FlightListRequest
{
	private Long fromCityId;
	private Long toCityId;
	private Date preferredTime;
	private Integer numberOfTravellers;
	private String slotBreakupType = "THREE_DAYS";

	public Long getFromCityId()
	{
		return fromCityId;
	}

	public void setFromCityId(Long fromCityId)
	{
		this.fromCityId = fromCityId;
	}

	public Long getToCityId()
	{
		return toCityId;
	}

	public void setToCityId(Long toCityId)
	{
		this.toCityId = toCityId;
	}

	public Date getPreferredTime()
	{
		return preferredTime;
	}

	public void setPreferredTime(Date preferredTime)
	{
		this.preferredTime = preferredTime;
	}

	public Integer getNumberOfTravellers()
	{
		return numberOfTravellers;
	}

	public void setNumberOfTravellers(Integer numberOfTravellers)
	{
		this.numberOfTravellers = numberOfTravellers;
	}

	public String getSlotBreakupType()
	{
		return slotBreakupType;
	}

	public void setSlotBreakupType(String slotBreakupType)
	{
		this.slotBreakupType = slotBreakupType;
	}
}
