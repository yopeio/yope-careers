package io.yope.careers.service;

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.service.exceptions.IllegalTitleStatusException;
import io.yope.careers.service.exceptions.TitleNotFoundException;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * Interface to a user service.
 *
 * @author Massimiliano Gerardi
 *
 */
public interface UserService {

    /**
     * Registers a user.
     * @param user the user to be registered.
     * @return
     */
    User register(User user);

    /**
     * Performs a query on the user.
     * @param query the query object containing all the query values.
     * @return a Page of results.
     */
    Page<User> search(QueryCriteria query);

    /**
     * gets an user by id.
     * @param id the id of the user
     * @return the user
     */
    User get(String id) ;

    /**
     * gets an user by username.
     * @param username the username of the user
     * @return the user
     */
    User getByUsername(String username) ;

    /**
     * replaces an user with a new description.
     * @param id the id of the user
     * @param user the new description for the user
     * @return the new user
     */
    User modify(String id, User user) throws UserNotFoundException;

    /**
     * deactivate an user
     * @param id the id of the user
     * @return the user
     */
    User delete(String id) throws UserNotFoundException;

    /**
     * retrieves the titles associated with an user.
     * @param id the id of the user
     * @return a list of titles
     * @throws UserNotFoundException
     */
    Page<Title> getTitles(String id) throws UserNotFoundException;

    /**
     * registers a title with an user.
     * @param id the id of the user
     * @param title the new title
     * @return the registered title
     * @throws UserNotFoundException
     */
    Title registerTitle(String id, Title title) throws UserNotFoundException;

    /**
     * unregisters a title with an user.
     * @param id the id of the user
     * @param title the new title
     * @return the registered title
     * @throws UserNotFoundException
     */
    Title unregisterTitle(String titleId) throws TitleNotFoundException, IllegalTitleStatusException;

    /**
     * Asks for the verification of a title.
     * @param titleId the id of the title
     * @return the registered title
     * @throws UserNotFoundException
     */
    Title confirmTitleVerification(final String titleId) throws TitleNotFoundException, IllegalTitleStatusException;

    /**
     * Revokes for the verification of a title.
     * @param titleId the id of the title
     * @return the registered title
     * @throws UserNotFoundException
     */
    Title revokeTitleVerification(final String titleId) throws TitleNotFoundException, IllegalTitleStatusException;

    /**
     * get a title by id.
     * @param id the id of the user
     * @return the registered title
     * @throws UserNotFoundException
     */
    Title getTitle(String titleId) throws TitleNotFoundException;

}
