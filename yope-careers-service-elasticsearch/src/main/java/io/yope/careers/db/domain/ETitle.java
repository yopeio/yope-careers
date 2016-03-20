/**
 *
 */
package io.yope.careers.db.domain;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import io.yope.careers.domain.Profile;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.Title.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@Wither
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ETitle {

    private String hash;

    private Long created;

    private String name;

    private String description;

    private Long issued;

    @Field(type = FieldType.Nested)
    private Profile profile;

    private Status status;

    public Title toTitle() {
        return Title.builder()
                .created(this.created)
                .description(this.description)
                .hash(this.hash)
                .issued(this.issued)
                .name(this.name)
                .profile(this.profile)
                .status(this.status)
                .build();
    }

}
