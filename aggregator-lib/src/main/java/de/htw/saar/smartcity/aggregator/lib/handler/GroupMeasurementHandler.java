package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorModel;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The type Group measurement handler.
 */
public abstract class GroupMeasurementHandler extends MeasurementHandler{

    /**
     * The constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(GroupMeasurementHandler.class);

    /**
     * The Combinator models.
     */
    protected List<CombinatorModel> combinatorModels = new ArrayList<>();

    /**
     * The Producer service.
     */
    protected final ProducerService producerService;
    /**
     * The Group service.
     */
    protected final GroupService groupService;
    private final CombinatorService combinatorService;

    /**
     * Instantiates a new Group measurement handler.
     *
     * @param storageWrapper    the storage wrapper
     * @param publisher         the publisher
     * @param producerService   the producer service
     * @param groupService      the group service
     * @param combinatorService the combinator service
     */
    protected GroupMeasurementHandler(StorageWrapper storageWrapper,
                                      Publisher publisher,
                                      ProducerService producerService,
                                      GroupService groupService,
                                      CombinatorService combinatorService) {
        super(storageWrapper, publisher);
        this.producerService = producerService;
        this.groupService = groupService;
        this.combinatorService = combinatorService;
    }


    /**
     * necessary initialization after object construction
     */
    @PostConstruct
    private void init() {
        addCombinators();
        createCombinatorsIfNotFound();
    }


    /**
     * Add combinators.
     */
    protected abstract void addCombinators();


    /**
     * create combinators needed in case not found
     */
    private void createCombinatorsIfNotFound() {

        for(CombinatorModel combinatorModel : combinatorModels)
        {
            createCombinatorIfNotFound(combinatorModel.getName());
        }
    }


    /**
     * Create combinator if not found combinator.
     *
     * @param combinatorName the combinator name
     * @return the combinator
     */
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


    /**
     * Handle measurement.
     *
     * @param groupId     the group id
     * @param producerId  the producer id
     * @param measurement the measurement
     */
    public void handleMeasurement(Long groupId, Long producerId, Measurement measurement) {

        Group group = null;
        String groupName = null;
        try {
            final Optional<Group> optGroup = groupService.findGroupById(groupId);
            final Optional<Producer> optProducer = producerService.findProducerById(producerId);

            if (optGroup.isPresent() && optProducer.isPresent()) {


                group = optGroup.get();
                groupName = group.getName();

                log.info("Measurement arrived for group " + groupName + " Measurement: " + measurement);

                TempGroupMeasurement tempGroupMeasurement = null;
                if(group.getProducers().size() > 1) {
                    tempGroupMeasurement = storageWrapper.getTempGroupMeasurement(groupName);
                }

                if (tempGroupMeasurement == null) {
                    tempGroupMeasurement = new TempGroupMeasurement(group.getProducers().size(), groupId);
                }

                tempGroupMeasurement.putGroupMeasurementStoreMeasurement(producerId, measurement);

                if (tempGroupMeasurement.ready()) {

                    for (Aggregator aggregator : group.getAggregators()) {

                        if (aggregator.getCombinator() != null) {

                            Optional<CombinatorModel> optGroupCombinator = combinatorModels.stream()
                                    .filter(c -> c.getName().equals(aggregator.getCombinator().getName()))
                                    .findFirst();

                            if (optGroupCombinator.isPresent()) {

                                tempGroupMeasurement.setGroupCombinator(optGroupCombinator.get());

                                Measurement m = tempGroupMeasurement.combine();

                                String path = aggregator.getObjectStorePath();
                                if (Utils.isBlankOrNull(path)) {
                                    path = groupName + "/" + tempGroupMeasurement.getAggregateName();
                                    aggregator.setObjectStorePath(path);
                                    storageWrapper.putAggregator(aggregator);
                                    log.info("Aggregator updated - ObjectStorePath set");
                                }

                                processMeasurement(aggregator, path, m);
                            }
                        }
                    }

                    if(group.getProducers().size() > 1)
                        storageWrapper.deleteTempGroupMeasurement(groupName);

                } else {

                    storageWrapper.putTempGroupMeasurement(groupName, tempGroupMeasurement);
                }
            }
        } catch (MeasurementException me) {
            log.error("Measurement could not be combined. Temp will be deleted...");

            if(groupName != null && group.getProducers().size() > 1)
                storageWrapper.deleteTempGroupMeasurement(groupName);

        } catch (Exception e) {
            log.error("Unknown exception occurred.");
            //e.printStackTrace();

        }
    }
}