package de.htw.saar.smartcity.aggregator.core.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreApplicationProperties extends ApplicationProperties {

    private String basicGroupTypeName;

    @Value("${BASIC_GROUP_TYPE_NAME}")
    public void setBasicGroupTypeName(String basicGroupTypeName) {
        this.basicGroupTypeName = basicGroupTypeName;
    }

    public String getBasicGroupTypeName() {
        return basicGroupTypeName;
    }


    @Override
    public String toString()
    {
        return new StringBuilder(super.toString())
                .append("BasicGroupTypeName=" + basicGroupTypeName)
                .toString();
    }
}
