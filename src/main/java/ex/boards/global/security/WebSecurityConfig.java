package ex.boards.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ex.boards.global.common.SuccessResDto;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(getCorsConfigurerCustomizer());

        settingRequestAuthorization(http);
        settingLoginForm(http);

        return http.build();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> getCorsConfigurerCustomizer() {
        return corsConfigurer -> corsConfigurer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("*")); // 모든 오리진 허용
            config.setAllowedMethods(Collections.singletonList("*")); // 모든 메서드 허용
            config.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
            config.setExposedHeaders(Collections.singletonList("*"));
            return config;
        });
    }

    private void settingRequestAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz ->
            authz
                // 정적 파일
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 유저 도메인
                .requestMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/logout").permitAll()
                // 그 외
                .anyRequest().authenticated()
        );
    }

    private void settingLoginForm(HttpSecurity http) throws Exception {
        http.formLogin((form) -> form
            .loginProcessingUrl("/users/login")
            .usernameParameter("email")
            .successHandler((request, response, authentication) -> {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());

                ObjectMapper objectMapper = new ObjectMapper();

                String json = objectMapper.writeValueAsString(new SuccessResDto());
                response.getWriter().write(json);
            }));
    }
}
