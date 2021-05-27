package de.htw.saar.smartcity.aggregator.temperature.aggregator.base;

import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.temperature.aggregator.properties.TemperatureAggregatorApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class TemperatureAggregatorSetupDataLoader extends GroupSetupDataLoader {


    public TemperatureAggregatorSetupDataLoader(TemperatureAggregatorApplicationProperties temperatureAggregatorApplicationProperties,
                                                GroupTypeService groupTypeService,
                                                DataTypeService dataTypeService) {

        super(temperatureAggregatorApplicationProperties, groupTypeService, dataTypeService);
    }
}
