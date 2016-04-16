package io.yope.careers.visitor;

import io.yope.careers.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

@Builder
@Wither
@AllArgsConstructor
@Getter
public class AuthorityVisitor extends CareerVisitor {

    private User user;

    @Override
    public String getContractKey() {
        return "Authority";
    }

    @Override
    public String getContractFile() {
        return "Authority.sol";
    }

    @Override
    public String getModifyMethod() {
        return "set";
    }

    @Override
    public String getRunMethod() {
        return "get";
    }

    @Override
    public Object[] getModifyArgs() {
        return new Object[] {user.getProfile().getFirstName()};
    }

}
