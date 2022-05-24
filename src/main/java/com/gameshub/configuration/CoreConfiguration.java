package com.gameshub.configuration;

import com.gameshub.domain.user.User;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableScheduling
@EnableSwagger2
@RequiredArgsConstructor
public class CoreConfiguration {

    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gameshub"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public List<User> confirmedUsers() {
        return userRepository.retrieveVerifiedUsers();
    }

    @Bean
    @Scope(value = "prototype")
    public List<User> admins() {
        return userRepository.retrieveAdmins();
    }
}
