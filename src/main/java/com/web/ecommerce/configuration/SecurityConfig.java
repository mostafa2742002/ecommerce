package com.web.ecommerce.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.web.ecommerce.filter.JwtFilter;
import com.web.ecommerce.service.UserDetailsServiceImpl;

/*
 * the explaination of the crsf and how it work is :
 * we have a public endpoint /hello that we want to ignore the csrf token for it (we can add more endpoints to ignore the csrf token for it)
 * we have a filter that will add the csrf token to the response header
 * and the csrf token will be added to the cookie and the response header
 * and every request will be checked for the csrf token and if it is not there it will return 403
 * we add the csrf token to the request header in the form of X-XSRF-TOKEN = csrf token
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CsrfTokenRequestAttributeHandler requestHandler = new
        // CsrfTokenRequestAttributeHandler();

        http

                .cors(cors -> cors.configurationSource(new CorsConfig()))
                //
                // // i put the csrf config in the csrfconfig class and i will call it here
                // .csrf((csrf) -> csrf.getClass().equals(CsrfConfig.class));

                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/error", "/webjars/**", "/index.html", "/signup", "/blog", "/blog/all",
                                "/blog/**", "/signin", "/home/refreshtoken", "/home/**", "/home", "/home/addnewuser",
                                "/home/authenticate", "/home/topselling",
                                "/home/resentllyadded", "/home/validateToken", "/home/addnewuser", "/home/verifyemail",
                                "/home/search", "/home/refreshtoken", "/home/generatetext", "/home/validatetoken")
                        .permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                // .sessionManagement(session ->
                // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
