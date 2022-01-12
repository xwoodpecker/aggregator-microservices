package de.htw.saar.smartcity.historic.dewpoint.aggregator.scheduler;

import de.htw.saar.smartcity.aggregator.lib.model.HistoricCombinatorModels;
import de.htw.saar.smartcity.aggregator.lib.properties.HistoricMicroserviceAggregatorApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.scheduler.HistoricAggregatorScheduler;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.HistoricCombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HistoricDewpointAggregatorScheduler extends HistoricAggregatorScheduler {


    public HistoricDewpointAggregatorScheduler(HistoricMicroserviceAggregatorApplicationProperties applicationProperties,
                                               DataTypeService dataTypeService, ProducerService producerService,
                                               HistoricCombinatorService historicCombinatorService,
                                               HistoricStorageWrapper storageWrapper) {

        super(applicationProperties, dataTypeService, producerService, historicCombinatorService, storageWrapper);
    }

    @Override
    protected void addHistoricCombinators() {

        historicCombinatorModels.add(HistoricCombinatorModels.averageHistoricCombinatorModel);
        historicCombinatorModels.add(HistoricCombinatorModels.minimumHistoricCombinatorModel);
        historicCombinatorModels.add(HistoricCombinatorModels.maximumHistoricCombinatorModel);

    }

}
