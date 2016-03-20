/**
 *
 */
package io.yope.careers.rest.resources.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Massimiliano Gerardi
 *
 */
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response<T> {

    private Boolean success;

    private Integer status;

    private T body;

    private List<Error> errors;

    public Response(final T body, final Boolean success, final Integer status) {
        super();
        this.success = success;
        this.status = status;
        this.body = body;
    }

    public Response(final List<Error> errors, final Boolean success, final Integer status) {
        super();
        this.success = success;
        this.status = status;
        this.errors = errors;
    }

}
