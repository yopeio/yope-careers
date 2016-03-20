package io.yope.careers.db;

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;

/**
 *
 * @author Massimiliano Gerardi
 *
 */
public interface UserService {

    User register(User user);

    Page<User> search(QueryCriteria query);

    User get(String id);

    User modify(String id, User user);

    Title registerTitle(String id, Title title);

    Title unregisterTitle(String titleId);

    Title confirmTitleVerification(final String titleId);

    Title revokeTitleVerification(final String titleId);

    Title getTitle(String titleId);

    User delete(String id);

    Page<Title> getTitles(String id);

}
