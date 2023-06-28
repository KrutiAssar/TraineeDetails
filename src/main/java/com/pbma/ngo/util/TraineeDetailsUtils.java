package com.pbma.ngo.util;

import java.util.List;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

public class TraineeDetailsUtils {

	private TraineeDetailsUtils() {
		
	}
	
	public static String transformRequest(final String json, final List<Object> chainrSpecJson) {
		Chainr chainr = Chainr.fromSpec(chainrSpecJson);
		Object inputJson = JsonUtils.jsonToObject(json);
		Object transformedOutput = chainr.transform(inputJson);
		return JsonUtils.toPrettyJsonString(transformedOutput);
	}
	
}
