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
import lombok.experimental.Wither;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder(toBuilder = true)
@Wither
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
@ToString(of = {"firstName", "lastName", "description"}, includeFieldNames=false)
public class Profile {

    private String title;

    private String firstName;

    private String lastName;

    private Long dateOfBirth;

    private String[] tags;

    private String role;

    private String description;

    private Long created;

    private Long modified;

}
