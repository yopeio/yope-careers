/**
 *
 */
package io.yope.careers.db.elastic.helpers;

import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
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

import io.yope.careers.db.domain.EUser;
import io.yope.careers.domain.Page;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.domain.User.Status;
import io.yope.careers.domain.User.Type;
import io.yope.careers.service.QueryCriteria;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
@Slf4j
public class SearchHelper {

    private static final FilterBuilder ACTIVE_USER_FILTER = FilterBuilders.boolFilter().must(FilterBuilders.termFilter("status", Status.ACTIVE.name().toLowerCase()));

    private static final FilterBuilder CANDIDATE_USER_FILTER = FilterBuilders.boolFilter().must(FilterBuilders.termFilter("type", User.Type.CANDIDATE.name().toLowerCase()));;


    @Autowired
    private ElasticsearchTemplate template;

    public Page<User> searchUser(final QueryCriteria criteria){
        final SearchQuery searchQuery = this.getSearchQuery(criteria);
        final FacetedPage<EUser> result = this.template.queryForPage(searchQuery, EUser.class);

        return new Page<User>(result.getContent().stream()
                .map(x -> x.toCandidate()).collect(Collectors.toList()),
                result.getTotalElements(), result.getTotalPages(),
                result.getNumber(), result.isLast());
    }

    private SearchQuery getSearchQuery(final QueryCriteria criteria) {
        final QueryBuilder filteredQuery = this.getUserQuery(criteria.getCandidate());
        log.info("Query: \n{}", filteredQuery);
        return new NativeSearchQueryBuilder()
                .withQuery(filteredQuery)
                .withPageable(new PageRequest(criteria.getPage(), criteria.getSize()))
                .build();
    }

    private QueryBuilder getUserQuery(final User user) {
        if (user == null) {
            return QueryBuilders.filteredQuery(
                    QueryBuilders.matchAllQuery(),
                    ACTIVE_USER_FILTER);
        }
        final FilterBuilder filter = this.getFilter(user);
        if (user.getHash() != null) {
            return QueryBuilders.filteredQuery(
                    QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("hash", user.getHash())),
                    filter);
        } if (user.getProfile() != null) {
            return QueryBuilders.filteredQuery(
                    this.getProfileQuery(user.getProfile(), "profile"),
                    filter);
        } if (CollectionUtils.isNotEmpty(user.getTitles())) {
            return QueryBuilders.filteredQuery(
                    this.getTitleQuery(user.getTitles().get(0)),
                    filter);
        }
        return QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
    }

    private FilterBuilder getFilter(final User user) {
        final FilterBuilder statusFilter = this.getFilter(user.getStatus());
        final FilterBuilder typeFilter = this.getFilter(user.getType());
        final AndFilterBuilder filter = FilterBuilders.andFilter();
        if (statusFilter != null) {
            filter.add(statusFilter);
        }
        if (typeFilter != null) {
            filter.add(typeFilter);
        }
        return filter;
    }

    private FilterBuilder getFilter(final Type type) {
        if (type == null) {
            return CANDIDATE_USER_FILTER;
        }
        return FilterBuilders.boolFilter().must(FilterBuilders.termFilter("type", type.name().toLowerCase()));
    }

    private FilterBuilder getFilter(final Status status) {
        if (status == null) {
            return ACTIVE_USER_FILTER;
        }
        if (status.equals(Status.UNKNOWN)) {
            return FilterBuilders.matchAllFilter();
        }
        return FilterBuilders.boolFilter().must(FilterBuilders.termFilter("status", status.name().toLowerCase()));
    }

    private QueryBuilder getTitleQuery(final Title title) {
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (title.getHash() != null) {
            queryBuilder.must(QueryBuilders.matchQuery("titles.hash", title.getHash()));
            return QueryBuilders.nestedQuery("titles", queryBuilder);
        } else if (title.getProfile() != null) {
            final Profile profile = title.getProfile();
            return this.getProfileQuery(profile, "titles.profile");
        }
        if (title.getStatus() != null) {
            queryBuilder.must(QueryBuilders.termQuery("titles.status", title.getStatus().name().toLowerCase()));
        }
        if (title.getName() != null) {
            queryBuilder.must(QueryBuilders.termQuery("titles.name", title.getName().toLowerCase()));
        }
        if (title.getDescription() != null) {
            final String[] values = StringUtils.split(title.getDescription().toLowerCase());
            queryBuilder.must(QueryBuilders.termsQuery("titles.description", values));
        }
        return QueryBuilders.nestedQuery("titles", queryBuilder);
    }

    private QueryBuilder getProfileQuery(final Profile profile, final String path) {
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (profile.getFirstName() != null) {
            queryBuilder.must(QueryBuilders.termQuery(path+".firstName", profile.getFirstName().toLowerCase()));
        }
        if (profile.getLastName() != null) {
            queryBuilder.must(QueryBuilders.termQuery(path+".lastName", profile.getLastName().toLowerCase()));
        }
        if (profile.getRole() != null) {
            final String[] values = StringUtils.split(profile.getRole().toLowerCase());
            queryBuilder.must(QueryBuilders.termsQuery(path+".description", values));
        }
        if (profile.getDescription() != null) {
            final String[] values = StringUtils.split(profile.getDescription().toLowerCase());
            queryBuilder.must(QueryBuilders.termsQuery(path+".description", values));
        }
        if (profile.getTags() != null) {
            queryBuilder.must(QueryBuilders.termsQuery(path+".tags", profile.getTags()));
        }
        return QueryBuilders.nestedQuery(path, queryBuilder);
    }

}
