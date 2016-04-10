/**
 *
 */
package io.yope.careers.service.exceptions;

/**
 * @author Massimiliano Gerardi
 *
 */
public class UserNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1211102677763772067L;

    /**
     *
     */
    public UserNotFoundException() {
    }

    /**
     * @param message
     */
    public UserNotFoundException(final String message) {
        super(message);
    }

}
