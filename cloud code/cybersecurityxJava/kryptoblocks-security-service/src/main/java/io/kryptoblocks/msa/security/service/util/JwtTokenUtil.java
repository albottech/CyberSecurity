/**
 * The JWT token util class. This class represent a JWT token util operations
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.kryptoblocks.msa.security.service.common.TimeProvider;
import io.kryptoblocks.msa.security.service.model.Authority;
import io.kryptoblocks.msa.security.service.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class JwtTokenUtil.
 */
@Component
public class JwtTokenUtil implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3301605591108950415L;
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

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

    /** The time provider. */
    @Autowired
    private TimeProvider timeProvider;

    /** The secret. */
    @Value("${jwt.secret}")
    private String secret;

    /** The expiration in minutes. */
    @Value("${jwt.expiration.in.minutes}")
    private Long expirationInMinutes;
    
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
     * Gets the claims from token.
     *
     * @param token the token
     * @return the claims from token
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Checks if is token expired.
     *
     * @param token the token
     * @return the boolean
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(timeProvider.now());
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
     * @param user the user
     * @param device the device
     * @return the string
     */
    public String generateToken(User user, Device device) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_DATA_AUTHORIZATION_GRP_LIST, converAuthorityAsString(user.getAuthorities()));
        final Date createdDate = timeProvider.now();
        claims.put(CLAIM_KEY_CREATED, createdDate);
        return doGenerateToken(claims);
    }
    
    /**
     * Conver authority as string.
     *
     * @param authList the auth list
     * @return the string
     */
    private String converAuthorityAsString(List<Authority> authList) {
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	for(Authority auth: authList) {
    		String authorityName = auth.getAuthorityname();
    		if(authorityName !=  null) {
    			stringBuilder.append(auth.getAuthorityname());
    			stringBuilder.append(","); 
    		}
    	}    	
    	return stringBuilder.toString();
    } 

    /**
     * Do generate token.
     *
     * @param claims the claims
     * @return the string
     */
    private String doGenerateToken(Map<String, Object> claims) {
    	LOGGER.debug("Generating JWT toke from authentication");
        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
        final Date expirationDate = new Date(createdDate.getTime() + expirationInMinutes * 60 * 1000);
        LOGGER.debug("JWT token experation time : {}", expirationDate);
        System.out.println("doGenerateToken " + createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
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
            claims.put(CLAIM_KEY_CREATED, timeProvider.now());
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
    public Boolean validateToken(String token, User user) {
        //JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        //final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token));
    }
}