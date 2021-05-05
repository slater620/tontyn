/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.configuration;

import com.tontyn.service.UserDetailsServiceImpl;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 *
 * @author tanankem
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
   @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
 
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable();
 
        // The pages does not require login
        http.authorizeRequests().antMatchers("/connexion").permitAll();
        
        // For USERS only.
        http.authorizeRequests().antMatchers("/").access("hasRole('ROLE_USER')");
 
        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/connexion")//
                .defaultSuccessUrl("/tontynapp")//
                .failureUrl("/connexion?error=true")//
                .usernameParameter("email")//
                .passwordParameter("motDePasse")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/connexion");
        
 
        // Config Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(10 * 60); // 24h
 
    }
 
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(this.dataSource);
        return db;
    }
    
}
