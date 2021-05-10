package de.htw.saar.smartcity.aggregator.dewpoint.base;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.base.MixedGroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.humidity.properties.HumidityApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import de.htw.saar.smartcity.aggregator.lib.properties.MixedGroupMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Setup data loader.
 */
@Component
public class DewpointSetupDataLoader extends MixedGroupSetupDataLoader {

    private DewpointApplicationProperties dewpointApplicationProperties;

    public DewpointSetupDataLoader(MicroserviceService microserviceService,
                                   DewpointApplicationProperties mixedGroupMicroserviceApplicationProperties,
                                   GroupTypeService groupTypeService,
                                   SensorTypeService sensorTypeService) {
        super(microserviceService, mixedGroupMicroserviceApplicationProperties, groupTypeService, sensorTypeService);
        this.dewpointApplicationProperties = mixedGroupMicroserviceApplicationProperties;
    }

    @Override
    @Transactional
    protected GroupType createGroupTypeIfNotFound(String groupTypeName) {


        GroupType groupType = groupTypeService.findGroupTypeByName(groupTypeName);
        if(groupType == null) {
            groupType = new GroupType();
            groupType.setName(groupTypeName);
            List<SensorType> sensorTypeList = new ArrayList<>();
            sensorTypeList.add(sensorTypeService.findSensorTypeByName(dewpointApplicationProperties.getTemperatureSensorTypeName()));
            sensorTypeList.add(sensorTypeService.findSensorTypeByName(dewpointApplicationProperties.getHumiditySensorTypeName()));
            groupType.setSensorTypes(sensorTypeList);
            groupTypeService.saveGroupType(groupType);
        }
        return groupType;
    }
}
