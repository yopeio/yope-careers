package io.yope.ethereum;


import com.cegeka.tetherj.NoSuchContractMethod;
import io.yope.careers.domain.Profile;
import io.yope.careers.domain.User;
import io.yope.careers.visitor.AuthorityVisitor;
import io.yope.careers.visitor.CareerVisitor;
import io.yope.careers.visitor.VisitorFactory;
import io.yope.ethereum.exceptions.ExceededGasException;
import io.yope.ethereum.model.Receipt;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class AuthorityTest extends BaseTest {

    @Test
    public void testCreateAndRunAuthority() throws ExceededGasException, NoSuchContractMethod {

        Profile profile = Profile.builder().firstName("Tel Aviv University").build();
        User user = User.builder().type(User.Type.AUTHORITY).profile(profile).build();
        CareerVisitor visitor = getVisitor(user);

        Map<String, Receipt> authority = blockchainFacade.createContracts(
                visitor
        );
        String contractAddress = authority.values().iterator().next().getContractAddress();

        log.info("addr: {}", contractAddress);
        assertNotNull(contractAddress);

        String primaryAuthorityName = blockchainFacade.<String>runContract(contractAddress, visitor);

        assertEquals(primaryAuthorityName, "Tel Aviv University");

        User newUser = user.withProfile(profile.withFirstName("New Tel Aviv University"));

        CareerVisitor newVisitor = getVisitor(newUser);

        Receipt receipt = blockchainFacade.modifyContract(contractAddress,
                newVisitor
        );

        log.info("receipt: {}", receipt);
        assertNotNull(contractAddress);

        String authorityName = blockchainFacade.<String>runContract(contractAddress, visitor);

        log.info("authorityName: {}", authorityName);
        assertEquals(authorityName, "New Tel Aviv University");

    }

    private CareerVisitor getVisitor(User user) {
        CareerVisitor visitor = VisitorFactory.getVisitor(user);
        visitor.setAccountAddress(ACCOUNT_ADDR);
        return visitor;
    }

}
