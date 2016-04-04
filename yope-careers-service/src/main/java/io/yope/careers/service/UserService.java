package io.yope.careers.service;

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.service.exceptions.IllegalTitleStatusException;
import io.yope.careers.service.exceptions.TitleNotFoundException;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 *
 * @author Massimiliano Gerardi
 *
 */
public interface UserService {

    User register(User user);

    Page<User> search(QueryCriteria query);

    User get(String id) ;

    User getByUsername(String id) ;

    User modify(String id, User user) throws UserNotFoundException;

    User delete(String id) throws UserNotFoundException;

    Page<Title> getTitles(String id) throws UserNotFoundException;

    Title registerTitle(String id, Title title) throws UserNotFoundException;

    Title unregisterTitle(String titleId) throws TitleNotFoundException, IllegalTitleStatusException;

    Title confirmTitleVerification(final String titleId) throws TitleNotFoundException, IllegalTitleStatusException;

    Title revokeTitleVerification(final String titleId) throws TitleNotFoundException, IllegalTitleStatusException;

    Title getTitle(String titleId) throws TitleNotFoundException;

}
