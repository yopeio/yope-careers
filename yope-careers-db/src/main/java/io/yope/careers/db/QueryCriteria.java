/**
 *
 */
package io.yope.careers.db;

import io.yope.careers.domain.Authority;
import io.yope.careers.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@Getter
@AllArgsConstructor
public class QueryCriteria {

    private final User candidate;

    private final Authority authority;

    private final Integer page;

    private final Integer size;


}
