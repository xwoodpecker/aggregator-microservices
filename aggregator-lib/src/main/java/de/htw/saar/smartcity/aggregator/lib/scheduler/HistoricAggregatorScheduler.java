package de.htw.saar.smartcity.aggregator.lib.scheduler;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.HistoricCombinator;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.model.HistoricCombinatorModel;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.HistoricMicroserviceAggregatorApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.HistoricCombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Historic aggregator scheduler.
 */
public abstract class HistoricAggregatorScheduler {

    private final HistoricMicroserviceAggregatorApplicationProperties applicationProperties;

    private static final Logger log = LoggerFactory.getLogger(HistoricAggregatorScheduler.class);

    private final ProducerService producerService;

    private final HistoricCombinatorService historicCombinatorService;

    private final HistoricStorageWrapper storageWrapper;

    private final DataType dataType;


    /**
     * The Historic combinator models.
     */
    protected List<HistoricCombinatorModel> historicCombinatorModels = new ArrayList<>();

    /**
     * Instantiates a new Historic aggregator scheduler.
     *
     * @param applicationProperties     the application properties
     * @param dataTypeService           the data type service
     * @param producerService           the producer service
     * @param historicCombinatorService the historic combinator service
     * @param storageWrapper            the storage wrapper
     */
    public HistoricAggregatorScheduler(HistoricMicroserviceAggregatorApplicationProperties applicationProperties,
                                       DataTypeService dataTypeService,
                                       ProducerService producerService,
                                       HistoricCombinatorService historicCombinatorService,
                                       HistoricStorageWrapper storageWrapper) {

        this.applicationProperties = applicationProperties;
        this.producerService = producerService;
        this.historicCombinatorService = historicCombinatorService;
        this.storageWrapper = storageWrapper;

        dataType = dataTypeService.findDataTypeByName(applicationProperties.getMicroServiceDataType());
    }


    /**
     * necessary initialization after object construction
     */
    @PostConstruct
    private void init() {
        addHistoricCombinators();
        createHistoricCombinatorsIfNotFound();
    }

    /**
     * Add historic combinators.
     */
    protected abstract void addHistoricCombinators();

    private void createHistoricCombinatorsIfNotFound() {

        for(HistoricCombinatorModel historicCombinatorModel : historicCombinatorModels)
        {
            createHistoricCombinatorIfNotFound(historicCombinatorModel.getName());
        }
    }

    /**
     * Create historic combinator if not found historic combinator.
     *
     * @param historicCombinatorName the historic combinator name
     * @return the historic combinator
     */
    @Transactional
    protected HistoricCombinator createHistoricCombinatorIfNotFound(String historicCombinatorName) {

        HistoricCombinator historicCombinator = historicCombinatorService.findHistoricCombinatorByName(historicCombinatorName);
        if(historicCombinator == null) {
            historicCombinator = new HistoricCombinator();
            historicCombinator.setName(historicCombinatorName);
            historicCombinatorService.saveHistoricCombinator(historicCombinator);
            log.info("Created HistoricCombinator " + historicCombinatorName);
        }
        return historicCombinator;
    }

    /**
     * compute the annual aggregates
     */
    @Scheduled(cron="@yearly")
    private void computeAnnually(){
        String timePrefix = String.format("/%d/",
                LocalDateTime.now().getYear() //- 1
        );

        computeHistoricsForTimePrefix(timePrefix);
    }

    /**
     * compute the monthly aggregates
     */
    @Scheduled(cron="@monthly") // 0 0 0 1 * *
    private void computeMonthly() {

        String timePrefix = String.format("/%d/%d/",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue() //-1
        );

        computeHistoricsForTimePrefix(timePrefix);
    }

    /**@Scheduled(cron="@weekly") // 0 0 0 * * 0
    private void computeWeekly() {
    }
    }**/

