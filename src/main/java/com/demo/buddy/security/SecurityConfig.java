package com.demo.buddy.security;

import com.demo.buddy.entity.User;
import com.demo.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((req) -> req.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/transactions").authenticated()
                        .requestMatchers("/contacts").authenticated()
                        .requestMatchers("/profil").authenticated()
                        .requestMatchers("/updateUserInfo").authenticated()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/login/*").anonymous()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("mdp")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"))
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login/github")
                        .redirectionEndpoint()
                        .baseUri("/login/oauth2/code/*")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("remove")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();

    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public UserService userDetailsService() {
        return new UserService();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

