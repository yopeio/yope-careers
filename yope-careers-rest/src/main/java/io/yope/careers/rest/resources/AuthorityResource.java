/**
 *
 */
package io.yope.careers.rest.resources;

import java.security.PublicKey;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.yope.careers.domain.User;
import io.yope.careers.rest.resources.domain.Response;
import io.yope.careers.rest.resources.domain.UserRegistrationRequest;
/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/authority")
public class AuthorityResource {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Response<User> registerUser(@RequestBody final UserRegistrationRequest request) {
        return new Response<User>();
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<List<User>> getAll() {
        return new Response<List<User>>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<User> get(final String id) {
        return new Response<User>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<User> put(final String id, @RequestBody final User User) {
        return new Response<User>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<User> delete(final String id) {
        return new Response<User>();
    }

    @RequestMapping(value="/{id}/hash", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<String> getHash(final String id) {
        return new Response<String>();
    }

    @RequestMapping(value="/{id}/pub", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response<String> addPublicKey(final String id, final PublicKey pub) {
        return new Response<String>();
    }

    @RequestMapping(value="/{id}/pub", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<PublicKey> getPublicKey(final String id) {
        return new Response<PublicKey>();
    }

    @RequestMapping(value="/{id}/pub", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<String> deletePublicKey(final String id, @RequestBody final PublicKey pub) {
        return new Response<String>();
    }

}
