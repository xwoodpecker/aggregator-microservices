package de.htw.saar.smartcity.aggregator.airquality.aggregator.base;

import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.airquality.aggregator.properties.AirqualityAggregatorApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class AirqualityAggregatorSetupDataLoader extends GroupSetupDataLoader {


    public AirqualityAggregatorSetupDataLoader(AirqualityAggregatorApplicationProperties airqualityAggregatorApplicationProperties,
                                                GroupTypeService groupTypeService,
                                                DataTypeService dataTypeService) {

        super(airqualityAggregatorApplicationProperties, groupTypeService, dataTypeService);
    }
}
