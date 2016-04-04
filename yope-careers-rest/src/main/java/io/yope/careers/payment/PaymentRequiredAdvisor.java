/**
 *
 */
package io.yope.careers.payment;

import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @author Massimiliano Gerardi
 *
 */
@Getter
@Component
public class PaymentRequiredAdvisor extends AbstractPointcutAdvisor {

    /**
     *
     */
    private static final long serialVersionUID = -223956039684191985L;

    @Autowired
    private StaticMethodMatcherPointcut pointcut;

    @Autowired
    private PaymentRequiredInterceptor advice;




}
