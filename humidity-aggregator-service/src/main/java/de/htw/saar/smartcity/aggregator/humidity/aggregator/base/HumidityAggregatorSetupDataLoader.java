package de.htw.saar.smartcity.aggregator.humidity.aggregator.base;

import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.humidity.aggregator.properties.HumidityAggregatorApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class HumidityAggregatorSetupDataLoader extends GroupSetupDataLoader {


    public HumidityAggregatorSetupDataLoader(HumidityAggregatorApplicationProperties humidityAggregatorApplicationProperties,
                                                GroupTypeService groupTypeService,
                                                DataTypeService dataTypeService) {

        super(humidityAggregatorApplicationProperties, groupTypeService, dataTypeService);
    }
}
