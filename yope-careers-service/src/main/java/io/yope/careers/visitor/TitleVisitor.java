package io.yope.careers.visitor;

import io.yope.careers.domain.Title;
import io.yope.ethereum.visitor.BlockchainVisitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static io.yope.ethereum.utils.EthereumUtil.removeLineBreaks;

@Builder
@AllArgsConstructor
@Getter
public class TitleVisitor extends BlockchainVisitor {

    private Title title;

    @Override
    public String getContractKey() {
        return "TitleContract";
    }

    @Override
    public String getContractFile() {
        return "TitleContract.sol";
    }

    @Override
    public String getNewMethod() {
        return "newTitle";
    }

    @Override
    public String getRetrieveMethod() {
        return "get";
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{title.getName(), title.getDescription(), title.getProfile().getFirstName(), title.getProfile().getLastName()};
    }

}
