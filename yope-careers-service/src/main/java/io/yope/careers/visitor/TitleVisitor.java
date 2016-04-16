package io.yope.careers.visitor;

import io.yope.careers.domain.Title;
import io.yope.ethereum.model.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

@Builder
@AllArgsConstructor
@Getter
@Wither
public class TitleVisitor extends CareerVisitor {

    private Title title;

    @Override
    public String getContractKey() {
        return "Title";
    }

    @Override
    public String getContractFile() {
        return "Title.sol";
    }

    @Override
    public void addMethods() {
        this.addMethod(Method.builder().type(Method.Type.MODIFY).name("set")
                .args(new Object[]{
                        title.getName(), title.getDescription(), title.getProfile().getFirstName(), title.getProfile().getLastName()
                }).build());
        this.addMethod(Method.builder().type(Method.Type.RUN).name("get").args(new Object[0]).build());
    }
}
