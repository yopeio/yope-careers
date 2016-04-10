/**
 *
 */
package io.yope.careers.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.rest.resources.domain.UserRegistrationRequest;
import io.yope.careers.service.CareersService;
import io.yope.careers.service.QueryCriteria;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
public class UserServiceAdapter {

    @Autowired
    private CareersService service;

    public User register(final UserRegistrationRequest request) {
        final User user = request.toUser();
        final User currentUser = service.register(user);
        return currentUser;
    }

    public Page<User> search(final QueryCriteria query) {
        return service.search(query);
    }

    public User get(final String id) throws UserNotFoundException {
        return service.get(id);
    }

    public User modify(final String id, final User candidate) throws UserNotFoundException {
        return service.modify(get(id).getHash(), candidate);
    }

    public User delete(final String id) throws UserNotFoundException {
        return service.delete(get(id).getHash());
    }

    public Page<Title> getTitles(final String id) throws UserNotFoundException {
        return service.getTitles(get(id).getHash());
    }

    public boolean exist(final String username) {
        return service.exist(username);
    }


}
