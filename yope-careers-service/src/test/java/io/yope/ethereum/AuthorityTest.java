package io.yope.ethereum;


import com.cegeka.tetherj.NoSuchContractMethod;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.User;
import io.yope.careers.visitor.CareerVisitorFactory;
import io.yope.ethereum.exceptions.ExceededGasException;
import io.yope.ethereum.model.Method;
import io.yope.ethereum.model.Receipt;
import io.yope.ethereum.visitor.BlockchainVisitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

@Slf4j
public class AuthorityTest extends BaseTest {

    private static final long TIMEOUT = 10;

    @Test
    public void testCreateAndRunAuthority() throws ExceededGasException, NoSuchContractMethod, ExecutionException, InterruptedException {

        Profile profile = Profile.builder().firstName("Tel Aviv University").lastName("").build();
        User user = User.builder().type(User.Type.AUTHORITY).profile(profile).password("secret").hash(ACCOUNT_ADDR).dateOfBirth(0L).build();
        BlockchainVisitor visitor = CareerVisitorFactory.UserVisitor.build(user);

        Future<Receipt> authority = blockchainFacade.createContracts(
                visitor
        );
        isDone(authority);

        String contractAddress = authority.get().getContractAddress();

        log.info("addr: {}", contractAddress);
        assertNotNull(contractAddress);

        boolean isAuthenticated = blockchainFacade.<Boolean>runContract(contractAddress, visitor);

        assertTrue(isAuthenticated);

        User newUser = user.withProfile(profile.withFirstName("New Tel Aviv University"));

        BlockchainVisitor newVisitor = CareerVisitorFactory.UserVisitor.build(newUser);

        Future<Receipt> receipt = blockchainFacade.modifyContract(contractAddress,
                newVisitor
        );

        isDone(receipt);

        log.info("receipt: {}", receipt);
        assertNotNull(contractAddress);

        newVisitor.addMethod(Method.builder().type(Method.Type.MODIFY).name("updatePassword")
                .args(new Object[]{"secret", "secret2"}).build());

        receipt = blockchainFacade.modifyContract(contractAddress,
                newVisitor
        );
        isDone(receipt);

        isAuthenticated = blockchainFacade.<Boolean>runContract(contractAddress, visitor);
        assertFalse(isAuthenticated);
    }

    @Test
    public void testStringsEqual() throws ExceededGasException, NoSuchContractMethod, ExecutionException, InterruptedException {
        BlockchainVisitor visitor = CareerVisitorFactory.UserVisitor.build(User.builder().password("secret2").type(User.Type.CANDIDATE).hash(ACCOUNT_ADDR)
                .profile(Profile.builder().build()).build());
        Future<Receipt> user = blockchainFacade.createContracts(visitor);
        isDone(user);

        boolean equals = run(visitor, user, "stringsEqual", "secret", "secret2");
        assertFalse(equals);

        equals = run(visitor, user, "stringsEqual", "secret2", "secret2");
        assertTrue(equals);

        boolean authenticated = run(visitor, user, "authenticate", "secret2");
        assertTrue(authenticated);

        authenticated = run(visitor, user, "authenticate", "secret");
        assertFalse(authenticated);
    }

    private<T> T run(BlockchainVisitor visitor, Future<Receipt> user, String method, Object... args) throws NoSuchContractMethod, InterruptedException, ExecutionException {
        visitor.addMethod(Method.builder().type(Method.Type.RUN).name(method)
                .args(args).build());

        return blockchainFacade.<T>runContract(user.get().getContractAddress(), visitor);
    }

    private static void isDone(Future<Receipt> receipt) {
        while (!receipt.isDone()) {
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
            }
        }
    }


}
