package io.yope.careers.visitor;

import io.yope.careers.domain.User;

public class VisitorFactory {

    public static CareerVisitor getVisitor(final User user) {
        if (user.getType().name().equals(User.Type.CANDIDATE.name())) {
            return CandidateVisitor.builder()
                    .user(user)
                    .build();
        } else if (user.getType().name().equals(User.Type.AUTHORITY.name())) {
            return AuthorityVisitor.builder()
                    .user(user)
                    .build();
        }
        throw new IllegalArgumentException("unmanaged user type or type not specified");
    }
}
