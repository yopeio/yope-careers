package io.yope.careers.visitor;

import io.yope.careers.domain.User;
import io.yope.ethereum.model.Method;

public class VisitorFactory {

    public static CareerVisitor getVisitor(final User user) {
        CareerVisitor visitor = null;
        if (user.getType().name().equals(User.Type.CANDIDATE.name())) {
            visitor = CandidateVisitor.builder()
                    .user(user)
                    .build();
        } else if (user.getType().name().equals(User.Type.AUTHORITY.name())) {
            visitor = AuthorityVisitor.builder()
                    .user(user)
                    .build();
        }
        visitor.addMethods();
        return visitor;
    }
}
