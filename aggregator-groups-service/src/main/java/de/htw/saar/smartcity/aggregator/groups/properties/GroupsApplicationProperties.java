package de.htw.saar.smartcity.aggregator.groups.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
/**
@Configuration
public class GroupsApplicationProperties {

    private String basicGroupTypeName;

    @Value("${BASIC_GROUP_TYPE_NAME}")
    public void setBasicGroupTypeName(String basicGroupTypeName) {
        this.basicGroupTypeName = basicGroupTypeName;
    }

    public String getBasicGroupTypeName() {
        return basicGroupTypeName;
    }


    @PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);
        log.info(this.toString());

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoreApplicationProperties{");
        sb.append("basicGroupTypeName='").append(basicGroupTypeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
**/