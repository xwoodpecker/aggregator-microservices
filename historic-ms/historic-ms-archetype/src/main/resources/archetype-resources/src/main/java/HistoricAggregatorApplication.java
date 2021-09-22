package $ import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

{package};

@SpringBootApplication(scanBasePackages = {"de.htw.saar.smartcity.aggregator"})
@EnableScheduling
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@EnableJpaRepositories("de.htw.saar.smartcity.aggregator.lib")
public class HistoricAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoricAggregatorApplication.class, args);
    }

}
