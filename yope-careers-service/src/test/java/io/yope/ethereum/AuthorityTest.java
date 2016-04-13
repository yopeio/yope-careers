package io.yope.ethereum;


import com.cegeka.tetherj.NoSuchContractMethod;
import io.yope.ethereum.exceptions.ExceededGasException;
import io.yope.ethereum.model.CreateContractRequest;
import io.yope.ethereum.model.Receipt;
import io.yope.ethereum.model.UpdateRunContractRequest;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class AuthorityTest extends BaseTest {

    private static final String CONTRACT_KEY = "AuthorityContract";
    private static final String CONTRACT_FILE = "AuthorityContract.sol";
    private static final String NEW_METHOD = "newAuthority";
    private static final String GET_METHOD = "get";

    @Test
    public void testCreateAndRunAuthority() throws ExceededGasException, NoSuchContractMethod {
        final String contract = removeLineBreaks(CONTRACT_FILE);

        Map<String, Receipt> authority = blockchainFacade.createContracts(
                new CreateContractRequest(ACCOUNT_ADDR, contract)
        );
        String contractAddress = authority.values().iterator().next().getContractAddress();

        Receipt receipt = blockchainFacade.modifyContract(contractAddress,
                new UpdateRunContractRequest(ACCOUNT_ADDR, contract, CONTRACT_KEY, contractAddress, NEW_METHOD, DateTime.now().getMillis(), "Tel Aviv University", "TAU")
        );

        String authorityName = blockchainFacade.<String>runContract(contractAddress,
                new UpdateRunContractRequest(ACCOUNT_ADDR, contract, CONTRACT_KEY, contractAddress, GET_METHOD)
        );
        log.info("receipt: {}", receipt);
        assertNotNull(authorityName);
    }

}
