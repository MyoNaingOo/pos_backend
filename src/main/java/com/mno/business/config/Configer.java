package com.mno.business.config;

import com.mno.business.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Configer {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    @Value("${frontend.url}")
    private String frontend;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(
                AbstractHttpConfigurer::disable
        );
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    .requestMatchers(
                                            "/api/product/**",
                                            "/api/auth/**",
                                            "/api/image/**",
                                            "/api/otp/**",
                                            "/api/v2/shop/**"
                                    ).permitAll()
                                    .requestMatchers("/api/v2/user/**").authenticated()
                                    .requestMatchers("/api/v2/panel/user/**").hasAnyAuthority(Role.USERMANAGER.name(), Role.CEO.name(), Role.OWNER.name())
                                    .requestMatchers("/api/v2/shop/sale/**").hasAnyAuthority(Role.SALEMANAGER.name(), Role.SALEWORKER.name(), Role.CEO.name(), Role.OWNER.name())
                                    .requestMatchers("/api/v2/panel/sale/**").hasAnyAuthority(Role.SALEMANAGER.name(), Role.CEO.name(), Role.OWNER.name())
                                    .requestMatchers("/api/v2/shop/store/**").hasAnyAuthority(Role.STOREMANAGER.name(), Role.STOREWORKER.name(), Role.CEO.name(), Role.OWNER.name())
                                    .requestMatchers("/api/v2/panel/store/**").hasAnyAuthority(Role.STOREMANAGER.name(), Role.CEO.name(), Role.OWNER.name())
                                    .requestMatchers("/api/v2/shop/product/**")
                                    .hasAnyAuthority(
                                            Role.STOREMANAGER.name(),
                                            Role.STOREWORKER.name(),
                                            Role.SALEMANAGER.name(),
                                            Role.SALEWORKER.name(),
                                            Role.CEO.name(),
                                            Role.USER.name(),
                                            Role.OWNER.name()
                                    )
                                    .requestMatchers("api/v2/panel/product/**").hasAnyAuthority(
                                            Role.STOREMANAGER.name(),
                                            Role.SALEMANAGER.name(),
                                            Role.CEO.name(),
                                            Role.OWNER.name())
                                    .requestMatchers("/**").fullyAuthenticated()
                                    .anyRequest().authenticated();

                        }
                )
                .httpBasic(withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(
                        logout -> logout.logoutUrl("/api/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())

                );


        return http.build();
    }


    @Bean
    public WebMvcConfigurer alloweconfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200/", "http://localhost:3000/", "http://localhost:8080/", "http://localhost:5173/",getFrontend())
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };

    }


}
