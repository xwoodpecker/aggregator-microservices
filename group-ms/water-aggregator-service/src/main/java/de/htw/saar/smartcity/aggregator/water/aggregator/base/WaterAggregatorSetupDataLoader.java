package de.htw.saar.smartcity.aggregator.water.aggregator.base;

import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.water.aggregator.properties.WaterAggregatorApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class WaterAggregatorSetupDataLoader extends GroupSetupDataLoader {


    public WaterAggregatorSetupDataLoader(WaterAggregatorApplicationProperties waterAggregatorApplicationProperties,
                                                GroupTypeService groupTypeService,
                                                DataTypeService dataTypeService) {

        super(waterAggregatorApplicationProperties, groupTypeService, dataTypeService);
    }
}
