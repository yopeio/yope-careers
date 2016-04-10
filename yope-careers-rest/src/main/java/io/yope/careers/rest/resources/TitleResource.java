/**
 *
 */
package io.yope.careers.rest.resources;

import org.crsh.console.jline.internal.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.domain.Verification;
import io.yope.careers.rest.resources.domain.Error;
import io.yope.careers.rest.resources.domain.Response;
import io.yope.careers.rest.resources.domain.TitleRegistrationRequest;
import io.yope.careers.rest.resources.domain.VerificationRegistrationRequest;
import io.yope.careers.rest.services.TitleServiceAdapter;
import io.yope.careers.service.exceptions.UserNotFoundException;

/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/title")
public class TitleResource {

    TitleServiceAdapter titleService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Response<Title> registerTitle(@RequestBody final TitleRegistrationRequest request, final User user) {
        try {
            return new Response<Title>(titleService.registerTitle(user.getHash(), request), null, null);
        } catch (final UserNotFoundException e) {
            Log.error("Getting User by id", e);
            return new Response<Title>(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), Lists.newArrayList(Error.builder().message("User Not Found").build()));
        }
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

    @RequestMapping(value="/{id}/verification", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
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
