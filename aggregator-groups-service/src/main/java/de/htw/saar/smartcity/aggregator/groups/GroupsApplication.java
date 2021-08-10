package de.htw.saar.smartcity.aggregator.groups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Groups application.
 */
@SpringBootApplication(scanBasePackages = "de.htw.saar.smartcity.aggregator")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@EnableJpaRepositories("de.htw.saar.smartcity.aggregator.lib")
public class GroupsApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GroupsApplication.class, args);
    }

}
