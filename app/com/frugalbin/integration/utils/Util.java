package com.frugalbin.integration.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.frugalbin.common.utils.Constants;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.WS.Response;

public class Util
{
	public static JsonNode getJsonResponse(Promise<Response> response)
	{
		return response.map(new Function<Response, JsonNode>()
		{
			@Override
			public JsonNode apply(Response res) throws Throwable
			{
				return res.asJson();
			}
		}).get(Constants.REST_TIMEOUT);
	}
}
