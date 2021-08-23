package de.htw.saar.smartcity.aggregator.management.properties;

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