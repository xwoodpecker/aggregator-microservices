package de.htw.saar.smartcity.aggregator.water.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"de.htw.saar.smartcity.aggregator.lib", "de.htw.saar.smartcity.aggregator.water.aggregator"})
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@EnableJpaRepositories("de.htw.saar.smartcity.aggregator.lib")
public class WaterAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterAggregatorApplication.class, args);
    }

}
