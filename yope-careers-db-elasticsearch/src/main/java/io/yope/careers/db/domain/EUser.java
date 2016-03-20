/**
 *
 */
package io.yope.careers.db.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import io.yope.careers.domain.Profile;
import io.yope.careers.domain.User;
import io.yope.careers.domain.User.Status;
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
@Document(indexName = "candidate", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class EUser {

    @Id
    private String hash;

    private Long created;

    private Long modified;

    private String username;

    private String password;

    @Field(type = FieldType.Nested)
    private Profile profile;

    @Field(type = FieldType.Nested)
    private List<ETitle> titles;

    private Status status;

    public User toCandidate() {
        return User.builder()
                .hash(this.getHash())
                .username(this.username)
                .created(this.getCreated())
                .modified(this.getModified())
                .profile(this.getProfile())
                .password(this.getPassword())
                .status(this.getStatus())
                .titles(this.titles == null? null : this.getTitles().stream().map(x -> x.toTitle()).collect(Collectors.toList()))
                .build();
    }

}
