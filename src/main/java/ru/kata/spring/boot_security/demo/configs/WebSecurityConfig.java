package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(CustomUserDetailService customUserDetailService,
                             SuccessUserHandler successUserHandler) {
        this.customUserDetailService = customUserDetailService;
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/login-page", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login-page")
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-page?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
