package de.htw.saar.smartcity.aggregator.picture.base;

import de.htw.saar.smartcity.aggregator.lib.base.RawSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class PictureSetupDataLoader extends RawSetupDataLoader {

    public PictureSetupDataLoader(DataTypeService dataTypeService, RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties) {
        super(dataTypeService, rawMicroserviceApplicationProperties);
    }
}
