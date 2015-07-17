
package com.muktalabs.em.controller.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

public class JSONMapperBean extends ObjectMapper {

	public JSONMapperBean() {
		super();
		
		init();
	}

	public void init() {
		
		this.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
	}
}
