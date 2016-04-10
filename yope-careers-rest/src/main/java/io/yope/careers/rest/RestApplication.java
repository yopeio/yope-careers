package io.yope.careers.rest;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Massimiliano Gerardi
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({
        "io.yope.careers",
        "io.yope.ethereum.configuration"
    })
@Configuration
public class RestApplication {

    @Bean
    public DefaultAdvisorAutoProxyCreator autoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(RestApplication.class);
    }

}
