/**
 *
 */
package io.yope.careers.rest.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.yope.careers.domain.Title;
import io.yope.careers.domain.Verification;
import io.yope.careers.rest.resources.domain.Response;
import io.yope.careers.rest.resources.domain.TitleRegistrationRequest;
import io.yope.careers.rest.resources.domain.VerificationRegistrationRequest;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/title")
public class TitleResource {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Response<Title> registerTitle(@RequestBody final TitleRegistrationRequest request) {
        return new Response<Title>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Title> get(final String id) {
        return new Response<Title>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<Title> put(final String id, @RequestBody final Title title) {
        return new Response<Title>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<Title> delete(final String id) {
        return new Response<Title>();
    }

    /*
     * VERIFICATION
     */

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Response<String> registerVerification(@RequestBody final VerificationRegistrationRequest request) {
        return new Response<String>();
    }

    @RequestMapping(value="/{id}/verification", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Verification> getVerification(final String id) {
        return new Response<Verification>();
    }

    @RequestMapping(value="/{id}/verification", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<Verification> confirmVerification(final String id, final Verification verification) {
        return new Response<Verification>();
    }

    @RequestMapping(value="/{id}/verification", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<Verification> revokeVerification(final String id) {
        return new Response<Verification>();
    }


}
