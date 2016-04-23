/**
 *
 */
package io.yope.careers.service;

import com.cegeka.tetherj.NoSuchContractMethod;
import io.yope.ethereum.exceptions.ExceededGasException;
import io.yope.ethereum.model.Receipt;
import io.yope.ethereum.services.BlockchainFacade;
import io.yope.ethereum.visitor.BlockchainVisitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
@Slf4j
@EnableConfigurationProperties
public class BlockchainServiceAdapter implements BlockchainService {

    private static final long TIMEOUT = 10;
    @Autowired
    private BlockchainFacade blockchainFacade;

    @Value("${org.ethereum.accountAddress}")
    private String accountAddress;


    @Override
    public String register(final BlockchainVisitor visitor) {
        try {
            visitor.setAccountAddress(accountAddress);
            Map<Receipt.Type, Future<Receipt>> contracts = blockchainFacade.createContracts(visitor);

            Future<Receipt> futureReceipt = contracts.values().iterator().next();

            while (!futureReceipt.isDone()) {
                try {
                    Thread.sleep(TIMEOUT);
                } catch (InterruptedException e) {}
            }
            return futureReceipt.get().getContractAddress();
        } catch (ExceededGasException e) {
            log.info("gas exceed error", e);
        } catch (NoSuchContractMethod e) {
            log.info("no method found", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.info("execution exception", e);
        }
        return null;
    }

}
