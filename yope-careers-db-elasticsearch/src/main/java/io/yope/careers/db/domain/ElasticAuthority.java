/**
 *
 */
package io.yope.careers.db.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import io.yope.careers.domain.Authority;
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
@Document(indexName = "authority", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class ElasticAuthority {

    private Long created;

    private String username;

    private String password;

    private String description;

    private String name;

    private String hash;

    public Authority toAuthority() {
        return Authority.builder()
                .hash(this.hash)
                .created(this.created)
                .description(this.description)
                .name(this.name)
                .password(this.password)
                .username(this.username)
                .build();
    }

}