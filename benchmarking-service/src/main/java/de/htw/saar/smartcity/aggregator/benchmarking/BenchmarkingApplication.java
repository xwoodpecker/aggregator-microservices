package de.htw.saar.smartcity.aggregator.benchmarking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "de.htw.saar.smartcity.aggregator")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@EnableJpaRepositories("de.htw.saar.smartcity.aggregator.lib")
public class BenchmarkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkingApplication.class, args);
    }

}
