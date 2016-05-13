package io.yope.careers.visitor;

import com.google.common.base.Joiner;
import io.yope.careers.domain.User;
import io.yope.ethereum.model.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

import java.util.Arrays;

@Builder
@AllArgsConstructor
@Getter
@Wither
public class UserVisitor extends CareerVisitor {

    private User user;

    @Override
    public String getContractKey() {
        return "User";
    }

    @Override
    public String getContractFile() {
        return "User.sol";
    }

    @Override
    public void addMethods() {
        this.addMethod(Method.builder().type(Method.Type.CREATE)
                .args(new Object[]{
                        user.getType().name(),
                        user.getUsername(),
                        user.getPassword(),
                        String.valueOf(user.getDateOfBirth()),
                        user.getProfile().getTitle(),
                        user.getProfile().getFirstName(),
                        user.getProfile().getLastName(),
                        user.getProfile().getRole(),
                        user.getProfile().getDescription(),
                        Joiner.on(",").join((user.getProfile().getTags()))
                        })
                .build());
        this.addMethod(Method.builder().type(Method.Type.MODIFY).name("set")
                .args(new Object[]{user.getProfile().getFirstName(), user.getProfile().getLastName(), String.valueOf(user.getDateOfBirth())}).build());
        this.addMethod(Method.builder().type(Method.Type.RUN).name("get").args(new Object[0]).build());
    }

}
