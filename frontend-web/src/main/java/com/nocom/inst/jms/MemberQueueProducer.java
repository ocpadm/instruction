package com.nocom.inst.jms;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.nocom.inst.model.Member;

/**
 * 
 * Sample JMS producer (client) registering a completion listener to enabling asynchronous handling of responses.
 * 
 * Example taken from Java EE7 Development with Wildfly
 * 
 * @author author
 *
 */
@ApplicationScoped
public class MemberQueueProducer {
//
//    @Inject
//    @JMSConnectionFactory("java:jboss/jms/TransXacf_local")
//    private JMSContext context;

//    @Inject
//    private MemberCompletionListener memberCompletionListener;
//    
	@Inject
	private Event<Member> addMemberEvent;


//    @Resource(mappedName = MemberQueueDefinition.MEMBER_QUEUE)
//    private Queue syncQueue;

    public void createMember(Member member) {
    	
    	addMemberEvent.fire(member);
//        context.createProducer()
//                .setAsync(memberCompletionListener)
//                .send(syncQueue, jsonParser.toString(member));
    }
}