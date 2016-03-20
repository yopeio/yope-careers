/**
 *
 */
package io.yope.careers.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import io.yope.careers.rest.services.UserHelper;
import io.yope.careers.service.QueryCriteria;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/candidate")
public class CandidateResource {

    @Autowired
    private UserHelper helper;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response<User> register(@RequestBody final User request) {
        final User user = this.helper.register(request);
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Page<User>> search(@RequestBody final QueryCriteria query) {
        final Page<User> page = this.helper.search(query);
        return new Response<Page<User>>(page, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<User> get(final String id) {
        final User user = this.helper.get(id);
        if (user == null) {
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(id).build()));
        }
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<User> put(final String id, @RequestBody final User candidate){
        try {
            final User user = this.helper.modify(id, candidate);
            return new Response<User>(user, Boolean.TRUE, HttpStatus.ACCEPTED.value());
        } catch (final UserNotFoundException e) {
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(e.getMessage()).build()));
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<User> delete(final String id){
        try {
        final User user = this.helper.delete(id);
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
        } catch (final UserNotFoundException e) {
            return new Response<User>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(e.getMessage()).build()));
        }
    }

    @RequestMapping(value="/{id}/titles", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Page<Title>> getTitles(final String id){
        try {
            final Page<Title> page = this.helper.getTitle(id);
            return new Response<Page<Title>>(page, Boolean.TRUE, HttpStatus.OK.value());
        } catch (final UserNotFoundException e) {
            return new Response<Page<Title>>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().field("id").message(e.getMessage()).build()));
        }
    }


}
