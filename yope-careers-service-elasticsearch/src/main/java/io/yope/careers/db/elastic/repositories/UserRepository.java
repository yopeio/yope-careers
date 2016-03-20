/**
 *
 */
package io.yope.careers.db.elastic.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.yope.careers.db.domain.EUser;

/**
 * @author Massimiliano Gerardi
 *
 */
public interface UserRepository extends ElasticsearchRepository<EUser, String> {

}
