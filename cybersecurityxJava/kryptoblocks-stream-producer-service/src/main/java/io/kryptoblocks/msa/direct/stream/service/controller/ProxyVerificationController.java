/**
 * The sign up API controller class
 * <p>
 *  
 * @author      Lxur LLC
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.direct.stream.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProxyVerificationController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyVerificationController.class);
	
	@RequestMapping(value = "/proxyverification", method = RequestMethod.GET)
	public ResponseEntity<String> getProxyVerification(HttpServletRequest request, HttpServletResponse response) {
		
		return new ResponseEntity<>("business service proxy verification completed",
				HttpStatus.OK);		
	}

	
	
}
