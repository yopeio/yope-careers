/**
 *
 */
package io.yope.careers.db;

import io.yope.careers.domain.Title;
import io.yope.careers.domain.Verification;

/**
 * @author Massimiliano Gerardi
 *
 */
public interface TitleService {

    Title register(Title title);

    Title get(String id);

    Title put(String id, Title title);

    Title delete(String id);

    /*
     * VERIFICATION
     */

    Verification askVerification(String titleId);

    Verification confirmVerification(String titleId);

    Verification getVerification(String titleId);

    Verification revokeVerification(String titleId);


}
