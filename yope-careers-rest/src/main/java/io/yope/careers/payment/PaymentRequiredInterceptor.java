/**
 *
 */
package io.yope.careers.payment;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Massimiliano Gerardi
 *
 */
@Slf4j
@Component
public class PaymentRequiredInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        try {
            log.info("~~~~~~~~ START METHOD {} ~~~~~~~~", invocation.getMethod().toGenericString());
            return invocation.proceed();
        } finally {
            log.info("~~~~~~~~ END METHOD {} ~~~~~~~~", invocation.getMethod().toGenericString());
        }
    }

}
