/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nocom.inst.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import com.nocom.inst.jms.MemberQueueProducer;
import com.nocom.inst.model.Member;
import com.nocom.inst.rest.MemberResourceRESTService;
import com.nocom.inst.websockets.MemberClientEndpoint;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@SessionScoped
@Model
public class MemberController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private MemberResourceRESTService memberRESTService;

	@Inject
	private MemberQueueProducer memberQueueProducer;

	@Inject
	MemberClientEndpoint memberClientEndPoint;

	@Produces
	@Named
	private Member newMember;

	private List<Member> members;

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
		newMember.setName("test");
		newMember.setEmail("no@com");
		newMember.setPhoneNumber("12345678901");

		// members = memberRESTService.listAllMembers();
	}

	public void registerThroughREST() throws Exception {

		Response response = null;

		try {

			response = memberRESTService.createMember(newMember);

			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, response.getStatusInfo().getReasonPhrase(), "Registration through REST unsuccessful");
				facesContext.addMessage("Registration through REST unsuccessful", m);
			} else {
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered through REST!", "Registration successful");
				facesContext.addMessage("Registration successful", m);
				response.close();
				members = memberRESTService.listAllMembers();
				initNewMember();
			}

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration through REST unsuccessful");
			facesContext.addMessage("Registration through REST unsuccessful", m);
		}

	}

	public void registerThroughJMS() throws Exception {
		try {

			memberQueueProducer.createMember(newMember);

			initNewMember();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration through JMS unsuccessful");
			facesContext.addMessage("Registration through JMS unsuccessful", m);
		}
	}

	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, String.format("%s registered through JMS!", member.getName()), "Registration successful");
		facesContext.addMessage("Registration successful", m);
		members = memberRESTService.listAllMembers();
	}

	@Produces
	@Named
	public List<Member> getMembers() {
		return members;
	}

	@Produces
	@Named
	public String getEndpoint() {
		return System.getProperty("MemberEndpointURL");
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
