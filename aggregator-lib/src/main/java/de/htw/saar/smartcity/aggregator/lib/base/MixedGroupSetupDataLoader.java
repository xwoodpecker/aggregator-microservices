package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.properties.MixedGroupMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class MixedGroupSetupDataLoader extends SetupDataLoader{

    protected final MixedGroupMicroserviceApplicationProperties mixedGroupMicroserviceApplicationProperties;
    protected final GroupTypeService groupTypeService;
    protected final DataTypeService dataTypeService;

    public MixedGroupSetupDataLoader(MixedGroupMicroserviceApplicationProperties mixedGroupMicroserviceApplicationProperties,
                                     GroupTypeService groupTypeService,
                                     DataTypeService dataTypeService) {

        this.mixedGroupMicroserviceApplicationProperties = mixedGroupMicroserviceApplicationProperties;
        this.groupTypeService = groupTypeService;
        this.dataTypeService = dataTypeService;
    }

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup)
            return;

        createGroupTypeIfNotFound(mixedGroupMicroserviceApplicationProperties.getMicroserviceGroupTypeName());

        alreadySetup = true;
    }


    protected abstract GroupType createGroupTypeIfNotFound(String groupTypeName);
}
