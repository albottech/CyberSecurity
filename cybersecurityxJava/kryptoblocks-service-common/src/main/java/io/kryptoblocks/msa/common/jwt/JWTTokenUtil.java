package io.kryptoblocks.msa.common.jwt;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.StringEncryptionUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class JWTTokenUtil.
 */
public class JWTTokenUtil {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3301605591108950415L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTTokenUtil.class);

	/** The Constant CLAIM_KEY_USERNAME. */
	public static final String CLAIM_KEY_USERNAME = "sub";
	
	/** The Constant CLAIM_KEY_AUDIENCE. */
	public static final String CLAIM_KEY_AUDIENCE = "audience";
	
	/** The Constant CLAIM_KEY_CREATED. */
	public static final String CLAIM_KEY_CREATED = "created";
	
	/** The Constant CLAIM_KEY_EXPIRED. */
	public static final String CLAIM_KEY_EXPIRED = "exp";

	/** The Constant AUDIENCE_UNKNOWN. */
	public static final String AUDIENCE_UNKNOWN = "unknown";
	
	/** The Constant AUDIENCE_WEB. */
	public static final String AUDIENCE_WEB = "web";
	
	/** The Constant AUDIENCE_MOBILE. */
	public static final String AUDIENCE_MOBILE = "mobile";
	
	/** The Constant AUDIENCE_TABLET. */
	public static final String AUDIENCE_TABLET = "tablet";
	
	/** The Constant CLAIM_DATA_AUTHORIZATION_GRP_LIST. */
	public static final String CLAIM_DATA_AUTHORIZATION_GRP_LIST = "authList";

	/** The secret. */
	@Value("${jwt.secret}")
	private String secret;

	/** The expiration in minutes. */
	@Value("${jwt.expiration.in.minutes}")
	private Long expirationInMinutes;
	
	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The string encryption util. */
	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	/**
	 * Gets the username from token.
	 *
	 * @param token the token
	 * @return the username from token
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * Gets the created date from token.
	 *
	 * @param token the token
	 * @return the created date from token
	 */
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	/**
	 * Gets the audience from token.
	 *
	 * @param token the token
	 * @return the audience from token
	 */
	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = getClaimsFromToken(token);
			audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	/**
	 * Checks if is token expired.
	 *
	 * @param token the token
	 * @return the boolean
	 */
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Checks if is created before last password reset.
	 *
	 * @param created the created
	 * @param lastPasswordReset the last password reset
	 * @return the boolean
	 */
	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	/**
	 * Generate audience.
	 *
	 * @param device the device
	 * @return the string
	 */
	private String generateAudience(Device device) {
		String audience = AUDIENCE_UNKNOWN;
		if (device.isNormal()) {
			audience = AUDIENCE_WEB;
		} else if (device.isTablet()) {
			audience = AUDIENCE_TABLET;
		} else if (device.isMobile()) {
			audience = AUDIENCE_MOBILE;
		}
		return audience;
	}

	/**
	 * Ignore token expiration.
	 *
	 * @param token the token
	 * @return the boolean
	 */
	private Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
	}

	/**
	 * Generate token.
	 *
	 * @param userMap the user map
	 * @param device the device
	 * @return the string
	 */
	public String generateToken(Map<String, Object> userMap, Device device) {
		/*
		 * Map<String, Object> claims = new HashMap<>(); claims.put(CLAIM_KEY_USERNAME,
		 * user.getUsername()); claims.put(CLAIM_KEY_AUDIENCE,
		 * generateAudience(device)); claims.put(CLAIM_DATA_AUTHORIZATION_GRP_LIST,
		 * converAuthorityAsString(user.getAuthorities())); final Date createdDate = new
		 * Date(); claims.put(CLAIM_KEY_CREATED, createdDate);
		 */
		return doGenerateToken(userMap);
	}

	/**
	 * Do generate token.
	 *
	 * @param claims the claims
	 * @return the string
	 */
	private String doGenerateToken(Map<String, Object> claims) {
		LOGGER.debug("Generating JWT toke from authentication");
		Date createdDate = new Date();
		String createdTimeAsString = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(createdDate);
		final Date expirationDate = new Date(new Date().getTime() + expirationInMinutes * 60 * 1000);
		String expiryTimeAsString = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(expirationDate);
		LOGGER.debug("JWT token experation time : {}", expirationDate); 
		claims.put(UserMap.CREATED_TIME, createdTimeAsString);
		claims.put(UserMap.EXPIRY_TIME, expiryTimeAsString); 
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	/**
	 * Can token be refreshed.
	 *
	 * @param token the token
	 * @param lastPasswordReset the last password reset
	 * @return the boolean
	 */
	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	/**
	 * Refresh token.
	 *
	 * @param token the token
	 * @return the string
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = doGenerateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * Validate token.
	 *
	 * @param token the token
	 * @param user the user
	 * @return the boolean
	 */
	public Boolean validateToken(String token, String user) {
		// JwtUser user = (JwtUser) userDetails;
		final String username = getUsernameFromToken(token);
		// final Date created = getCreatedDateFromToken(token);
		// final Date expiration = getExpirationDateFromToken(token);
		return (username.equals(user) && !isTokenExpired(token));
	}

	/**
	 * Gets the claims from token.
	 *
	 * @param token the token
	 * @return the claims from token
	 */
	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * Gets the expiration date from token.
	 *
	 * @param token the token
	 * @return the expiration date from token
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * Extract user from request.
	 *
	 * @param request the request
	 * @return the string
	 */
	public String extractUserFromRequest(HttpServletRequest request) {
		//TODO 
		//validate token
		String token = request.getHeader(serviceAuthJwtToken);		 
		Claims claims = getClaimsFromToken(token);
		return  (String) claims.get(UserMap.EMAIL);	
	}
	
	/**
	 * Check signup confirmation.
	 *
	 * @param receivedToken the received token
	 * @param persistedToken the persisted token
	 * @return true, if successful
	 */
	public boolean checkSignupConfirmation(String receivedToken, String persistedToken) {
		// TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);

		Claims persistedClaims = getClaimsFromToken(persistedToken);
		Claims receivedClaims = getClaimsFromToken(receivedToken);
		String receivedVerificationKey = (String) receivedClaims.get(UserMap.VERIFICATION_KEY);
		String persistedVerificationKey = (String) persistedClaims.get(UserMap.VERIFICATION_KEY);

		if (receivedVerificationKey.equalsIgnoreCase(persistedVerificationKey)) {
			String signupConfirm = (String) receivedClaims.get(UserMap.SIGN_UP_CONFIRM_NOTIFICATION);
			if (new Boolean(signupConfirm).booleanValue()) {
				String currentDate = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(new Date());
				String signUpConfirmationTime = (String) receivedClaims.get(UserMap.SIGN_UP_CONFIRM_CREATED_TIME);
				long elapesedTime = DateType.DATE_FORMAT_WITH_NANO_SECONDS
						.getDateDifferenceInMilliSeconds(signUpConfirmationTime, currentDate);
				String confirmTTL = (String) receivedClaims.get(UserMap.SIGN_UP_CONFIRM_EXPIRATION_TIME);
				long configmrTTLAsLong = new Long(confirmTTL).longValue();
				if (configmrTTLAsLong > elapesedTime) {
					return true;
				}
			}
		}
		return false;
	}

}
