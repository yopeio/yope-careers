package io.yope.careers.visitor;

import io.yope.careers.domain.User;

public class VisitorFactory {

    public static CareerVisitor getVisitor(final User user) {
        CareerVisitor visitor = UserVisitor.builder()
                .user(user)
                .build();
        visitor.addMethods();
        return visitor;
    }
}
