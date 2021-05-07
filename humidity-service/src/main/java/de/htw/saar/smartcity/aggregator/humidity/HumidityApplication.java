package de.htw.saar.smartcity.aggregator.humidity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "de.htw.saar.smartcity.aggregator")
@EntityScan("de.htw.saar.smartcity.aggregator")
@EnableJpaRepositories("de.htw.saar.smartcity.aggregator")
public class HumidityApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumidityApplication.class, args);
    }

}
