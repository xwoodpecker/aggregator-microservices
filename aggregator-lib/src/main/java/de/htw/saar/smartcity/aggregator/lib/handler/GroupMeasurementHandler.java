package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.*;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.GroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class GroupMeasurementHandler {

    protected static final Logger log = LoggerFactory.getLogger(GroupMeasurementHandler.class);

    protected List<GroupCombinator> groupCombinators = new ArrayList<>();

    private final StorageWrapper storageWrapper;
    protected final ProducerService producerService;
    protected final GroupService groupService;
    private final CombinatorService combinatorService;
    private final Publisher publisher;

    public GroupMeasurementHandler(StorageWrapper storageWrapper,
                                        ProducerService producerService,
                                        GroupService groupService,
                                        CombinatorService combinatorService,
                                        Publisher publisher) {

        this.storageWrapper = storageWrapper;
        this.producerService = producerService;
        this.groupService = groupService;
        this.combinatorService = combinatorService;
        this.publisher = publisher;
    }


    @PostConstruct
    private void init() {
        addCombinators();
        createCombinatorsIfNotFound();
    }


    protected abstract void addCombinators();


    private void createCombinatorsIfNotFound() {

        for(GroupCombinator groupCombinator : groupCombinators)
        {
            createCombinatorIfNotFound(groupCombinator.getName());
        }
    }


    @Transactional
    protected Combinator createCombinatorIfNotFound(String combinatorName) {

        Combinator combinator = combinatorService.findCombinatorByName(combinatorName);
        if(combinator == null) {
            combinator = new Combinator();
            combinator.setName(combinatorName);
            combinatorService.saveCombinator(combinator);
            log.info("Created Combinator " + combinatorName);
        }
        return combinator;
    }


    public void handleMeasurement(Long groupId, Long producerId, Measurement measurement) {

        final Optional<Group> optGroup = groupService.findGroupById(groupId);
        final Optional<Producer> optProducer = producerService.findProducerById(producerId);

        if(optGroup.isPresent() && optProducer.isPresent()) {
            

            final Group group = optGroup.get();
            String groupName = group.getName();

            log.info("Measurement arrived for group " + groupName + " Measurement: " + measurement);

            TempGroupMeasurement tempGroupMeasurement = storageWrapper.getTempGroupMeasurement(groupName);

            if (tempGroupMeasurement == null) {
                tempGroupMeasurement = new TempGroupMeasurement(group.getProducers().size(), groupId);
            }

            tempGroupMeasurement.putGroupMeasurementStoreMeasurement(producerId, measurement);

            if(tempGroupMeasurement.ready()) {

               for (Aggregator aggregator : group.getAggregators()) {

                    if (aggregator.getCombinator() != null) {

                        Optional<GroupCombinator> optGroupCombinator = groupCombinators.stream()
                                .filter(c -> c.getName().equals(aggregator.getCombinator().getName()))
                                .findFirst();

                        if (optGroupCombinator.isPresent()) {

                            tempGroupMeasurement.setGroupCombinator(optGroupCombinator.get());

                            Measurement m;
                            try {
                                m = tempGroupMeasurement.combine();

                            } catch (MeasurementException me) {

                                log.error("Measurement could not be combined. Temp will be deleted...");
                                storageWrapper.deleteTempGroupMeasurement(groupName);
                                return;
                            }

                            final String objName = storageWrapper.putMeasurementAndCache(groupName + "/" + tempGroupMeasurement.getAggregateName(), m);

                            if(objName != null) {

                                List<Group> activeGroups = aggregator.getGroups().stream().filter(g -> g.getActive()).collect(Collectors.toList());

                                if (activeGroups.size() > 0) {

                                    final String url = storageWrapper.getPresignedObjectUrl(objName);
                                    activeGroups.forEach(
                                            g -> publisher.publish(
                                                    String.format("%s.%s.%s", g.getGroupType().getName(), g.getId(), aggregator.getId()),
                                                    url
                                            )
                                    );
                                }
                            }
                        }
                    }
               }

                storageWrapper.deleteTempGroupMeasurement(groupName);
            }
            else {

                storageWrapper.putTempGroupMeasurement(groupName, tempGroupMeasurement);
            }
        }
    }


}