package ru.kpfu.itis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kpfu.itis.security.CustomAccessDeniedHandler;
import ru.kpfu.itis.security.CustomAuthenticationEntryPoint;

import java.util.Collection;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/electron/user",
            "/electron/user/sportsmen",
            "/electron/user/coaches",
            "/electron/home",
            "/electron/auth/login",
            "/electron/auth/register",
            "/electron/auth/password-reset/request",
            "/electron/auth/password-reset/confirm",
            "/error/**",
            "/js/**",
            "/css/**",
            "/images/**"
    );
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/electron/auth/login")
                        .loginProcessingUrl("/electron/auth/login")
                        .usernameParameter("email")
                        .successHandler((request,
                                         response,
                                         authentication) ->{
                            Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
                            String redirectUrl = "/electron/home";

                            if (authorities.stream().anyMatch(grantedAuthority ->
                                    grantedAuthority.getAuthority().equals("COACH"))) {
                                redirectUrl = "/electron/coach/home";
                            } else if (authorities.stream().anyMatch(grantedAuthority ->
                                    grantedAuthority.getAuthority().equals("SPORTSMAN"))) {
                                redirectUrl = "/electron/sportsman/home";
                            }
                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/electron/auth/logout")
                        .logoutSuccessUrl("/electron/auth/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .cors(cors -> {})
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS.toArray(new String[0])).permitAll()
                        .requestMatchers("/electron/coach/**").hasAuthority("COACH")
                        .requestMatchers("/electron/sportsman/**").hasAuthority("SPORTSMAN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
}
