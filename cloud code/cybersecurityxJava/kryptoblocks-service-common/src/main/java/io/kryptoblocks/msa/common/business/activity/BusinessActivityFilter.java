package io.kryptoblocks.msa.common.business.activity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.util.ExceptionUtils;
import org.springframework.web.util.UrlPathHelper;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.StringEncryptionUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.event.BusinessActivityEventSender;
import io.kryptoblocks.msa.data.nfr.business.activity.key.BusinessActivityKey;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.RequestDetails;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.ResponseDetails;
 
// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityFilter.
 */
public class BusinessActivityFilter implements Filter {

	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivityFilter.class);
	
	/** The Constant ENCRYPTED_BODY_CONTENT_MSG_PART. */
	private static final String ENCRYPTED_BODY_CONTENT_MSG_PART = "encrypted body content : ";
	
	/** The Constant RESERVED_DATA_NAME. */
	private static final String [] RESERVED_DATA_NAME = {"username", "password"};
	
	/** The service name. */
	@Value("${spring.application.name}")	
	private String serviceName;
	
	/** The business activity entity. */
	@Autowired
	BusinessActivity businessActivityEntity;
	
	/** The request details. */
	@Autowired
	RequestDetails requestDetails;
	
	/** The response details. */
	@Autowired
	ResponseDetails responseDetails;
	
	/** The string encryption util. */
	@Autowired
	StringEncryptionUtil stringEncryptionUtil;
	
	/** The business activity event sender. */
	@Autowired
	BusinessActivityEventSender businessActivityEventSender;

	
	/**
	 * Inits the.
	 *
	 * @param filterConfig the filter config
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// not required 
		
	}

	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) 
				throws IOException, ServletException {
	try {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
	
	        Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);
	        BufferedRequestWrapper bufferedReqest = new BufferedRequestWrapper(httpServletRequest);  
	        BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);
	        
	        requestDetails.setOperation(new UrlPathHelper().getPathWithinApplication(httpServletRequest).replace('/', ' ').trim());
	         
	        requestDetails.setParameters(requestMap);	         
	               
	        requestDetails.setBody(getdisplayBodyContent(bufferedReqest));
	        
	        requestDetails.setRemoteAddress(httpServletRequest.getRemoteAddr());
	        String startTime = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();        
	        
	        
	        LOGGER.debug("business activity request path translated: {}",new UrlPathHelper().getPathWithinApplication(httpServletRequest));
	        LOGGER.debug("business activity request context path: {}",httpServletRequest.getContextPath());
	        chain.doFilter (bufferedReqest, bufferedResponse); 
	        responseDetails.setContent(getdisplayResponse(bufferedResponse));
	        String endTime = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
	        businessActivityEntity.setRequestDetails(requestDetails);
	        businessActivityEntity.setResponseDetails(responseDetails);
	        businessActivityEntity.setLocation(serviceName + ":" +  requestDetails.getOperation() );
	        BusinessActivityKey key = new BusinessActivityKey();
	        key.setId(java.util.UUID.randomUUID().toString());
	        key.setServiceName(serviceName);
	        businessActivityEntity.setKey(key);
	        businessActivityEntity.setDurationInMilliSeconds(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateDifferenceInMilliSecondsAsString(startTime, endTime));
	        businessActivityEventSender.sendServiceBusinessActivity(businessActivityEntity);
	} catch( Exception e ) {
		LOGGER.error(ExceptionUtils.getExceptionMessage(e));
	}
	}

	
	/**
	 * Gets the typesafe request map.
	 *
	 * @param request the request
	 * @return the typesafe request map
	 */
	private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
		Map<String, String> typesafeRequestMap = new HashMap<>();
		Enumeration<?> requestParamNames = request.getParameterNames();
		while (requestParamNames.hasMoreElements()) {
			String requestParamName = (String)requestParamNames.nextElement();
			String requestParamValue = getdisplayBodyValue(request.getParameter(requestParamName));
			typesafeRequestMap.put(requestParamName, requestParamValue);
		}
		return typesafeRequestMap;
	}	
	
	
	/**
	 * Destroy.
	 */
	@Override
	public void destroy() {
		// not required 
					//throw new UnsupportedOperationException();
	}

	
	/**
	 * The Class BufferedRequestWrapper.
	 */
	private static final class BufferedRequestWrapper extends HttpServletRequestWrapper {

         
		
		/** The baos. */
		private ByteArrayOutputStream baos = null;
        
        /** The buffer. */
        private byte[] buffer = null;
 

        /**
         * Instantiates a new buffered request wrapper.
         *
         * @param req the req
         * @throws IOException Signals that an I/O exception has occurred.
         */
        public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
            super(req);
            // Read InputStream and store its content in a buffer.
            InputStream is = req.getInputStream();
            this.baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int letti;
            while ((letti = is.read(buf)) > 0) {
                this.baos.write(buf, 0, letti);
            }
            this.buffer = this.baos.toByteArray();
        }

        
        /**
         * Gets the input stream.
         *
         * @return the input stream
         */
        @Override
        public ServletInputStream getInputStream() {
             
        	return new BufferedServletInputStream(new ByteArrayInputStream(this.buffer));
             
        }

        

        /**
         * Gets the request body.
         *
         * @return the request body
         * @throws IOException Signals that an I/O exception has occurred.
         */
        String getRequestBody() throws IOException  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
            String line = null;
            StringBuilder inputBuffer = new StringBuilder();
            do {
            	line = reader.readLine();
            	if (null != line) {
            		inputBuffer.append(line.trim());
            	}
            } while (line != null);
            reader.close();
            return inputBuffer.toString().trim();
        }

    }


    /**
     * The Class BufferedServletInputStream.
     */
    private static final class BufferedServletInputStream extends ServletInputStream {

        /** The bais. */
        private ByteArrayInputStream bais;

        /**
         * Instantiates a new buffered servlet input stream.
         *
         * @param bais the bais
         */
        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        /**
         * Available.
         *
         * @return the int
         */
        @Override
        public int available() {
            return this.bais.available();
        }

        /**
         * Read.
         *
         * @return the int
         */
        @Override
        public int read() {
            return this.bais.read();
        }

        /**
         * Read.
         *
         * @param buf the buf
         * @param off the off
         * @param len the len
         * @return the int
         */
        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

		/**
		 * Checks if is finished.
		 *
		 * @return true, if is finished
		 */
		@Override
		public boolean isFinished() {
			// not required 
			return false;
			//throw new UnsupportedOperationException();
		}

		/**
		 * Checks if is ready.
		 *
		 * @return true, if is ready
		 */
		@Override
		public boolean isReady() {
			// not required 
			return false;
			//throw new UnsupportedOperationException();
		}

		/**
		 * Sets the read listener.
		 *
		 * @param arg0 the new read listener
		 */
		@Override
		public void setReadListener(ReadListener arg0) {
			// not required 
			//throw new UnsupportedOperationException();
			
		}
 

    }
	
    /**
     * The Class TeeServletOutputStream.
     */
    public class TeeServletOutputStream extends ServletOutputStream {

    	/** The target stream. */
	    private final TeeOutputStream targetStream;

    	/**
	     * Instantiates a new tee servlet output stream.
	     *
	     * @param one the one
	     * @param two the two
	     */
	    public TeeServletOutputStream( OutputStream one, OutputStream two ) {
    		targetStream = new TeeOutputStream( one, two);
    	}
    	
		/**
		 * Write.
		 *
		 * @param arg0 the arg 0
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void write(int arg0) throws IOException {
			this.targetStream.write(arg0);
		}
		
		/**
		 * Flush.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void flush() throws IOException {
			super.flush();
			this.targetStream.flush();
		}

		/**
		 * Close.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void close() throws IOException {
			super.close();
			this.targetStream.close();
		}

		/**
		 * Checks if is ready.
		 *
		 * @return true, if is ready
		 */
		@Override
		public boolean isReady() {
			// not required 
			return false;
			//throw new UnsupportedOperationException();
		}

		/**
		 * Sets the write listener.
		 *
		 * @param arg0 the new write listener
		 */
		@Override
		public void setWriteListener(WriteListener arg0){
			// not required 
			//throw new UnsupportedOperationException();
			
		}		
    }
    
    
    
    /**
     * The Class BufferedResponseWrapper.
     */
    public class BufferedResponseWrapper implements HttpServletResponse {

    	/** The original. */
	    HttpServletResponse original;
    	
	    /** The tee. */
	    TeeServletOutputStream tee;
    	
	    /** The bos. */
	    ByteArrayOutputStream bos;

    	/**
	     * Instantiates a new buffered response wrapper.
	     *
	     * @param response the response
	     */
	    public BufferedResponseWrapper(HttpServletResponse response) {
    		original = response;
    	}

    	/**
	     * Gets the content.
	     *
	     * @return the content
	     */
	    public String getContent() {
			return bos.toString();
		}
    	
    	/**
	     * Gets the writer.
	     *
	     * @return the writer
	     * @throws IOException Signals that an I/O exception has occurred.
	     */
	    public PrintWriter getWriter() throws IOException {
    		return original.getWriter();
    	}

    	/**
	     * Gets the output stream.
	     *
	     * @return the output stream
	     * @throws IOException Signals that an I/O exception has occurred.
	     */
	    public ServletOutputStream getOutputStream() throws IOException {
    		if( tee == null ){
    			bos = new ByteArrayOutputStream();
    			tee = new TeeServletOutputStream( original.getOutputStream(), bos );
    		}
    		return tee;

    	}

		/**
		 * Gets the character encoding.
		 *
		 * @return the character encoding
		 */
		@Override
		public String getCharacterEncoding() {
			return original.getCharacterEncoding();
		}

		/**
		 * Gets the content type.
		 *
		 * @return the content type
		 */
		@Override
		public String getContentType() {
			return original.getContentType();
		}

		/**
		 * Sets the character encoding.
		 *
		 * @param charset the new character encoding
		 */
		@Override
		public void setCharacterEncoding(String charset) {
			original.setCharacterEncoding(charset);
		}

		/**
		 * Sets the content length.
		 *
		 * @param len the new content length
		 */
		@Override
		public void setContentLength(int len) {
			original.setContentLength(len);
		}

		/**
		 * Sets the content type.
		 *
		 * @param type the new content type
		 */
		@Override
		public void setContentType(String type) {
			original.setContentType(type);
		}

		/**
		 * Sets the buffer size.
		 *
		 * @param size the new buffer size
		 */
		@Override
		public void setBufferSize(int size) {
			original.setBufferSize(size);
		}

		/**
		 * Gets the buffer size.
		 *
		 * @return the buffer size
		 */
		@Override
		public int getBufferSize() {
			return original.getBufferSize();
		}

		/**
		 * Flush buffer.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void flushBuffer() throws IOException {
			tee.flush();
		}

		/**
		 * Reset buffer.
		 */
		@Override
		public void resetBuffer() {
			original.resetBuffer();
		}

		/**
		 * Checks if is committed.
		 *
		 * @return true, if is committed
		 */
		@Override
		public boolean isCommitted() {
			return original.isCommitted();
		}

		/**
		 * Reset.
		 */
		@Override
		public void reset() {
			original.reset();
		}

		/**
		 * Sets the locale.
		 *
		 * @param loc the new locale
		 */
		@Override
		public void setLocale(Locale loc) {
			original.setLocale(loc);
		}

		/**
		 * Gets the locale.
		 *
		 * @return the locale
		 */
		@Override
		public Locale getLocale() {
			return original.getLocale();
		}

		/**
		 * Adds the cookie.
		 *
		 * @param cookie the cookie
		 */
		@Override
		public void addCookie(Cookie cookie) {
			original.addCookie(cookie);
		}

		/**
		 * Contains header.
		 *
		 * @param name the name
		 * @return true, if successful
		 */
		@Override
		public boolean containsHeader(String name) {
			return original.containsHeader(name);
		}

		/**
		 * Encode URL.
		 *
		 * @param url the url
		 * @return the string
		 */
		@Override
		public String encodeURL(String url) {
			return original.encodeURL(url);
		}

		/**
		 * Encode redirect URL.
		 *
		 * @param url the url
		 * @return the string
		 */
		@Override
		public String encodeRedirectURL(String url) {
			return original.encodeRedirectURL(url);
		}

		/**
		 * Encode url.
		 *
		 * @param url the url
		 * @return the string
		 */
		@SuppressWarnings("deprecation")
		@Override
		public String encodeUrl(String url) {
			return original.encodeUrl(url);
		}

		/**
		 * Encode redirect url.
		 *
		 * @param url the url
		 * @return the string
		 */
		@SuppressWarnings("deprecation")
		@Override
		public String encodeRedirectUrl(String url) {
			return original.encodeRedirectUrl(url);
		}

		/**
		 * Send error.
		 *
		 * @param sc the sc
		 * @param msg the msg
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void sendError(int sc, String msg) throws IOException {
			original.sendError(sc, msg);
		}

		/**
		 * Send error.
		 *
		 * @param sc the sc
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void sendError(int sc) throws IOException {
			original.sendError(sc);
		}

		/**
		 * Send redirect.
		 *
		 * @param location the location
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void sendRedirect(String location) throws IOException {
			original.sendRedirect(location);
		}

		/**
		 * Sets the date header.
		 *
		 * @param name the name
		 * @param date the date
		 */
		@Override
		public void setDateHeader(String name, long date) {
			original.setDateHeader(name, date);
		}

		/**
		 * Adds the date header.
		 *
		 * @param name the name
		 * @param date the date
		 */
		@Override
		public void addDateHeader(String name, long date) {
			original.addDateHeader(name, date);
		}

		/**
		 * Sets the header.
		 *
		 * @param name the name
		 * @param value the value
		 */
		@Override
		public void setHeader(String name, String value) {
			original.setHeader(name, value);
		}

		/**
		 * Adds the header.
		 *
		 * @param name the name
		 * @param value the value
		 */
		@Override
		public void addHeader(String name, String value) {
			original.addHeader(name, value);
		}

		/**
		 * Sets the int header.
		 *
		 * @param name the name
		 * @param value the value
		 */
		@Override
		public void setIntHeader(String name, int value) {
			original.setIntHeader(name, value);
		}

		/**
		 * Adds the int header.
		 *
		 * @param name the name
		 * @param value the value
		 */
		@Override
		public void addIntHeader(String name, int value) {
			original.addIntHeader(name, value);
		}

		/**
		 * Sets the status.
		 *
		 * @param sc the new status
		 */
		@Override
		public void setStatus(int sc) {
			original.setStatus(sc);
		}

		/**
		 * Sets the status.
		 *
		 * @param sc the sc
		 * @param sm the sm
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void setStatus(int sc, String sm) {
			original.setStatus(sc, sm);
		}

		/**
		 * Sets the content length long.
		 *
		 * @param arg0 the new content length long
		 */
		@Override
		public void setContentLengthLong(long arg0) {
			// not required 
			//throw new UnsupportedOperationException();
			
		}

		/**
		 * Gets the header.
		 *
		 * @param arg0 the arg 0
		 * @return the header
		 */
		@Override
		public String getHeader(String arg0) {
			 
			// not required 
			return null;
			//throw new UnsupportedOperationException();
		}

		/**
		 * Gets the header names.
		 *
		 * @return the header names
		 */
		@Override
		public Collection<String> getHeaderNames() {
			 
			// not required 
			return null;
						//throw new UnsupportedOperationException();
		}

		/**
		 * Gets the headers.
		 *
		 * @param arg0 the arg 0
		 * @return the headers
		 */
		@Override
		public Collection<String> getHeaders(String arg0) {
			// not required 
			return null;
						//throw new UnsupportedOperationException();
		}

		/**
		 * Gets the status.
		 *
		 * @return the status
		 */
		@Override
		public int getStatus() {			 
			// not required 
			return 0;
						//throw new UnsupportedOperationException();
		}

    }
    
    /**
     * Gets the display body content.
     *
     * @param bufferedReqest the buffered reqest
     * @return the display body content
     */
    private String getdisplayBodyContent(BufferedRequestWrapper bufferedReqest) {
    	String returnValue = null;
    	try {
    	String body = bufferedReqest.getRequestBody();
    	    
    	if(stringContainsItemFromReservedWords(body)){
	        					 
				String cipherText = stringEncryptionUtil.encrypt(body);
				returnValue = "body contain sensitive data: " + ":" + ENCRYPTED_BODY_CONTENT_MSG_PART  + cipherText;
	        }else {	        
	        	returnValue = bufferedReqest.getRequestBody();
	        }
    	}catch(Exception e) {
    		 LOGGER.error("exception in getdisplayBodyContent method: {}", org.apache.commons.lang3.exception.ExceptionUtils.getMessage(e));
    	 }
    	 
    	return returnValue;
    	
    	
    }
    
    /**
     * Gets the display response.
     *
     * @param bufferedResponse the buffered response
     * @return the display response
     */
    private String getdisplayResponse(BufferedResponseWrapper bufferedResponse) {
    	String returnValue = null;
    	try {
    	String response = bufferedResponse.getContent();
    	   
    	if(stringContainsItemFromReservedWords(response)){
	        					 
				String cipherText = stringEncryptionUtil.encrypt(response);
				returnValue = "response contain sensitive data: " + ":" + ENCRYPTED_BODY_CONTENT_MSG_PART  + cipherText;
	        }else {	        
	        	returnValue = bufferedResponse.getContent();
	        }
    	}catch(Exception e) {
    		 LOGGER.error("exception in getdisplayResponse method: {}", org.apache.commons.lang.exception.ExceptionUtils.getMessage(e));
    	 }
    	 
    	return returnValue;
    	
    	
    }
    
    /**
     * Gets the display body value.
     *
     * @param value the value
     * @return the display body value
     */
    private String getdisplayBodyValue(String value) {
    	String returnValue = null;
    	try {
    	 
    	  
    		if(stringContainsItemFromReservedWords(value)){
	        					 
				String cipherText = stringEncryptionUtil.encrypt(value);
				returnValue = "response contain sensitive data: " + ":" + ENCRYPTED_BODY_CONTENT_MSG_PART  + cipherText;
	        }else {	        
	        	returnValue = value;
	        }
    	}catch(Exception e) {
    		 LOGGER.error("exception in getdisplayResponse method: {}", org.apache.commons.lang.exception.ExceptionUtils.getMessage(e));
    	 }
    	 
    	return returnValue;
    	
    	
    }
    
    /**
     * String contains item from reserved words.
     *
     * @param inputStr the input str
     * @return true, if successful
     */
    private boolean stringContainsItemFromReservedWords(String inputStr) {
        return Arrays.stream(RESERVED_DATA_NAME	).parallel().anyMatch(inputStr::contains);
    }
    
    
    
    
    

	
}
