package io.yope.careers.service;

import io.yope.careers.db.QueryCriteria;
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

    User modify(String id);

    User delete(String id);

    Page<Title> getTitles(String id);
}
