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

import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.rest.resources.domain.Response;
import io.yope.careers.rest.services.UserHelper;
import io.yope.careers.service.QueryCriteria;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/authority")
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
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<User> put(final String id, final User candidate){
        final User user = this.helper.modify(id, candidate);
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<User> delete(final String id){
        final User user = this.helper.delete(id);
        return new Response<User>(user, Boolean.TRUE, HttpStatus.CREATED.value());
    }

    @RequestMapping(value="/{id}/titles", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<Page<Title>> getTitles(final String id){
        final Page<Title> page = this.helper.getTitle(id);
        return new Response<Page<Title>>(page, Boolean.TRUE, HttpStatus.OK.value());
    }


}
