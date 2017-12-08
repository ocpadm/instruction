package com.nocom.inst.jms;

import java.security.Principal;

import javax.inject.Inject;

import org.slf4j.Logger;

import com.nocom.inst.json.JsonParser;


/**
 * 
 * Sample MDB listing for message from queue, message body containers JSON serialized member record.
 * 
 * Example taken from Java EE7 Development with Wildfly
 * 
 * @author author
 *
 */
//@MessageDriven(name = "MemberRegistrationQueueReceiver", activationConfig = {
//		@ActivationConfigProperty(propertyName = "maxPoolDepth", propertyValue="3"),
//		@ActivationConfigProperty(propertyName = "maxMessages", propertyValue="1"),
//	    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//	    @ActivationConfigProperty(propertyName = "transportType", propertyValue = "CLIENT")
//	})
//public class MemberRegistrationQueueReceiver implements MessageListener {
	public class MemberRegistrationQueueReceiver  {

	@Inject
	private Logger logger;
	
	@Inject
	private Principal principal;
	
	@Inject JsonParser jsonSerializer;

//    @Override
//    public void onMessage(Message message) {
//    	
//    	String messageId = "NaN";
//    	
//        try {
//        	
//        	messageId = message.getJMSCorrelationID();
//        	
//            final String content = message.getBody(String.class);
//		      MDC.put("transaction.id", message.getStringProperty("transaction.id"));
//            MDC.put("user.id", principal.getName());
//            
//            final Member member = jsonSerializer.toObject(content);
//            
//            logger.info(content);
//        } catch (JMSException ex) {
//          logger.error	
//        	throw new ContextedRuntimeException(ex.toString()).addContextValue("message id", messageId);
//        }
//    }
}