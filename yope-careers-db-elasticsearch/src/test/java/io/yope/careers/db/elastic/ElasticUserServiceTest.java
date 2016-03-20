/**
 *
 */
package io.yope.careers.db.elastic;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.yope.careers.db.QueryCriteria;
import io.yope.careers.db.domain.ElasticCandidate;
import io.yope.careers.db.elastic.repositories.UserRepository;
import io.yope.careers.domain.Page;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Massimiliano Gerardi
 *
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElasticConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class ElasticUserServiceTest {

    @Autowired
    private ElasticUserService service;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private UserRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.template.deleteIndex(ElasticCandidate.class);
        this.template.createIndex(ElasticCandidate.class);
        this.template.putMapping(ElasticCandidate.class);
        this.template.refresh(ElasticCandidate.class, true);
        final JavaType type = this.mapper.getTypeFactory().constructCollectionType(List.class, User.class);
        final List<User> candidates = this.mapper.readValue(new File("src/test/resources/candidates.json"), type);

        for (final User candidate : candidates) {
            final ElasticCandidate elasticCandidate = ElasticCandidate.builder()
                    .hash(candidate.getHash())
                    .username(candidate.getUsername())
                    .created(candidate.getCreated())
                    .modified(candidate.getModified())
                    .profile(candidate.getProfile())
                    .password(candidate.getPassword())
                    .active(candidate.getActive())
                    .titles(candidate.getTitles())
                    .build();

            final IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(elasticCandidate.getHash())
                    .withObject(elasticCandidate).build();
            this.template.index(indexQuery);
            this.template.refresh(ElasticCandidate.class, true);
        }
    }

    @Test
    public void testRegister() {
        final String id = UUID.randomUUID().toString();
        User candidate = this.service.get("60b99c55-0763-4c81-b872-169c0582730a")
                .withHash(id)
                .withActive(Boolean.TRUE);
        this.service.register(candidate);
        candidate = this.service.get(id);
        Assert.assertNotNull(candidate);
        Assert.assertNull(candidate.getTitles());
        final Iterable<ElasticCandidate> candidates = this.repository.findAll();
        Assert.assertNotNull(candidates);
        Assert.assertEquals(101, Lists.newArrayList(candidates.iterator()).size());
    }

    @Test
    public void testGetAll() throws JsonProcessingException {
        final Iterable<ElasticCandidate> candidates = this.repository.findAll();
        Assert.assertNotNull(candidates);
        Assert.assertEquals(100, Lists.newArrayList(candidates.iterator()).size());
        for (final ElasticCandidate candidate : candidates) {
            Assert.assertNotNull(candidate.getTitles());
        }
    }

    @Test
    public void testGetById() {
        final User candidate = this.service.get("60b99c55-0763-4c81-b872-169c0582730a");
        Assert.assertNotNull(candidate);
        Assert.assertEquals("60b99c55-0763-4c81-b872-169c0582730a", candidate.getHash());
        Assert.assertEquals("name0", candidate.getProfile().getFirstName());
        Assert.assertNotNull(candidate.getTitles());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfile() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder()
               .profile(Profile.builder()
                       .description("Engineer")
                       .firstName("name0")
                       .lastName("lastname0").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(1, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByHashProfile() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final List<Title> titles = Lists.newArrayList(
               Title.builder().profile(Profile.builder()
                       .firstName("other")
                       .lastName("name").build()).build()
               );
       final User candidate = User.builder().titles(titles).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(1, page.getElements().size());
       Assert.assertTrue(page.getLast());
       final User user = page.getElements().get(0);
       Assert.assertEquals("84caacbb-8aa3-480d-b286-a98cf23ef9a2", user.getHash());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileNone() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder()
               .profile(Profile.builder()
                       .description("Engineer")
                       .firstName("name4")
                       .lastName("lastname0").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(0, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileName() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder()
               .profile(Profile.builder()
                       .firstName("name4")
                       .lastName("lastname4").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(1, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileDescription() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder()
               .profile(Profile.builder().description("Engineer").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(3, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileDescriptionOneHit() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().profile(Profile.builder().description("Software").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(1, page.getElements().size());
       Assert.assertTrue(page.getLast());
       final User result = page.getElements().get(0);
       Assert.assertEquals("f3107684-0f88-41e4-8664-79a976187ad6", result.getHash());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileDescriptionLowerCase() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().profile(Profile.builder().description("engineer").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(3, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileDescriptionThreeTerms() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().profile(Profile.builder().description("cobol software engineer").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       page.getElements().forEach(c -> log.info("{}", c));
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(4, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileDescriptionMultipleTerms() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().profile(Profile.builder().description("javascript cobol software developer").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       page.getElements().forEach(c -> log.info("{}", c));
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(3, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     * @throws JsonProcessingException
     */
    @Test
    public void testSearchByProfileFirstName() throws JsonProcessingException {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder()
               .profile(Profile.builder().firstName("name0").build()).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(1, page.getElements().size());
       Assert.assertTrue(page.getLast());
       final User result = page.getElements().get(0);
       Assert.assertEquals("60b99c55-0763-4c81-b872-169c0582730a", result.getHash());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     */
    @Test
    public void testSearchByTitleNone() {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().titles(Lists.newArrayList(Title.builder().name("dip").build())).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(0, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     */
    @Test
    public void testSearchByTitle() {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().titles(Lists.newArrayList(Title.builder().name("Diploma").build())).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(10, page.getElements().size());
       Assert.assertFalse(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     */
    @Test
    public void testSearchByTitleDescription() {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().titles(Lists.newArrayList(Title.builder().description("title").build())).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(10, page.getPages().intValue());
       Assert.assertEquals(96, page.getTotal().intValue());
       Assert.assertEquals(10, page.getElements().size());
       Assert.assertFalse(page.getLast());
    }

    /**
     * Test method for {@link io.yope.careers.db.elastic.ElasticUserService#search(io.yope.careers.db.QueryCriteria)}.
     */
    @Test
    public void testSearchByTitleDescriptionOneHit() {
       Assert.assertNotNull(this.service);
       final User candidate = User.builder().titles(Lists.newArrayList(Title.builder().description("great").build())).build();
       final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10).candidate(candidate).build());
       Assert.assertNotNull(page);
       Assert.assertEquals(0, page.getFrom().intValue());
       Assert.assertEquals(1, page.getElements().size());
       Assert.assertTrue(page.getLast());
    }

    @Test
    public void testGetTitles() {
        final Page<Title> page = this.service.getTitles("60b99c55-0763-4c81-b872-169c0582730a");
        Assert.assertNotNull(page);
        Assert.assertEquals(0, page.getFrom().intValue());
        Assert.assertEquals(1, page.getElements().size());
        Assert.assertTrue(page.getLast());
    }

    @Test
    public void testGetTitlesTwoHits() {
        final Page<Title> page = this.service.getTitles("f65aebc5-43fd-4429-8915-7c906871d208");
        Assert.assertNotNull(page);
        Assert.assertEquals(0, page.getFrom().intValue());
        Assert.assertEquals(2, page.getElements().size());
        Assert.assertTrue(page.getLast());
    }

    @Test
    public void testModify() {
        final User candidate = this.service.get("70aa978d-0ca7-4b0e-9d93-55939d50e71a");
        final User newCandidate = this.service.modify("70aa978d-0ca7-4b0e-9d93-55939d50e71a", candidate.toBuilder().
                profile(candidate.getProfile().toBuilder().firstName("massi").build()).build());
        Assert.assertEquals("massi", newCandidate.getProfile().getFirstName());
        final Page<User> page = this.service.search(QueryCriteria.builder().page(0).size(10)
                .candidate(User.builder().profile(Profile.builder().firstName("massi").build()).build()).build());
        Assert.assertEquals(0, page.getFrom().intValue());
        Assert.assertEquals(1, page.getElements().size());
        Assert.assertTrue(page.getLast());
    }

    @Test
    public void testSearch() {

        final QueryBuilder query = nestedQuery("titles", boolQuery().must(termQuery("titles.name", "diploma")));
        log.info("{}", query);
        final BoolFilterBuilder filter = FilterBuilders.boolFilter().must(FilterBuilders.termFilter("active", Boolean.FALSE));
        log.info("{}", filter);
        final QueryBuilder filteredQuery = QueryBuilders.filteredQuery(query, filter);
        log.info("{}", filteredQuery);
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(filteredQuery).build();

        final List<ElasticCandidate> candidates = this.template.queryForList(searchQuery, ElasticCandidate.class);
        Assert.assertEquals(1, candidates.size());
    }

    @Test
    public void testAddRemoveTitle() {
        final String id = "fa6d7dc3-14c0-46db-b7ba-e15392d4cb21";
        User candidate = this.service.get(id);
        Assert.assertEquals(1, candidate.getTitles().size());
        final String titleId = UUID.randomUUID().toString();
        final Title title = Title.builder().name("Master").hash(titleId).build();
        this.service.registerTitle(id, title);
        candidate = this.service.get(id);
        Assert.assertEquals(2, candidate.getTitles().size());

        this.service.unregisterTitle(titleId);
        candidate = this.service.get(id);
        Assert.assertEquals(1, candidate.getTitles().size());

    }

    @Test
    public void testSearchByHash() {
        final String id = "fa6d7dc3-14c0-46db-b7ba-e15392d4cb21";
        final User candidate = User.builder().hash(id).build();
        final Page<User> page = this.service.search(QueryCriteria.builder().candidate(candidate).page(0).size(1).build());
        Assert.assertEquals(0, page.getFrom().intValue());
        Assert.assertEquals(1, page.getElements().size());
        Assert.assertTrue(page.getLast());
        final User result = page.getElements().get(0);
        Assert.assertEquals(id, result.getHash());
    }

    @Test
    public void testSearchByTitleHash() {
        final String id = "70aa978d-0ca7-4b0e-9d93-55939d50e71a";
        final String titleId = "70aa978d-0ca7-4b0e-9d92-55939d50e71a";
        final Title title = Title.builder().hash(titleId).build();
        final User candidate = User.builder().titles(Lists.newArrayList(title)).build();
        final Page<User> page = this.service.search(QueryCriteria.builder().candidate(candidate).page(0).size(1).build());
        Assert.assertEquals(0, page.getFrom().intValue());
        Assert.assertEquals(1, page.getElements().size());
        Assert.assertTrue(page.getLast());
        final User result = page.getElements().get(0);
        Assert.assertEquals(id, result.getHash());
        Assert.assertEquals(titleId, result.getTitles().get(0).getHash());
    }

    @Test
    public void testGetTitle() {
        final String titleId = "70aa978d-0ca7-4b0e-9d92-55939d50e71a";
        final Title title = this.service.getTitle(titleId);
        Assert.assertNotNull(title);
        Assert.assertEquals(titleId, title.getHash());

    }

}
