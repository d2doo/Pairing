package com.ssafy.i10a709be.common.configuration;

import com.ssafy.i10a709be.common.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(userAuthenticationProvider);
    }
}
