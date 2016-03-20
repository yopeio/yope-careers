/**
 *
 */
package io.yope.careers.service.exceptions;

/**
 * @author Massimiliano Gerardi
 *
 */
public class TitleNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1211102677763772067L;

    /**
     *
     */
    public TitleNotFoundException() {
    }

    /**
     * @param message
     */
    public TitleNotFoundException(final String message) {
        super(message);
    }

}
