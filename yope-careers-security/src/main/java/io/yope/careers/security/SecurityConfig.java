/**
 *
 */
package io.yope.careers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Massimiliano Gerardi
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Profile("auth")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin()
            .loginPage("/login")
            .failureUrl("/login?error")
            .usernameParameter("username")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("remember-me")
            .logoutSuccessUrl("/")
            .permitAll()
            .and()
            .rememberMe();

        http.authorizeRequests().anyRequest().permitAll();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

}
