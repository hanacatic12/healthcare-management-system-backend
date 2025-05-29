//package com.healthcare.system.healthcare.config;
//
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.naming.AuthenticationException;
//@Component
//public class CustomAuthenticationManager implements AuthenticationManager {
//
//    @Autowired
//    private HealthcareDetailsService customUserDetailsService;
//
//
//    @Bean
//    protected PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        final UserDetails userDetail = customUserDetailsService.loadUserByUsername(authentication.getName());
//        if(!passwordEncoder().matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
//            throw new BadCredentialsException("Bad credentials");
//        }
//        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
//}
