package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.BaseGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.BaseTempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**public abstract class BaseGroupMeasurementHandler extends GroupMeasurementHandler{

    private static final Logger log = LoggerFactory.getLogger(BaseGroupMeasurementHandler.class);

    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;

    protected List<BaseGroupCombinator> baseGroupCombinators = new ArrayList<>();

    public BaseGroupMeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;

    }

    @PostConstruct
    protected abstract void addCombinators();

    public void handleGroupMeasurement(BaseTempGroupMeasurement groupMeasurement){
        Group group = groupMeasurement.getGroup();
        log.info("Message arrived for group " + group.getName());

        List<Measurement> measurements = groupMeasurement.getMeasurements();



        for (Measurement measurement : measurements) {
            //todo
            //baseGroupMeasurement.getSensorNameMeasurementMap().putIfAbsent();
        }

        for(BaseGroupCombinator baseGroupCombinator : baseGroupCombinators) {

            groupMeasurement.setGroupCombinator(baseGroupCombinator);
            groupMeasurement.combine();

            //storageWrapper.putMeasurement(group, groupMeasurement);
        }
    }
} **/
