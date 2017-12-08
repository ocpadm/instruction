package com.nocom.inst.jms;

import javax.jms.JMSDestinationDefinition;


/**
 * 
 * Example taken from Java EE7 Development with Wildfly
 * 
 * @author author
 *
 */
//@JMSDestinationDefinition(
//        name = MemberQueueDefinition.MEMBER_QUEUE,
//        interfaceName = "javax.jms.Queue"
//)
public class MemberQueueDefinition {

    public static final String MEMBER_QUEUE = "java:global/jms/memberQueue";
}