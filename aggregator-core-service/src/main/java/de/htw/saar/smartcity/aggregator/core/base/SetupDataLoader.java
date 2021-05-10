package de.htw.saar.smartcity.aggregator.core.base;

import de.htw.saar.smartcity.aggregator.core.properties.CoreApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final CoreApplicationProperties coreApplicationProperties;
    private final GroupTypeService groupTypeService;


    public SetupDataLoader(CoreApplicationProperties coreApplicationProperties, GroupTypeService groupTypeService) {
        this.coreApplicationProperties = coreApplicationProperties;
        this.groupTypeService = groupTypeService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup)
            return;

        createGroupTypeIfNotFound(coreApplicationProperties.getBasicGroupTypeName());


        alreadySetup = true;
    }


    @Transactional
    GroupType createGroupTypeIfNotFound(String groupTypeName) {

        GroupType groupType = groupTypeService.findGroupTypeByName(groupTypeName);
        if(groupType == null) {
            groupType = new GroupType();
            groupType.setName(groupTypeName);
            groupTypeService.saveGroupType(groupType);
        }
        return groupType;
    }
}