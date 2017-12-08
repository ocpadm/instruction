package com.nocom.inst.jms;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.CompletionListener;
import javax.jms.Message;

import org.slf4j.Logger;

/**
 * 
 * Sample JMS async listener to be registered on a JMS queue producer for asynchronous response handling.
 * 
 * Example taken from Java EE7 Development with Wildfly
 * 
 * @author author
 *
 */
@ApplicationScoped
public class MemberCompletionListener implements CompletionListener {

	@Inject
	private Logger logger;

	public MemberCompletionListener() {
		super();
	}

	@Override
	public void onCompletion(Message message) {
		try {
			final String text = message.getBody(String.class);
			logger.info("Send was successful: " + text);
		} catch (Throwable e) {
			logger.error("Problem with message format");
		}
	}

	@Override
	public void onException(Message message, Exception exception) {
		try {
			final String text = message.getBody(String.class);
			logger.info("Send failed..." + text);
		} catch (Throwable e) {
			logger.error("Problem with message format");
		}
	}
}