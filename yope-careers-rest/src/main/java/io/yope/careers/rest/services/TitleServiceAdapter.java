/**
 *
 */
package io.yope.careers.rest.services;

import io.yope.careers.domain.Title;
import io.yope.careers.rest.resources.domain.TitleRegistrationRequest;
import io.yope.careers.service.CareersService;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * @author Massimiliano Gerardi
 *
 */
public class TitleServiceAdapter extends UserServiceAdapter {

    private CareersService service;

    /**
     * Registers a title on request of a user.
     *
     * If the title has not profile associated, the user's profile is used.
     * This if for the cases when a user register a title on behalf of another user.
     * @param user the user making the request
     * @param request the title request
     * @return the registered title
     * @throws UserNotFoundException
     */
    public Title registerTitle(final String userId, final TitleRegistrationRequest request) throws UserNotFoundException {
        return service.registerTitle(userId, request.toTitle());
    }

}
