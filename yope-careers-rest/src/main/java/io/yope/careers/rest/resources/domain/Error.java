/**
 *
 */
package io.yope.careers.rest.resources.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

/**
 * @author massi
 *
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Error {

    @JsonProperty private final String message;
    @JsonProperty private final String field;

}
