/**
 *
 */
package io.yope.careers.rest.resources;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.rest.resources.domain.CandidateRegistrationRequest;
import io.yope.careers.rest.resources.domain.Response;
import io.yope.careers.service.QueryCriteria;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/authority")
public class CandidateResource {


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response<User> register(@RequestBody final CandidateRegistrationRequest request) {
        return new Response<User>();
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<List<User>> search(@RequestBody final QueryCriteria query) {
        return new Response<List<User>>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<User> get(final String id){
        return new Response<User>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<User> put(final String id, final User candidate){
        return new Response<User>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<List<Title>> getTitles(final String id){
        return new Response<List<Title>>();
    }


}
