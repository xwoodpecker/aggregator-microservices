package de.htw.saar.smartcity.aggregator.humidity.aggregator.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorModels;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HumidityAggregatorGroupMeasurementHandler extends GroupMeasurementHandler {

    public HumidityAggregatorGroupMeasurementHandler(StorageWrapper storageWrapper,
                                                        ProducerService producerService,
                                                        GroupService groupService,
                                                        CombinatorService combinatorService,
                                                        Publisher publisher) {
        super(storageWrapper, producerService, groupService, combinatorService, publisher);
    }

    @Override
    protected void addCombinators() {

        combinatorModels.add(CombinatorModels.averageCombinatorModel);
        combinatorModels.add(CombinatorModels.minimumCombinatorModel);
        combinatorModels.add(CombinatorModels.maximumCombinatorModel);

    }
}
