package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.MixedTempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.GroupMemberService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MixedGroupMeasurementHandler extends GroupMeasurementHandler{

    private static final Logger log = LoggerFactory.getLogger(MixedGroupMeasurementHandler.class);


    protected List<MixedGroupCombinator> mixedGroupCombinators = new ArrayList<>();
    private final GroupMemberService groupMemberService;

    public MixedGroupMeasurementHandler(StorageWrapper storageWrapper, GroupMemberService groupMemberService) {
        super(storageWrapper);
        this.groupMemberService = groupMemberService;
    }


    @PostConstruct
    private void init() {
        addCombinators();
    }

    protected abstract void addCombinators();


    /**public void handleGroupMeasurement(TempGroupMeasurement groupMeasurement){
        Group group = groupMeasurement.getGroup();
        log.info("Message arrived for group " + group.getName() );

        List<Measurement> measurements = groupMeasurement.getSensorTypeMeasurementMap().values().stream().collect(Collectors.toList());



        for (Measurement measurement : measurements) {
            String measurement = measurements.get(sensorName);
            Sensor sensor = storageWrapper.getSensor(sensorName);
            SensorType sensorType = sensor.getSensorType();
            Long sensorTypeId = sensorType.getId();

            Measurement m = sensorTypeIdMeasurementFactoryMap.get(sensorTypeId).create(sensor, measurement);
            mixedGroupMeasurement.getSensorTypeMeasurementMap().putIfAbsent(sensorType.getId(), m);
        }

        for(MixedGroupCombinator mixedGroupCombinator : mixedGroupCombinators) {

            groupMeasurement.setGroupCombinator(mixedGroupCombinator);
            groupMeasurement.combine();

            //storageWrapper.putMeasurement(group, groupMeasurement);
        }
    } **/

    public void handleMeasurement(Long groupId, Long groupMemberId, Measurement measurement) {

        Optional<GroupMember> optGroup = groupMemberService.findGroupMemberById(groupId);
        Optional<GroupMember> optGroupMember = groupMemberService.findGroupMemberById(groupMemberId);

        if(optGroup.isPresent() && optGroupMember.isPresent()) {
            Group group = (Group) optGroup.get();

            MixedTempGroupMeasurement tempGroupMeasurement = (MixedTempGroupMeasurement) storageWrapper.getTempGroupMeasurement(group.getName());

            if (tempGroupMeasurement == null) {
                tempGroupMeasurement = new MixedTempGroupMeasurement(group);
            }

            tempGroupMeasurement.getMemberIdMeasurementMap().put(groupMemberId, measurement);

            if(tempGroupMeasurement.ready()) {

                for(MixedGroupCombinator mixedGroupCombinator : mixedGroupCombinators) {

                    tempGroupMeasurement.setGroupCombinator(mixedGroupCombinator);
                    Measurement m = tempGroupMeasurement.combine();

                    String url = storageWrapper.putMeasurement(group.getName() + "/" + tempGroupMeasurement.getAggregateName(), m);

                    //todo: oof

                    storageWrapper.deleteTempGroupMeasurement(group.getName());
                }
            } else {

                storageWrapper.putTempGroupMeasurement(group.getName(), tempGroupMeasurement);
            }


        }
    }
}
