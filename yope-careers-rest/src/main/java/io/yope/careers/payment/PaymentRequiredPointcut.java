/**
 *
 */
package io.yope.careers.payment;

import java.lang.reflect.Method;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

/**
 * @author Massimiliano Gerardi
 *
 */
@Component
public class PaymentRequiredPointcut extends StaticMethodMatcherPointcut {

    /* (non-Javadoc)
     * @see org.springframework.aop.MethodMatcher#matches(java.lang.reflect.Method, java.lang.Class)
     */
    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        return method.isAnnotationPresent(PaymentRequired.class);
    }

}
