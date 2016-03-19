/**
 *
 */
package io.yope.careers.rest.resources;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.yope.careers.domain.Candidate;
import io.yope.careers.domain.Query;
import io.yope.careers.domain.Title;
import io.yope.careers.rest.resources.domain.CandidateRegistrationRequest;
import io.yope.careers.rest.resources.domain.Response;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/authority")
public class CandidateResource {


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response<Candidate> register(@RequestBody final CandidateRegistrationRequest request) {
        return new Response<Candidate>();
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<List<Candidate>> search(@RequestBody final Query query) {
        return new Response<List<Candidate>>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Candidate> get(final String id){
        return new Response<Candidate>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<Candidate> put(final String id, final Candidate candidate){
        return new Response<Candidate>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<List<Title>> getTitles(final String id){
        return new Response<List<Title>>();
    }


}
