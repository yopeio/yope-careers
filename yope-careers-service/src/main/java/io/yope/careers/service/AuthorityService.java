/**
 *
 */
package io.yope.careers.service;

import io.yope.careers.domain.Authority;

/**
 * @author Massimiliano Gerardi
 *
 */
public interface AuthorityService {

    Authority register(Authority candidate);

    Authority get(String id);

    Authority modify(String id);

    Authority delete(String id);

    Boolean addPublicKey(String id, String publicKey);

    String getPublicKey(String id);

    String revokePublicKey(String id, String publicKey);
}
