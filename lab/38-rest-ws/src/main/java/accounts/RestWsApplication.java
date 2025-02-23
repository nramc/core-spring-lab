package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class RestWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWsApplication.class, args);
    }

    // TODO-00: In this lab, you are going to exercise the following:
    // - Implementing controller handlers that handle HTTP POST, PUT, DELETE requests
    // - Using proper annotation for extracting data from incoming request
    // - Creating URI for a newly created item in handling HTTP POST request
    // - Exercising RestTemplate for sending HTTP requests to the server application

}
