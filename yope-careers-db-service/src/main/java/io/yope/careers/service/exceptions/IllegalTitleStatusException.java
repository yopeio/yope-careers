/**
 *
 */
package io.yope.careers.service.exceptions;

/**
 * @author Massimiliano Gerardi
 *
 */
public class IllegalTitleStatusException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1211102677763772067L;

    /**
     *
     */
    public IllegalTitleStatusException() {
    }

    /**
     * @param message
     */
    public IllegalTitleStatusException(final String message) {
        super(message);
    }

}
