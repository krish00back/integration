package com.frugalbin.integration.controllers.dto.response;

import java.util.Date;

public class FlightDetailsBean
{
	private String flightNumber;
	private Date departureTime;
	private Date arrivalTime;

	public FlightDetailsBean(String flightNumber, Date departureTime, Date arrivalTime)
	{
		this.flightNumber = flightNumber;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	public String getFlightNumber()
	{
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber)
	{
		this.flightNumber = flightNumber;
	}

	public Date getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(Date departureTime)
	{
		this.departureTime = departureTime;
	}

	public Date getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}
}
