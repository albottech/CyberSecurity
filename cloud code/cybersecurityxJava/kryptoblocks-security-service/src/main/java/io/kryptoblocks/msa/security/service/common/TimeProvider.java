/**
 * The Time provider component class. This class represent a simple time provider component
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */

package io.kryptoblocks.msa.security.service.common;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

 
// TODO: Auto-generated Javadoc
/**
 * The Class TimeProvider.
 */
@Component
public class TimeProvider implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3301695478208950415L;

    /**
     * Now.
     *
     * @return the date
     */
    public Date now() {
        return new Date();
    }
}
