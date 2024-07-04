package org.example.miniprojet.config;


import org.example.miniprojet.filter.JwtAuthenticationFilter;
import org.example.miniprojet.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class securityConfig {
    private  final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtauthenticationFilter;

    public securityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtauthenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/login","/register")
                                .permitAll()
                                .requestMatchers("/login/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/login/enseignant/**").hasAuthority("ENSEIGNANT")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtauthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build()
                ;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
