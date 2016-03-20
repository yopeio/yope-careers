/**
 *
 */
package io.yope.careers.db.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import io.yope.careers.domain.User;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(indexName = "candidate", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class ElasticCandidate {

    @Id
    private String hash;

    private Long created;

    private Long modified;

    private String username;

    private String password;

    @Field(type = FieldType.Nested)
    private Profile profile;

    @Field(type = FieldType.Nested)
    private List<Title> titles;

    private Boolean active;

    public User toCandidate() {
        return User.builder()
                .hash(this.getHash())
                .username(this.username)
                .created(this.getCreated())
                .modified(this.getModified())
                .profile(this.getProfile())
                .password(this.getPassword())
                .active(this.getActive())
                .titles(this.getTitles())
                .build();

    }

}
