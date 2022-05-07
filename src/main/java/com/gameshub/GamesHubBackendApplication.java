package com.gameshub;

import com.gameshub.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class GamesHubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamesHubBackendApplication.class, args);
    }

}
