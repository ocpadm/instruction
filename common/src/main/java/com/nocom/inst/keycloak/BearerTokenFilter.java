package com.nocom.inst.keycloak;

import java.io.IOException;
import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.MDC;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

/**
 * JAX-RS client side request filter registered on JAX-RS ClientBuilder. Abstracting JWT token from keycloak JAAS principal and inject the token on HTTP header attribute.
 *
 * @author author
 * @version $Revision$, $Date$
 */
@Provider
public class BearerTokenFilter implements ClientRequestFilter {

	@Inject
	private HttpServletRequest servletRequest;

	public void filter(ClientRequestContext requestContext) throws IOException {

		MultivaluedMap<String, Object> headers = requestContext.getHeaders();

//		final Principal userPrincipal = servletRequest.getUserPrincipal();
//
//		if (userPrincipal instanceof KeycloakPrincipal) {
//
//			KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal;
//
//			AccessToken token = kp.getKeycloakSecurityContext().getToken();
//
//			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + kp.getKeycloakSecurityContext().getTokenString());
//
//		}

		// add id to track user transaction boundaries
		headers.add("transaction.id", MDC.get("transaction.id"));
		//headers.add("user.id", MDC.get("user.id"));

	}
}