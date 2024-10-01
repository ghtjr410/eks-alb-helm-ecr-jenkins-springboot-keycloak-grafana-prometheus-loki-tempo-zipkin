package com.ghtjr.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig  {

    private final String[] freeResourceUrls = {};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(freeResourceUrls).permitAll()
                    .requestMatchers("/api/user/**").hasRole("user")
                    .requestMatchers("/api/admin/**").hasRole("admin")
                    .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {

            if (jwt != null) {
                System.out.println("JWT가 존재합니다.");
                System.out.println("JWT 클레임: " + jwt.getClaims());
            } else {
                System.out.println("JWT가 존재하지 않습니다.");
            }

            Collection<GrantedAuthority> authorities = extractRealmRoles(jwt.getClaimAsMap("realm_access"));
            System.out.println("추출된 권한: " + authorities);
            return authorities;
        });
        return jwtAuthenticationConverter;
    }

    private Collection<GrantedAuthority> extractRealmRoles(Map<String, Object> realmAccess) {
        System.out.println("extractRealmRoles 메서드 호출됨.");
        if (realmAccess == null || !realmAccess.containsKey("roles")) {
            System.out.println("realmAccess가 null입니다.");
            System.out.println("realmAccess에 'roles' 키가 존재하지 않습니다.");
            return List.of();
        }
        List<String> roles = (List<String>) realmAccess.get("roles");
        System.out.println("추출된 롤 목록: " + roles);
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
