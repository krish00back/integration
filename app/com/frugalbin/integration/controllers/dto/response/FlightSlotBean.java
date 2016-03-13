package com.frugalbin.integration.controllers.dto.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightSlotBean
{
	private float minPrice;

	/**
	 * Flight Name -> list of Flights(Flight number, departure, arrival)<br>
	 * 
	 * eg, Indigo -> {9W2847, 2016-02-25T13:35+05:30, 2016-02-25T15:00+05:30}
	 */
	private Map<String, List<FlightDetailsBean>> flightDetailsMap = new HashMap<String, List<FlightDetailsBean>>();

	public float getMinPrice()
	{
		return minPrice;
	}

	public void setMinPrice(float minPrice)
	{
		this.minPrice = minPrice;
	}

	public Map<String, List<FlightDetailsBean>> getFlightDetailsMap()
	{
		return flightDetailsMap;
	}

	public void setFlightDetailsMap(Map<String, List<FlightDetailsBean>> flightDetailsMap)
	{
		this.flightDetailsMap = flightDetailsMap;
	}

	public void addFlightDetails(String flightName, FlightDetailsBean flightDetails)
	{
		List<FlightDetailsBean> flightDetailsList = flightDetailsMap.get(flightName);

		if (flightDetailsList == null)
		{
			flightDetailsList = new ArrayList<FlightDetailsBean>();
			flightDetailsMap.put(flightName, flightDetailsList);
		}

		flightDetailsList.add(flightDetails);
	}
}
