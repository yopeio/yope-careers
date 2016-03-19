/**
 *
 */
package io.yope.careers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@AllArgsConstructor
@Getter
public class Profile {

    private final String id;

    private final String title;

    private final String firstName;

    private final String lastName;

    private final String description;

    private final Long created;

}
