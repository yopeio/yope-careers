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

import io.yope.careers.domain.Authority;
import io.yope.careers.rest.resources.domain.AuthorityRegistrationRequest;
import io.yope.careers.rest.resources.domain.Response;
/**
 * @author Massimiliano Gerardi
 *
 */
@RestController
@RequestMapping("/authority")
public class AuthorityResource {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Response<Authority> registerAuthority(@RequestBody final AuthorityRegistrationRequest request) {
        return new Response<Authority>();
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<List<Authority>> getAll() {
        return new Response<List<Authority>>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Response<Authority> get(final String id) {
        return new Response<Authority>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response<Authority> put(final String id, @RequestBody final Authority authority) {
        return new Response<Authority>();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public Response<Authority> delete(final String id) {
        return new Response<Authority>();
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
