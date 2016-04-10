/**
 *
 */
package io.yope.careers.rest.resources.domain;

import org.hibernate.validator.constraints.NotEmpty;

import io.yope.careers.domain.Profile;
import lombok.Getter;

/**
 * @author Massimiliano Gerardi
 *
 */
@Getter
public class RegistrationProfile {

    @NotEmpty
    private String title;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String[] tags;

    @NotEmpty
    private String role;

    @NotEmpty
    private String description;

    public Profile toProfile() {
        return Profile.builder()
                .tags(tags)
                .title(title)
                .description(description)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

}
