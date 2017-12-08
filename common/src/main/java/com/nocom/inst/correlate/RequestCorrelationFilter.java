package com.nocom.inst.correlate;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;

/**
 * Checks for correlation id in the header. If it doesn't exist, establishes a new correlation id.
 * 
 * <p>
 * Initialization parameters are as follows:
 * </p>
 * <li>transaction.id.header - header name containing the correlation id. Default is transaction.id.</li>
 * 
 * @author D. Ashmore
 *
 */
@WebFilter(filterName = "RequestCorrelationFilter", urlPatterns = { "/*" })
public class RequestCorrelationFilter implements Filter {

	public static final String DEFAULT_CORRELATION_ID_HEADER_NAME = "transaction.id";
	public static final String INIT_PARM_CORRELATION_ID_HEADER = "transaction.id.header";

	//@Inject
	//private Principal principal;
	
	private String correlationHeaderName;

	public void init(FilterConfig filterConfig) throws ServletException {
		String headerName = filterConfig.getInitParameter(INIT_PARM_CORRELATION_ID_HEADER);
		if (StringUtils.isEmpty(headerName)) {
			correlationHeaderName = DEFAULT_CORRELATION_ID_HEADER_NAME;
		} else {
			correlationHeaderName = headerName.trim();
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String correlationId = httpServletRequest.getHeader(correlationHeaderName);
		if (StringUtils.isEmpty(correlationId)) {
			correlationId = UUID.randomUUID().toString();

		}

		// Record the correlation id with MDC
		MDC.put("transaction.id", correlationId);
		//MDC.put("user.id", (principal!=null?principal.getName():"noname"));


		try {
			chain.doFilter(httpServletRequest, response);
		} finally {
			MDC.clear();
		}
	}

	public void destroy() {
		// NoOp

	}


}
