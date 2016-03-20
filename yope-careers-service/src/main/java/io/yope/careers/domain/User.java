/**
 *
 */
package io.yope.careers.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(of = {"type", "username", "profile"}, includeFieldNames = false)
public class User {

    public enum Type {
        CANDIDATE, RECRUITER, AUTHORITY;
    }

    public enum Status {
        ACTIVE, INACTIVE, PENDING, UNKNOWN;
    }

    private Long created;

    private Long modified;

    private Type type;

    private String username;

    @JsonIgnore
    private String password;

    private Profile profile;

    private Status status;

    private String hash;

    private List<Title> titles;

}
