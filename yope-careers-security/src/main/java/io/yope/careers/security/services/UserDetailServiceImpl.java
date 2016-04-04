/**
 *
 */
package io.yope.careers.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.yope.careers.domain.User;
import io.yope.careers.service.UserService;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.service.getByUsername(username);
        if (user == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                AuthorityUtils.createAuthorityList(String.format("ROLE_%s", user.getType().name())));
    }

}
