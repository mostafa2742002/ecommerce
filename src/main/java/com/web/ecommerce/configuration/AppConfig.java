package com.web.ecommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    // @Autowired
    // private UserRepository userRepository;

    // @Bean
    // public UserDetailsService userDetailsService(){
    // return username -> userRepository.findByEmail(username)
    // .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    // }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    // DaoAuthenticationProvider authenticationProvider = new
    // DaoAuthenticationProvider();
    // authenticationProvider.setUserDetailsService(userDetailsService());
    // authenticationProvider.setPasswordEncoder(passwordEncoder());
    // return authenticationProvider;
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
