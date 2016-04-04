package io.yope.careers.db.elastic;

import java.text.MessageFormat;
import java.util.List;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.yope.careers.db.domain.ETitle;
import io.yope.careers.db.domain.EUser;
import io.yope.careers.db.elastic.helpers.SearchHelper;
import io.yope.careers.db.elastic.repositories.UserRepository;
import io.yope.careers.domain.Page;
import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.careers.domain.User.Status;
import io.yope.careers.service.QueryCriteria;
import io.yope.careers.service.UserService;
import io.yope.careers.service.exceptions.IllegalTitleStatusException;
import io.yope.careers.service.exceptions.TitleNotFoundException;
import io.yope.careers.service.exceptions.UserNotFoundException;
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
                .created(DateTime.now().getMillis())
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
            return null;
        }
        return currentCandidate.toCandidate();
    }


    @Override
    public User modify(final String id, final User candidate) throws UserNotFoundException {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new UserNotFoundException(id);
        }
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withProfile(candidate.getProfile())
                .withStatus(candidate.getStatus())
                .withPassword(candidate.getPassword());
        return this.save(elasticCandidate);
    }

    @Override
    public User delete(final String id) throws UserNotFoundException {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new UserNotFoundException(id);
        }
        final EUser elasticCandidate = currentCandidate.withStatus(Status.INACTIVE);
        return this.save(elasticCandidate);
    }

    @Override
    public Page<Title> getTitles(final String id) throws UserNotFoundException {
        final User currentCandidate = this.get(id);
        if (currentCandidate == null) {
            throw new UserNotFoundException(id);
        }
        return new Page<Title>(currentCandidate.getTitles(), new Long(currentCandidate.getTitles().size()), 1, 0, Boolean.TRUE);
    }

    @Override
    public Title registerTitle(final String id, final Title title) throws UserNotFoundException {
        final EUser currentCandidate = this.repository.findOne(id);
        if (currentCandidate == null) {
            throw new UserNotFoundException(id);
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
                .status(Title.Status.UNVERIFIED)
                .build();
        titles.add(etitle);
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withTitles(titles);
        this.save(elasticCandidate);
        return title;
    }

    @Override
    public Title confirmTitleVerification(final String titleId) throws IllegalTitleStatusException, TitleNotFoundException {
        return this.changeTitleStatus(titleId, Title.Status.VERIFIED);

    }

    @Override
    public Title revokeTitleVerification(final String titleId) throws IllegalTitleStatusException, TitleNotFoundException {
        return this.changeTitleStatus(titleId, Title.Status.UNVERIFIED);
    }

    private Title changeTitleStatus(final String titleId, final Title.Status status) throws IllegalTitleStatusException, TitleNotFoundException {
        final EUser currentCandidate = this.getUserForTitle(Title.builder().hash(titleId).build());
        final List<ETitle> titles = currentCandidate.getTitles();
        final ETitle currentTitle = titles.stream().filter(x -> x.getHash().equals(titleId)).findFirst().orElse(null);
        if (currentTitle.getStatus().equals(status)) {
            throw new IllegalTitleStatusException(MessageFormat.format("Title {0} has already status {1}", titleId, status)) ;
        }
        titles.remove(currentTitle);
        final ETitle modifiedTitle = currentTitle.withStatus(status);
        titles.add(modifiedTitle);
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withTitles(titles);
        this.save(elasticCandidate);
        return modifiedTitle.toTitle();
    }

    @Override
    public Title unregisterTitle(final String titleId) throws TitleNotFoundException {
        final EUser currentCandidate = this.getUserForTitle(Title.builder().hash(titleId).build());
        final List<ETitle> titles = currentCandidate.getTitles();
        final ETitle title = titles.stream().filter(x -> x.getHash().equals(titleId)).findFirst().orElse(null);
        titles.remove(title);
        final EUser elasticCandidate = currentCandidate
                .withModified(DateTime.now().getMillis())
                .withTitles(titles);
        this.save(elasticCandidate);
        return title.toTitle();
    }

    private EUser getUserForTitle(final Title title) throws TitleNotFoundException {
        final User searchCandidate = User.builder().titles(Lists.newArrayList(title)).build();
        final Page<User> users = this.search(QueryCriteria.builder().candidate(searchCandidate).page(0).size(1).build());
        final User candidate = users.getElements().stream().findFirst().orElse(null);
        if (candidate == null) {
            throw new TitleNotFoundException(MessageFormat.format("No results for query on '{0}'", title));
        }
        return this.repository.findOne(candidate.getHash());
    }

    @Override
    public Title getTitle(final String titleId) throws TitleNotFoundException {
        final User candidate = User.builder().titles(Lists.newArrayList(Title.builder().hash(titleId).build())).build();
        final Page<User> users = this.search(QueryCriteria.builder().candidate(candidate).page(0).size(1).build());
        final User currentCandidate = users.getElements().stream().findFirst().orElse(null);
        if (currentCandidate == null) {
            throw new TitleNotFoundException(MessageFormat.format("No results for query on '{0}'", titleId));
        }
        final List<Title> titles = currentCandidate.getTitles();
        return titles.stream().filter(x -> x.getHash().equals(titleId)).findFirst().orElse(null);
    }

    @Override
    public User getByUsername(final String username) {
        final EUser currentCandidate = this.repository.findByUsername(username);
        if (currentCandidate == null) {
            return null;
        }
        return currentCandidate.toCandidate();
    }

}
