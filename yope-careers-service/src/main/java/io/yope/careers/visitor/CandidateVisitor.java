package io.yope.careers.visitor;

import io.yope.careers.domain.User;
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
    public String getModifyMethod() {
        return "set";
    }

    @Override
    public String getRunMethod() {
        return "get";
    }

    @Override
    public Object[] getModifyArgs() {
        return new Object[] {user.getProfile().getFirstName(), user.getProfile().getLastName(), user.getDateOfBirth()};
    }

}
