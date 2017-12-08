package com.nocom.inst.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Adam Bien style CDI logger producer.
 * 
 * @author Author.
 *
 */
public class LoggingProducer {
	
	@Produces
	//@AuditTrackLogger
	public Logger getLogger(final InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}

}
