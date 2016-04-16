package io.yope.careers.visitor;

import io.yope.careers.domain.User;
import io.yope.ethereum.model.Method;
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
    public void addMethods() {
        this.addMethod(Method.builder().type(Method.Type.MODIFY).name("set")
                .args(new Object[]{user.getProfile().getFirstName()}).build());
        this.addMethod(Method.builder().type(Method.Type.RUN).name("get").args(new Object[0]).build());
    }

}
