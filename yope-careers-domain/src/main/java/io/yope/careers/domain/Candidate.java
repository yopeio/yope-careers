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
public class Candidate {

    private final String id;

    private final Long created;

    private final String username;

    private final String password;

    private final Profile profile;

    private final Boolean active;

    private final String hash;

}
