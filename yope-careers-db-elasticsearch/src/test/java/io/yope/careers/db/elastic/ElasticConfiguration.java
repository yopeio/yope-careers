/**
 *
 */
package io.yope.careers.db.elastic;

import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Massimiliano Gerardi
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "io.yope.careers.db.elastic.repositories")
@ComponentScan(basePackages = {"io.yope.careers.db.elastic"})
public class ElasticConfiguration {

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(NodeBuilder.nodeBuilder()
                .local(true)
                .node().client());
    }
}
