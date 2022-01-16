package de.htw.saar.smartcity.aggregator.water.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WaterAggregatorApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${WATER_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${WATER_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${WATER_AGGREGATOR_MICROSERVICE_DATA_TYPE_NAME}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${WATER_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }


}
