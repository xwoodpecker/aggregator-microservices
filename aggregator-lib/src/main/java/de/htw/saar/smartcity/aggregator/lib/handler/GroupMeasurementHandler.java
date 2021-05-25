package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;

public abstract class GroupMeasurementHandler {

    protected final StorageWrapper storageWrapper;

    public GroupMeasurementHandler(StorageWrapper storageWrapper) {

        this.storageWrapper = storageWrapper;

    }


    public abstract void handleMeasurement(Long groupId, Long groupMemberId, Measurement measurement);

}
