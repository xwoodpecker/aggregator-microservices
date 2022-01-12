package de.htw.saar.smartcity.aggregator.lib.properties;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Historic aggregator application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public abstract class HistoricMicroserviceAggregatorApplicationProperties extends  HistoricAggregatorApplicationProperties implements MinioApplicationProperties {

     /**
     * The Delete raw measurements.
     */
    protected boolean deleteRawMeasurements;

    /**
     * Sets delete raw measurements.
     *
     * @param deleteRawMeasurements the delete raw measurements
     */
    protected abstract void setDeleteRawMeasurements(boolean deleteRawMeasurements);

    public boolean getDeleteRawMeasurements() {
        return this.deleteRawMeasurements;
    }
}
