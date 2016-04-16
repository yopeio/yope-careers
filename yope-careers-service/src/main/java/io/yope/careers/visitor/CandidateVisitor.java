package io.yope.careers.visitor;

import io.yope.careers.domain.User;
import io.yope.ethereum.model.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

@Builder
@AllArgsConstructor
@Getter
@Wither
public class CandidateVisitor extends CareerVisitor {

    private User user;

    @Override
    public String getContractKey() {
        return "Candidate";
    }

    @Override
    public String getContractFile() {
        return "Candidate.sol";
    }

    @Override
    public void addMethods() {
        this.addMethod(Method.builder().type(Method.Type.MODIFY).name("set")
                .args(new Object[]{user.getProfile().getFirstName(), user.getProfile().getLastName(), user.getDateOfBirth()}).build());
        this.addMethod(Method.builder().type(Method.Type.RUN).name("get").args(new Object[0]).build());
    }

}
