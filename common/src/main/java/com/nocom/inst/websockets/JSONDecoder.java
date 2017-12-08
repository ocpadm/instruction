package com.nocom.inst.websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.nocom.inst.model.Member;

public class JSONDecoder implements Decoder.Text<Object> {

	private Gson gson;

	@Override
	public void init(EndpointConfig config) {
		gson = new Gson();
	}

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public Object decode(String s) throws DecodeException {
		return gson.fromJson(s, Member.class);
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}