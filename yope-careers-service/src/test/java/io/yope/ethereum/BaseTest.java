package io.yope.ethereum;

import io.yope.ethereum.rpc.EthereumResource;
import io.yope.ethereum.rpc.EthereumRpc;
import io.yope.ethereum.services.AccountService;
import io.yope.ethereum.services.BlockchainFacade;
import io.yope.ethereum.services.ContractService;
import io.yope.ethereum.services.EthereumFacade;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.stream.Collectors;

import static io.yope.ethereum.utils.EthereumUtil.decryptQuantity;


public abstract class BaseTest {

    public static final String ETHEREUM_ADDR = "http://ethereum.yope.io";
    public static final String ACCOUNT_ADDR = "0x03733b713032e9040d04acd4720bedaa717378df";

    BlockchainFacade blockchainFacade;
    ContractService contractService;
    AccountService accountService;

    private EthereumRpc rpc;

    @Before
    public void init() throws MalformedURLException {
        rpc = new EthereumResource(ETHEREUM_ADDR).getGethRpc();
        contractService = new ContractService(rpc, decryptQuantity(rpc.eth_gasPrice()));
        accountService = new AccountService(rpc);
        blockchainFacade = new EthereumFacade(contractService, accountService);
    }

    String removeLineBreaks(final String file) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(file);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String collect = buffer.lines().collect(Collectors.joining("\n"));
        return collect.replace("\n", "").replace("\r", "");
    }

}
