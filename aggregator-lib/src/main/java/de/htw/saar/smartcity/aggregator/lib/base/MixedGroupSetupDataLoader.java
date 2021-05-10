package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.properties.MixedGroupMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class MixedGroupSetupDataLoader extends SetupDataLoader{

    protected final MixedGroupMicroserviceApplicationProperties mixedGroupMicroserviceApplicationProperties;
    protected final GroupTypeService groupTypeService;
    protected final SensorTypeService sensorTypeService;

    public MixedGroupSetupDataLoader(MicroserviceService microserviceService,
                                     MixedGroupMicroserviceApplicationProperties mixedGroupMicroserviceApplicationProperties,
                                     GroupTypeService groupTypeService,
                                     SensorTypeService sensorTypeService) {
        super(microserviceService, mixedGroupMicroserviceApplicationProperties);
        this.mixedGroupMicroserviceApplicationProperties = mixedGroupMicroserviceApplicationProperties;
        this.groupTypeService = groupTypeService;
        this.sensorTypeService = sensorTypeService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;

        createMicroserviceIfNotFound(mixedGroupMicroserviceApplicationProperties.getMicroserviceName(),
                mixedGroupMicroserviceApplicationProperties.getMicroserviceURL());
        createGroupTypeIfNotFound(mixedGroupMicroserviceApplicationProperties.getMicroserviceGroupTypeName());

        alreadySetup = true;
    }


    protected abstract GroupType createGroupTypeIfNotFound(String groupTypeName);
}
