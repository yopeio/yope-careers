package io.yope.careers.visitor;

import io.yope.careers.domain.Title;
import io.yope.careers.domain.User;
import io.yope.ethereum.model.Method;
import io.yope.ethereum.visitor.BlockchainVisitor;
import io.yope.ethereum.visitor.VisitorFactory;

import static io.yope.ethereum.utils.EthereumUtil.removeLineBreaksFromFile;

public abstract class CareerVisitorFactory {

    public static class UserVisitor {

        public static BlockchainVisitor build(final String method, final String contractHash, final User user) {
            return VisitorFactory.build(contractHash, user.getHash(),
                    user.getPassword(),
                    "User",
                    removeLineBreaksFromFile("User.sol", CareerVisitorFactory.class),
                    user,
                    addMethod(method, user)
            );
        }

        private static Method addMethod(String method, User user) {
            switch (method) {
                case "create":
                    return Method.builder()
                            .args(new Object[]{
                                    user.getType().name(),
                                    user.getPassword(),
                                    String.valueOf(user.getDateOfBirth()),
                                    user.getProfile().getTitle(),
                                    user.getProfile().getFirstName(),
                                    user.getProfile().getLastName()
                            })
                            .build();
                case "updateProfile":
                    return Method.builder().name(method)
                            .args(new Object[]{user.getProfile().getFirstName(), user.getProfile().getLastName(), user.getPassword()}).build();
                case "authenticate":
                    return Method.builder().name(method).args(new Object[]{user.getPassword()}).build();
            }
            return null;
        }

        public static class TitleVisitor {

            public static BlockchainVisitor build(final User user, final String contractHash, final String method, final Title title) {
                return VisitorFactory.build(contractHash, user.getHash(),
                        user.getPassword(),
                        "Title",
                        removeLineBreaksFromFile("Title.sol", CareerVisitorFactory.class), title,
                        addMethod(method, title)
                );
            }

            public static Method addMethod(final String method, final Title title) {
                switch (method) {
                    case "set":
                        return Method.builder().name(method)
                                .args(new Object[]{
                                        title.getName(), title.getDescription(), title.getProfile().getFirstName(), title.getProfile().getLastName()
                                }).build();
                    case "get":
                        return Method.builder().name(method).args(new Object[0]).build();

                }
                return null;
            }
        }
    }

}
