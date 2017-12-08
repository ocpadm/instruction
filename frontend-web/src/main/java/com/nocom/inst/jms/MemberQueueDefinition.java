package com.nocom.inst.jms;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
        name = MemberQueueDefinition.MEMBER_QUEUE,
        interfaceName = "javax.jms.Queue"
)
public class MemberQueueDefinition {

    public static final String MEMBER_QUEUE = "java:jboss/jms/TransTestPut";
}