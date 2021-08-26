package ${package}.base;

import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class GroupSetupDataLoaderImpl extends GroupSetupDataLoader {


    public GroupSetupDataLoaderImpl(GroupMicroserviceApplicationProperties groupMicroserviceApplicationProperties,
                                    GroupTypeService groupTypeService,
                                    DataTypeService dataTypeService) {

        super(groupMicroserviceApplicationProperties, groupTypeService, dataTypeService);
    }
}
