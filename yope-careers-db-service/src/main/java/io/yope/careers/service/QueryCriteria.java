/**
 *
 */
package io.yope.careers.service;

import io.yope.careers.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A query descriptor.
 * Contains all the info to be used in creating an ElasticSearch query.
 *
 * @author Massimiliano Gerardi
 *
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QueryCriteria {

    private User candidate;

    private Integer page;

    private Integer size;


}
