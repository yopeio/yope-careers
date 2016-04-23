package io.yope.ethereum;


import com.cegeka.tetherj.NoSuchContractMethod;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.User;
import io.yope.careers.visitor.CareerVisitor;
import io.yope.careers.visitor.VisitorFactory;
import io.yope.ethereum.exceptions.ExceededGasException;
import io.yope.ethereum.model.Method;
import io.yope.ethereum.model.Receipt;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class AuthorityTest extends BaseTest {

    private static final long TIMEOUT = 10;

    @Test
    public void testCreateAndRunAuthority() throws ExceededGasException, NoSuchContractMethod, ExecutionException, InterruptedException {

        Profile profile = Profile.builder().firstName("Tel Aviv University").build();
        User user = User.builder().type(User.Type.AUTHORITY).profile(profile).build();
        CareerVisitor visitor = getVisitor(user);

        Map<Receipt.Type, Future<Receipt>> authority = blockchainFacade.createContracts(
                visitor
        );

        for (Future<Receipt> r : authority.values()) {
            isDone(r);
        }

        String contractAddress = authority.values().iterator().next().get().getContractAddress();

        log.info("addr: {}", contractAddress);
        assertNotNull(contractAddress);

        String primaryAuthorityName = blockchainFacade.<String>runContract(contractAddress, visitor);

        assertEquals("Tel Aviv University", primaryAuthorityName);

        User newUser = user.withProfile(profile.withFirstName("New Tel Aviv University"));

        CareerVisitor newVisitor = getVisitor(newUser);

        Future<Receipt> receipt = blockchainFacade.modifyContract(contractAddress,
                newVisitor
        );

        isDone(receipt);

        log.info("receipt: {}", receipt);
        assertNotNull(contractAddress);

        String authorityName = blockchainFacade.<String>runContract(contractAddress, visitor);

        log.info("authorityName: {}", authorityName);
        assertEquals("New Tel Aviv University", authorityName);

        newVisitor.addMethod(Method.builder().type(Method.Type.MODIFY).name("validate")
                .args(new Object[]{"UTA"}).build());

        receipt = blockchainFacade.modifyContract(contractAddress,
                newVisitor
        );
        isDone(receipt);
        log.info("receipt: {}", receipt);
        authorityName = blockchainFacade.<String>runContract(contractAddress, visitor);
        log.info("authorityName: {}", authorityName);
        assertEquals(authorityName, "UTA");
    }

    private static void isDone(Future<Receipt> receipt) {
        while (!receipt.isDone()) {
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
            }
        }
    }

    private CareerVisitor getVisitor(User user) {
        CareerVisitor visitor = VisitorFactory.getVisitor(user);
        visitor.setAccountAddress(ACCOUNT_ADDR);
        return visitor;
    }

}
