package de.htw.saar.smartcity.aggregator.dewpoint.base;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class DewpointSetupDataLoader extends GroupSetupDataLoader {

    public DewpointSetupDataLoader(DewpointApplicationProperties groupMicroserviceApplicationProperties,
                                   GroupTypeService groupTypeService,
                                   DataTypeService dataTypeService) {

        super(groupMicroserviceApplicationProperties, groupTypeService, dataTypeService);
    }
}