    /**
     * compute the daily aggregates
     */
    @Scheduled(cron="@daily") // 0 0 0 0 * *
    private void computeDaily() {

        LocalDateTime time = LocalDateTime.now().minusDays(1);
        String timePrefix = String.format("/%d/%d/%d/",
                time.getYear(),
                time.getMonthValue(),
                time.getDayOfMonth()
        );

        computeHistoricsForTimePrefix(timePrefix);
    }

    /**
     * compute the hourly aggregates
     */
    @Scheduled(cron="@hourly") //0 0 * * * *
    private void computeHourly() {

        LocalDateTime time = LocalDateTime.now().minusHours(1);
        String timePrefix = String.format("/%d/%d/%d/%d/",
                time.getYear(),
                time.getMonthValue(),
                time.getDayOfMonth(),
                time.getHour()
        );

        computeRawForTimePrefix(timePrefix);

    }

    /**
     * compute the aggregates for the raw measurements for the given time prefix
     *
     * @param timePrefix the given prefix
     */
    private void computeRawForTimePrefix(String timePrefix) {

        List<Producer> producerList = producerService.findAllProducersByDataType(dataType);

        for(Producer producer : producerList) {

            String objectsPath = producer.getObjectStorePath() + timePrefix;
            List<Measurement> measurements;
            if(applicationProperties.getDeleteRawMeasurements())
                measurements = storageWrapper.deleteAndReturnMeasurementsByPrefix(objectsPath);
            else
                measurements = storageWrapper.getMeasurementsByPrefix(objectsPath);

            if(measurements.size() > 0) {
                for (HistoricCombinatorModel historicCombinatorModel : historicCombinatorModels) {

                    storageWrapper.putHistoricMeasurementCheckCompression(
                            parent(objectsPath) + "/historic/" + historicCombinatorModel.getName() + "/" + lastDir(objectsPath),
                            getMeasurement(historicCombinatorModel, measurements)
                    );
                }
            }
        }

    }

    /**
     * return the parent path for given objects path
     * @param objectsPath the input path
     * @return the parent path
     */
    private String parent(String objectsPath) {

        String temp = objectsPath.substring(0, objectsPath.length() - 1);
        return temp.substring(0, temp.lastIndexOf("/"));
    }

    /**
     * return the last directory for given objects path
     * @param objectsPath the input path
     * @return the last directory
     */
    private String lastDir(String objectsPath) {
        String temp = objectsPath.substring(0, objectsPath.length() - 1);
        return temp.substring(temp.lastIndexOf("/") + 1);
    }

    /**
     * compute historic aggregates for given time prefix
     * @param timePrefix the given time prefix
     */
    private void computeHistoricsForTimePrefix(String timePrefix) {

        List<Producer> producerList = producerService.findAllProducersByDataType(dataType);

        for(Producer producer : producerList) {

            for(HistoricCombinatorModel historicCombinatorModel : historicCombinatorModels) {

                String basePath = producer.getObjectStorePath() + timePrefix;
                String objectsPath = basePath + "historic/" + historicCombinatorModel.getName() + "/";
                List<Measurement> measurements = storageWrapper.getMeasurementsByPrefix(objectsPath);

                if(measurements.size() > 0) {
                    storageWrapper.putHistoricMeasurementCheckCompression(
                            parent(basePath) + "/historic/" + historicCombinatorModel.getName() + "/" + lastDir(basePath),
                            getMeasurement(historicCombinatorModel, measurements)
                    );
                }
            }
        }
    }

    /**
     * combine the measurement by applying the HistoricCombinatorModel to the list of measurements
     * @param historicCombinatorModel the model used to calculate
     * @param measurements the measurements for the computation
     * @return the historic aggregate
     */
    private Measurement getMeasurement(HistoricCombinatorModel historicCombinatorModel, List<Measurement> measurements) {
        Double combined = (Double) historicCombinatorModel.getFunction().apply(measurements);
        Measurement m = new Measurement();
        m.setValue(combined);
        m.setTime(LocalDateTime.now());
        return m;
    }

}
