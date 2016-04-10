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
import io.yope.careers.service.QueryCriteria;
import io.yope.careers.service.UserService;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
public class UserServiceAdapter {

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private UserService userService;

    public User register(final UserRegistrationRequest request) {
        final User user = request.toUser();
        final String hash = blockchainService.register(user);
        final User currentUser = userService.register(user.withHash(hash));
        return currentUser;
    }

    public Page<User> search(final QueryCriteria query) {
        return userService.search(query);
    }

    public Boolean exist(final String id) {
        return userService.getByUsername(id) != null || userService.get(id) != null;
    }

    public User get(final String id) throws UserNotFoundException {
        User currentUser = userService.getByUsername(id);
        if (currentUser!=null) {
            return currentUser;
        }
        currentUser = userService.get(id);
        if (currentUser!=null) {
            return currentUser;
        }
        throw new UserNotFoundException(id);
    }

    public User modify(final String id, final User candidate) throws UserNotFoundException {
        return userService.modify(get(id).getHash(), candidate);
    }

    public User delete(final String id) throws UserNotFoundException {
        return userService.delete(get(id).getHash());
    }

    public Page<Title> getTitles(final String id) throws UserNotFoundException {
        return userService.getTitles(get(id).getHash());
    }


}
