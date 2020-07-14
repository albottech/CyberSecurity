package io.kryptoblocks.msa.security.service.filter;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.model.AuthenticationResult;
import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.common.util.ExceptionUtil;

import io.kryptoblocks.msa.security.service.business.AuthenticationService;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.kerberos.authentication.KerberosServiceRequestToken;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;
import javax.servlet.http.HttpServletRequestWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class KerberosAuthenticationProcessingFilter.
 */
public class KerberosAuthenticationProcessingFilter extends GenericFilterBean {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KerberosAuthenticationProcessingFilter.class);

	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The authentication URL path. */
	@Value("${service.route.authentication.path}")
	private String authenticationURLPath;
	
	/** The authentication service. */
	@Autowired
	AuthenticationService authenticationService;	

	/** The authentication details source. */
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	
	
	/** The success handler. */
	private AuthenticationSuccessHandler successHandler;
	
	/** The failure handler. */
	private AuthenticationFailureHandler failureHandler;
	
	/** The session strategy. */
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
	
	/** The skip if already authenticated. */
	private boolean skipIfAlreadyAuthenticated = true;

	/**
	 * Do filter.
	 *
	 * @param req the req
	 * @param res the res
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;		
		String operationName = request.getMethod();
		 

		/*if (skipIfAlreadyAuthenticated) {
			String curUrl = ((HttpServletRequest) req).getRequestURL().toString();
			LOGGER.debug("From Kerberos Authentication Processing Filter: URL : {}", curUrl);
			if (curUrl.contains(authenticationURLPath)) {
				LOGGER.debug(
						"From Kerberos Authentication Processing Filter: current URL is an authentication request");
				// check on user name password credential
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					loginUser = objectMapper.readValue(request.getInputStream(), LoginUser.class);
					String jsonInUser = objectMapper.writeValueAsString(loginUser);
					requestWrapper = new HeaderMapRequestWrapper(request);
					requestWrapper.addHeader("loginUser", jsonInUser);
					LOGGER.debug("Input body user  ### :  {}", jsonInUser);
				} catch (Exception e) {
					requestWrapper = null;
					LOGGER.debug("Ignore this exception");
				}
			}
		}*/
		// check on existing security token or request with user object
		if ((request.getHeader(serviceAuthJwtToken) != null)) {
			LOGGER.debug("bypass");
				chain.doFilter(request, response);			
		} else {
			String token = request.getHeader("Authorization");
			if (token != null && (token.startsWith("Negotiate ") || token.startsWith("Kerberos "))) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("received negotiate header token for request " + request.getRequestURL() + ": " + token);
				}
				
				AuthenticationResult userNamePasswordAuthentication;
				try {					
					userNamePasswordAuthentication = authenticationService.kerberosTicketAuthentication(request, token, null);
					if (userNamePasswordAuthentication.getToken() != null) {
						HttpHeaders responseHeaders = new HttpHeaders();
						responseHeaders.add(serviceAuthJwtToken, userNamePasswordAuthentication.getToken());						
					} else {
						//throw ExceptionUtil.generateApiException(userNamePasswordAuthentication.getMsg(), operationName,
						//		ExceptionHandlerType.BUSINESS_EXCEPTION_HANDLER.getValue());
					}

				} catch (BusinessException be) {
					//throw ExceptionUtil.generateApiExceptionFromBusinessException(be, operationName);
				}
				
				chain.doFilter(request, response);
			}
		}

		chain.doFilter(request, response);

	}

	/**
	 * After properties set.
	 *
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		//Assert.notNull(this.authenticationManager, "authenticationManager must be specified");
	}

	 

	/**
	 * <p>
	 * This handler is called after a successful authentication. One can add
	 * additional authentication behavior by setting this.
	 * </p>
	 * <p>
	 * Default is null, which means nothing additional happens
	 * </p>
	 *
	 * @param successHandler
	 *            the authentication success handler
	 */
	public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
		this.successHandler = successHandler;
	}

	/**
	 * <p>
	 * This handler is called after a failure authentication. In most cases you only
	 * get Kerberos/SPNEGO failures with a wrong server or network configurations
	 * and not during runtime. If the client encounters an error, he will just stop
	 * the communication with server and therefore this handler will not be called
	 * in this case.
	 * </p>
	 * <p>
	 * Default is null, which means that the Filter returns the HTTP 500 code
	 * </p>
	 *
	 * @param failureHandler
	 *            the authentication failure handler
	 */
	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}

	/**
	 * Should Kerberos authentication be skipped if a user is already authenticated
	 * for this request (e.g. in the HTTP session).
	 *
	 * @param skipIfAlreadyAuthenticated
	 *            default is true
	 */
	public void setSkipIfAlreadyAuthenticated(boolean skipIfAlreadyAuthenticated) {
		this.skipIfAlreadyAuthenticated = skipIfAlreadyAuthenticated;
	}

	/**
	 * The session handling strategy which will be invoked immediately after an
	 * authentication request is successfully processed by the
	 * <tt>AuthenticationManager</tt>. Used, for example, to handle changing of the
	 * session identifier to prevent session fixation attacks.
	 *
	 * @param sessionStrategy
	 *            the implementation to use. If not set a null implementation is
	 *            used.
	 */
	public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	/**
	 * Sets the authentication details source.
	 *
	 * @param authenticationDetailsSource
	 *            the authentication details source
	 */
	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}
	
	
}
