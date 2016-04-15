package io.yope.careers.visitor;

import io.yope.careers.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Builder
@AllArgsConstructor
@Getter
public class CandidateVisitor extends CareerVisitor {

    private User user;

    @Override
    public String getContractKey() {
        return "CandidateContract";
    }

    @Override
    public String getContractFile() {
        return "CandidateContract.sol";
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
    public Object[] getArgs() {
        Object[] args = new Object[3];
        if (user != null) {
            if (user.getProfile() != null) {
                if (StringUtils.isNotBlank(user.getProfile().getFirstName())) {
                    args[0] = user.getProfile().getFirstName();
                }
                if (StringUtils.isNotBlank(user.getProfile().getLastName())) {
                    args[1] = user.getProfile().getLastName();
                }
            }
            if (user.getDateOfBirth() != null && user.getDateOfBirth() > 0) {
                args[2] = user.getDateOfBirth();
            }
        }
        return args;
    }

}
