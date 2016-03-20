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
public class Authority {

    private final String id;

    private final Long created;

    private final String username;

    private final String password;

    private final String description;

    private final String name;

    private final String hash;

}