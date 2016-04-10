package io.yope.careers.visitor;

import io.yope.careers.domain.User;
import io.yope.ethereum.visitor.BlockchainVisitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static io.yope.ethereum.utils.EthereumUtil.removeLineBreaks;

@Builder
@AllArgsConstructor
@Getter
public class CandidateVisitor extends BlockchainVisitor {

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
    public String getNewMethod() {
        return "newCandidate";
    }

    @Override
    public String getRetrieveMethod() {
        return "get";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{user.getProfile().getFirstName(),
                user.getProfile().getLastName(),
                user.getDateOfBirth()};
    }

}
