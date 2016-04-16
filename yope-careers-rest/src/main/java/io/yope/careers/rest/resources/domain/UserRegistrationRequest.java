/**
 *
 */
package io.yope.careers.rest.resources.domain;

import org.hibernate.validator.constraints.NotEmpty;

import io.yope.careers.domain.User;
import io.yope.careers.domain.User.Type;
import lombok.Getter;

/**
 * @author Massimiliano Gerardi
 *
 */
@Getter
public class UserRegistrationRequest {

    @NotEmpty
    private Type type;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private Long dateOfBirth;

    @NotEmpty
    private RegistrationProfile profile;

    public User toUser() {
        return User.builder()
                .dateOfBirth(dateOfBirth)
                .username(username)
                .password(password)
                .profile(profile.toProfile())
                .type(type)
                .build();
    }
}
