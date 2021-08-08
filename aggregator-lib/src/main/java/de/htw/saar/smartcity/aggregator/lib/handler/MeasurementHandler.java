package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MeasurementHandler {


    protected final StorageWrapper storageWrapper;
    protected final Publisher publisher;

    protected MeasurementHandler(StorageWrapper storageWrapper, Publisher publisher) {
        this.storageWrapper = storageWrapper;
        this.publisher = publisher;
    }

    protected void processMeasurement(Producer producer, String path, Measurement m) {

        final String objName = storageWrapper.putMeasurement(path, m);

        if(producer.getExportAsMetric())
            storageWrapper.cacheMeasurement(path, m);

        if(objName != null) {

            List<Group> activeGroups = producer.getGroups().stream().filter(g -> g.getActive()).collect(Collectors.toList());

            if (activeGroups.size() > 0) {

                final String url = storageWrapper.getPresignedObjectUrl(objName);
                activeGroups.forEach(
                        g -> publisher.publish(
                                String.format("%s.%s.%s", g.getGroupType().getName(), g.getId(), producer.getId()),
                                url
                        )
                );
            }
        }
    }
}
