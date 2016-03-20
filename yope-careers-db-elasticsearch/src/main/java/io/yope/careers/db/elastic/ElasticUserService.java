package io.yope.careers.db.elastic;

import java.util.List;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.yope.careers.db.QueryCriteria;
import io.yope.careers.db.UserService;
import io.yope.careers.db.domain.ETitle;
import io.yope.careers.db.domain.EUser;
import io.yope.careers.db.elastic.helpers.SearchHelper;
import io.yope.careers.db.elastic.repositories.UserRepository;
import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.domain.User.Status;
/**
 *
 * @author Massimiliano Gerardi
 *
 */
@Component
public class ElasticUserService implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SearchHelper helper;

    @Override
    public User register(final User candidate) {
        final EUser elasticCandidate = EUser.builder()
                .hash(candidate.getHash())
                .created(candidate.getCreated())
                .profile(candidate.getProfile())
                .profile(candidate.getProfile())
                .password(candidate.getPassword())
                .username(candidate.getUsername())
                .status(Status.PENDING)
                .build();
        this.save(elasticCandidate);
        return candidate;
    }

    private User save(final EUser elasticCandidate) {
        final EUser candidate = this.repository.save(elasticCandidate);
        return candidate.toCandidate();
    }

    @Override
    public Page<User> search(final QueryCriteria criteria) {
        return this.helper.searchUser(criteria);
    }

    @Override
    public User get(final String id) {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new IllegalArgumentException(id);
        }
        return currentCandidate.toCandidate();
    }


    @Override
    public User modify(final String id, final User candidate) {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new IllegalArgumentException(id);
        }
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withProfile(candidate.getProfile())
                .withStatus(candidate.getStatus())
                .withPassword(candidate.getPassword());
        return this.save(elasticCandidate);
    }

    @Override
    public User delete(final String id) {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new IllegalArgumentException(id);
        }
        final EUser elasticCandidate = currentCandidate.withStatus(Status.INACTIVE);
        return this.save(elasticCandidate);
    }

    @Override
    public Page<Title> getTitles(final String id) {
        final User currentCandidate = this.get(id);
        if (currentCandidate == null) {
            throw new IllegalArgumentException(id);
        }
        return new Page<Title>(currentCandidate.getTitles(), new Long(currentCandidate.getTitles().size()), 1, 0, Boolean.TRUE);
    }

    @Override
    public Title registerTitle(final String id, final Title title) {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new IllegalArgumentException(id);
        }
        List<ETitle> titles = currentCandidate.getTitles();
        if (titles == null) {
            titles = Lists.newArrayList();
        }
        final ETitle etitle = ETitle.builder()
                .created(DateTime.now().getMillis())
                .issued(title.getIssued())
                .description(title.getDescription())
                .name(title.getName())
                .profile(title.getProfile())
                .hash(title.getHash())
                .build();
        titles.add(etitle);
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withTitles(titles);
        this.save(elasticCandidate);
        return title;
    }

    @Override
    public Title unregisterTitle(final String titleId) {
        final User searchCandidate = User.builder().titles(Lists.newArrayList(Title.builder().hash(titleId).build())).build();
        final Page<User> users = this.search(QueryCriteria.builder().candidate(searchCandidate).page(0).size(1).build());
        final User candidate = users.getElements().stream().findFirst().get();
        if (candidate == null) {
            throw new IllegalArgumentException(titleId);
        }
        final EUser currentCandidate = this.repository.findOne(candidate.getHash());

        final List<ETitle> titles = currentCandidate.getTitles();
        final ETitle title = titles.stream().filter(x -> x.getHash().equals(titleId)).findFirst().get();
        titles.remove(title);
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withTitles(titles);
        this.save(elasticCandidate);
        return title.toTitle();
    }

    @Override
    public Title getTitle(final String titleId) {
        final User candidate = User.builder().titles(Lists.newArrayList(Title.builder().hash(titleId).build())).build();
        final Page<User> users = this.search(QueryCriteria.builder().candidate(candidate).page(0).size(1).build());
        final User currentCandidate = users.getElements().stream().findFirst().get();
        if (currentCandidate == null) {
            throw new IllegalArgumentException(titleId);
        }
        final List<Title> titles = currentCandidate.getTitles();
        return titles.stream().filter(x -> x.getHash().equals(titleId)).findFirst().get();
    }

}
