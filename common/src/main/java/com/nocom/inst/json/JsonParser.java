package com.nocom.inst.json;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocom.inst.model.Member;

/**
 * 
 * Marshall JSON to model up and down.
 * 
 * @author author
 *
 */
public class JsonParser {
	
	
	public String toString(Member member) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(member);
		} catch (Exception e) {
			throw new ContextedRuntimeException(e.getMessage()).addContextValue("member name", member.getName());
		}
		
	}
	
	public String toPrettyString(Member member) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(member);
		} catch (Exception e) {
			throw new ContextedRuntimeException(e.getMessage()).addContextValue("member name", member.getName());
		}
		
	}
	
	
	public Member toObject(String value) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.readValue(value, Member.class);
		} catch (Exception e) {
			throw new ContextedRuntimeException(e.getMessage()).addContextValue("JSON expression", value);
		}
		
		
	}

}
