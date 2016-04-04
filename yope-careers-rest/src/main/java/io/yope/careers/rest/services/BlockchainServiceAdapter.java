/**
 *
 */
package io.yope.careers.rest.services;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
public class BlockchainServiceAdapter implements BlockchainService {

    @Override
    public String register(final Object obj) {
        return UUID.randomUUID().toString();
    }

}
