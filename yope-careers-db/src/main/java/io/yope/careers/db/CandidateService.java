package io.yope.careers.db;import java.util.List;

import javax.management.Query;

import io.yope.careers.domain.Candidate;
import io.yope.careers.domain.Title;

/**
 *
 * @author Massimiliano Gerardi
 *
 */
public interface CandidateService {

    Candidate register(Candidate candidate);

    List<Candidate> search(Query query);

    Candidate get(String id);

    Candidate modify(String id);

    Candidate delete(String id);

    List<Title> getTitles(String id);
}
