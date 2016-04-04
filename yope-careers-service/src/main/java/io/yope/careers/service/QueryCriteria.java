/**
 *
 */
package io.yope.careers.service;

import io.yope.careers.domain.Authority;
import io.yope.careers.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QueryCriteria {

    private User candidate;

    private Authority authority;

    private Integer page;

    private Integer size;


}
