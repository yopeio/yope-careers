/**
 *
 */
package io.yope.careers.rest.resources;

import org.crsh.console.jline.internal.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.rest.resources.domain.Error;
import io.yope.careers.rest.resources.domain.Response;
import io.yope.careers.rest.resources.domain.UserRegistrationRequest;
import io.yope.careers.rest.services.UserServiceAdapter;
import io.yope.careers.service.QueryCriteria;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserServiceAdapter userService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response<User> register(@RequestBody final UserRegistrationRequest request) {
        if (userService.exist(request.getUsername())) {
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("username").message(request.getUsername()).build()));
        }
        final User user = userService.register(request);
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response<Page<User>> search(@RequestBody final QueryCriteria query) {
        final Page<User> page = userService.search(query);
        return new Response<Page<User>>(page, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<User> get(@PathVariable final String id) {
        try {
            return new Response<User>(userService.get(id), Boolean.TRUE, HttpStatus.CREATED.value());
        } catch (final UserNotFoundException e) {
            Log.error("Getting User by id", e);
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(id).build()));
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<User> put(@PathVariable final String id, @RequestBody final User candidate){
        try {
            final User user = userService.modify(id, candidate);
            return new Response<User>(user, Boolean.TRUE, HttpStatus.ACCEPTED.value());
        } catch (final UserNotFoundException e) {
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(e.getMessage()).build()));
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<User> delete(@PathVariable final String id){
        try {
        final User user = userService.delete(id);
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
        } catch (final UserNotFoundException e) {
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(e.getMessage()).build()));
        }
    }

    @RequestMapping(value="/{id}/titles", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Page<Title>> getTitles(@PathVariable final String id){
        try {
            final Page<Title> page = userService.getTitles(id);
            return new Response<Page<Title>>(page, Boolean.TRUE, HttpStatus.OK.value());
        } catch (final UserNotFoundException e) {
            return new Response<Page<Title>>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(e.getMessage()).build()));
        }
    }


}
