/**
 *
 */
package io.yope.careers.service;

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.service.exceptions.UserNotFoundException;
import io.yope.careers.visitor.CandidateVisitor;
import io.yope.careers.visitor.TitleVisitor;
import io.yope.careers.visitor.VisitorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
public class CareersService {

    @Autowired
    UserService userService;

    @Autowired
    BlockchainService blockchainService;

    public User register(final User user) {
        final String hash = this.blockchainService.register(
                VisitorFactory.getVisitor(user));
        final User currentUser = this.userService.register(user.withHash(hash));
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
        return userService.modify(id, candidate);
    }

    public User delete(final String id) throws UserNotFoundException {
        return userService.delete(id);
    }

    public Page<Title> getTitles(final String id) throws UserNotFoundException {
        return userService.getTitles(id);
    }

    public Title registerTitle(final String userId, final Title title) throws UserNotFoundException {
        final User user = get(userId);
        final Title.TitleBuilder builder = title.toBuilder();
        if  (title.getProfile() == null) {
            builder.profile(user.getProfile());
        }
        final String hash = blockchainService.register(TitleVisitor.builder().title(title).build());
        return userService.registerTitle(userId, title.withHash(hash));
    }

}
