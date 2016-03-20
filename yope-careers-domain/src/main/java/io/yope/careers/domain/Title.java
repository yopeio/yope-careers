/**
 *
 */
package io.yope.careers.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
@ToString(of = {"name", "description"}, includeFieldNames = false)
public class Title {

    private String hash;

    private Long created;

    private String name;

    private String description;

    private Long issued;

    private Profile profile;

}
