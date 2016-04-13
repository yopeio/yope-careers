/**
 *
 */
package io.yope.careers.service;

import com.cegeka.tetherj.NoSuchContractMethod;
import io.yope.ethereum.exceptions.ExceededGasException;
import io.yope.ethereum.model.CreateContractRequest;
import io.yope.ethereum.model.Receipt;
import io.yope.ethereum.model.UpdateRunContractRequest;
import io.yope.ethereum.services.BlockchainFacade;
import io.yope.ethereum.visitor.BlockchainVisitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
@Slf4j
@EnableConfigurationProperties
public class BlockchainServiceAdapter implements BlockchainService {

    @Autowired
    private BlockchainFacade blockchainFacade;

    @Value("${org.ethereum.accountAddress}")
    private String accountAddress;


    @Override
    public String register(final BlockchainVisitor visitor) {
        try {
            visitor.setAccountAddress(accountAddress);
            Map<String, Receipt> contracts = blockchainFacade.createContracts(new CreateContractRequest(visitor));
            String contractAddress = contracts.values().iterator().next().getContractAddress();
            blockchainFacade.modifyContract(contractAddress, new UpdateRunContractRequest(visitor));
            return contractAddress;
        } catch (ExceededGasException e) {
            log.info("gas exceed error", e);
        } catch (NoSuchContractMethod e) {
            log.info("contract does not exist", e);
        }
        return null;
    }

}
