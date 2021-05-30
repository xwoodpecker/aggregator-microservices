package de.htw.saar.smartcity.aggregator.lib.handler;

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
            //baseGroupMeasurement.getSensorNameMeasurementMap().putIfAbsent();
        }

        for(BaseGroupCombinator baseGroupCombinator : baseGroupCombinators) {

            groupMeasurement.setGroupCombinator(baseGroupCombinator);
            groupMeasurement.combine();

            //storageWrapper.putMeasurement(group, groupMeasurement);
        }
    }
} **/
