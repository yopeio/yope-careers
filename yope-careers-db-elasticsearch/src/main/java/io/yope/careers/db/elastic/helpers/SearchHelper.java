/**
 *
 */
package io.yope.careers.db.elastic.helpers;

import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import io.yope.careers.db.QueryCriteria;
import io.yope.careers.db.domain.ElasticCandidate;
import io.yope.careers.domain.Page;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
@Slf4j
public class SearchHelper {

    @Autowired
    private ElasticsearchTemplate template;

    public Page<User> searchUser(final QueryCriteria criteria){
        final User user = criteria.getCandidate();
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        QueryBuilder query = null;
        if (user.getHash() != null) {
            queryBuilder.must(QueryBuilders.matchQuery("hash", user.getHash()));
            query = QueryBuilders.constantScoreQuery(queryBuilder);
        } else if (user.getProfile() != null) {
            query = QueryBuilders.nestedQuery("profile", queryBuilder);
            if (user.getProfile().getFirstName() != null) {
                queryBuilder.must(QueryBuilders.termQuery("profile.firstName", user.getProfile().getFirstName().toLowerCase()));
            }
            if (user.getProfile().getLastName() != null) {
                queryBuilder.must(QueryBuilders.termQuery("profile.lastName", user.getProfile().getLastName().toLowerCase()));
            }
            if (user.getProfile().getDescription() != null) {
                final String[] values = StringUtils.split(user.getProfile().getDescription().toLowerCase());
                queryBuilder.must(QueryBuilders.termsQuery("profile.description", values));
            }
        } else if (CollectionUtils.isNotEmpty(user.getTitles())) {
            query = QueryBuilders.nestedQuery("titles", queryBuilder);
            final Title title = user.getTitles().get(0);
            if (title.getHash() != null) {
                queryBuilder.must(QueryBuilders.matchQuery("titles.hash", title.getHash()));
            }
            if (title.getName() != null) {
                queryBuilder.must(QueryBuilders.termQuery("titles.name", title.getName().toLowerCase()));
            }
            if (title.getDescription() != null) {
                final String[] values = StringUtils.split(title.getDescription().toLowerCase());
                queryBuilder.must(QueryBuilders.termsQuery("titles.description", values));
            }
            if (title.getProfile() != null) {
                final Profile profile = title.getProfile();
            }
        }
        Boolean active = Boolean.TRUE;
        if (user.getActive() != null) {
            active = user.getActive();
        }
        final BoolFilterBuilder filter = FilterBuilders.boolFilter().must(FilterBuilders.termFilter("active", active));
        final QueryBuilder filteredQuery = QueryBuilders.filteredQuery(query, filter);
        log.info("\n {}", filteredQuery);
        final SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(filteredQuery)
                .withPageable(new PageRequest(criteria.getPage(), criteria.getSize()))
                .build();
        final FacetedPage<ElasticCandidate> result = this.template.queryForPage(searchQuery, ElasticCandidate.class);

        return new Page<User>(result.getContent().stream()
                .map(x -> User.builder()
                .created(x.getCreated())
                .hash(x.getHash())
                .modified(x.getModified())
                .profile(x.getProfile())
                .password(x.getPassword())
                .active(x.getActive())
                .titles(x.getTitles())
                .build()).collect(Collectors.toList()),
                result.getTotalElements(), result.getTotalPages(),
                result.getNumber(), result.isLast());
    }

}
