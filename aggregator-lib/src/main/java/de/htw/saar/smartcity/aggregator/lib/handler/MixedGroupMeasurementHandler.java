package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.*;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.MixedTempGroupMeasurement;
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


public abstract class MixedGroupMeasurementHandler extends GroupMeasurementHandler{

    private static final Logger log = LoggerFactory.getLogger(MixedGroupMeasurementHandler.class);


    protected List<MixedGroupCombinator> mixedGroupCombinators = new ArrayList<>();
    private final ProducerService producerService;
    private final GroupService groupService;
    private final CombinatorService combinatorService;
    private final Publisher publisher;

    public MixedGroupMeasurementHandler(StorageWrapper storageWrapper,
                                        ProducerService producerService,
                                        GroupService groupService,
                                        CombinatorService combinatorService,
                                        Publisher publisher) {

        super(storageWrapper);
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


    //todo: refactor
    private void createCombinatorsIfNotFound() {

        for(MixedGroupCombinator mixedGroupCombinator : mixedGroupCombinators)
        {
            //createCombinatorIfNotFound(mixedGroupCombinator.getName());
        }
    }


   /** @Transactional
    Combinator createCombinatorIfNotFound(String combinatorName) {

        Combinator combinator = combinatorService.findCombinatorByName(combinatorName);
        if(combinator == null) {
            combinator = new Combinator();
            combinator.setName(combinatorName);
            combinatorService.saveCombinator(combinator);
        }
        return combinator;
    } **/


    public void handleMeasurement(Long groupId, Long producerId, Measurement measurement) {

        Optional<Group> optGroup = groupService.findGroupById(groupId);
        Optional<Producer> optProducer = producerService.findProducerById(producerId);

        if(optGroup.isPresent() && optProducer.isPresent()) {
            Group group = optGroup.get();

            MixedTempGroupMeasurement tempGroupMeasurement = (MixedTempGroupMeasurement) storageWrapper.getTempGroupMeasurement(group.getName());

            if (tempGroupMeasurement == null) {
                tempGroupMeasurement = new MixedTempGroupMeasurement(group);
            }

            tempGroupMeasurement.getProducerIdMeasurementMap().put(producerId, measurement);

            if(tempGroupMeasurement.ready()) {

               /** for (Aggregator aggregator : group.getAggregators()) {

                    if (aggregator.getCombinator() != null) {

                        Optional<MixedGroupCombinator> optMixedGroupCombinator = mixedGroupCombinators.stream()
                                //.filter(c -> c.getName().equals(aggregator.getCombinator().getName()))
                                .findFirst();
                        if (optMixedGroupCombinator.isPresent()) {
                            tempGroupMeasurement.setGroupCombinator(optMixedGroupCombinator.get());

                            Measurement m = tempGroupMeasurement.combine();
                            String url = storageWrapper.putMeasurement(group.getName() + "/" + tempGroupMeasurement.getAggregateName(), m);

                            aggregator.getGroups().forEach(
                                    g -> publisher.publish(
                                            String.format("%s.%s.%s", g.getGroupType().getName(), g.getId(), aggregator.getId()),
                                            url
                                    )
                            );
                        }

                    } **/

               // }

                //todo: delete or reset map ?
                //storageWrapper.deleteTempGroupMeasurement(group.getName());
            }
            else {

                storageWrapper.putTempGroupMeasurement(group.getName(), tempGroupMeasurement);
            }
        }
    }


}