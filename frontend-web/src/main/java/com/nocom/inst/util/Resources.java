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
package com.nocom.inst.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import com.nocom.inst.keycloak.BearerTokenFilter;
import com.nocom.inst.rest.MemberResourceRESTService;
import com.nocom.inst.websockets.MemberClientEndpoint;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 *
 * <p>
 * Example injection on a managed bean field:
 * </p>
 *
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {

	@Inject
	BearerTokenFilter bearerTokenFilter;

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * 
	 * Construct RestEasy proxied service endpoint.
	 * 
	 * @param CDI
	 *            Injection Point
	 * @return JAX-RS WebTarget for resourcing members
	 */
	@Produces
	MemberResourceRESTService getRESTClient(InjectionPoint ip) {

		return new ResteasyClientBuilder().connectionPoolSize(20).build().target(System.getProperty("MemberEndpointURL")).register(bearerTokenFilter).proxy(MemberResourceRESTService.class);

	}

	/**
	 * 
	 * construct WebSockets client endpointt.
	 * 
	 * @param CDI
	 *            Injection Point
	 * @return WebSockets client endpoint
	 */
	@Produces
	MemberClientEndpoint getWebSocketsClient(InjectionPoint ip) {
		
		try {
			return new MemberClientEndpoint(new URI(System.getProperty("MemberWebSocketsEndpointURL")));

		} catch (URISyntaxException e) {
			throw new ContextedRuntimeException(e).addContextValue("URI	", System.getProperty("MemberWebSocketsEndpointURL"));
		}

	}

}
