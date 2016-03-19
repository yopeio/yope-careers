/**
 *
 */
package io.yope.careers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Massimiliano Gerardi
 *
 */
@Builder
@AllArgsConstructor
@Getter
public class Verification {

    private final String id;

    private final String titleId;

    private final String authorityId;

    private final String verificationCode;

    private final String verificationHash;

    private final String hash;

}
