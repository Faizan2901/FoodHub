package com.codemind.FoodHub.security;

import com.codemind.FoodHub.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DefaultSecurity {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
            return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService){

            DaoAuthenticationProvider auth=new DaoAuthenticationProvider();
            auth.setUserDetailsService(userService);
            auth.setPasswordEncoder(bCryptPasswordEncoder());
            return auth;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(configurer->

                configurer
                        .requestMatchers("/").hasRole("CUSTOMER")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/systems/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

        )
                .formLogin(form->

                form
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/authenticateUser")
                        .permitAll()
        )
                .logout(LogoutConfigurer::permitAll
                )
                .exceptionHandling();

        return httpSecurity.build();

    }

}
