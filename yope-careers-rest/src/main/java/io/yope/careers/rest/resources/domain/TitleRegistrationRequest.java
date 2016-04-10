/**
 *
 */
package io.yope.careers.rest.resources.domain;

import org.hibernate.validator.constraints.NotEmpty;

import io.yope.careers.domain.Title;

/**
 * @author Massimiliano Gerardi
 *
 */
public class TitleRegistrationRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private Long issued;

    @NotEmpty
    private RegistrationProfile profile;


    public Title toTitle() {
        return Title.builder()
                .description(description)
                .name(name)
                .profile(profile.toProfile())
                .build();
    }
}
