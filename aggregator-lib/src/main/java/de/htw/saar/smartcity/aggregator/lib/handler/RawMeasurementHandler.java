package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawBasicApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Raw measurement handler.
 */
public abstract class RawMeasurementHandler extends MeasurementHandler {

    /**
     * The constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(RawMeasurementHandler.class);

    private final RawBasicApplicationProperties rawBasicApplicationProperties;
    private final DataTypeService dataTypeService;
    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;
    private final Publisher publisher;

    /**
     * Instantiates a new Raw measurement handler.
     * @param rawBasicApplicationProperties        the basic application properties
     * @param storageWrapper                       the storage wrapper
     * @param publisher                            the publisher
     * @param dataTypeService                      the data type service
     * @param measurementFactory                   the measurement factory
     */
    protected RawMeasurementHandler(StorageWrapper storageWrapper, Publisher publisher, RawBasicApplicationProperties rawBasicApplicationProperties, DataTypeService dataTypeService, MeasurementFactory measurementFactory) {
        super(storageWrapper, publisher);
        this.rawBasicApplicationProperties = rawBasicApplicationProperties;
        this.dataTypeService = dataTypeService;
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
        this.publisher = publisher;
    }


    /**
     * Handle message.
     *
     * @param sensorMeasurement the sensor measurement
     */
    public void handleMessage(SensorMeasurement sensorMeasurement){

        final String sensorName = sensorMeasurement.getSensorName();
        final String measurement = sensorMeasurement.getMeasurement();

        String loggedMeasurement = measurement;
        if(measurement.length() > 100) {
            loggedMeasurement = measurement.substring(0, 97);
            loggedMeasurement += "...";
        }

        log.info("Message arrived for sensor " + sensorName + " Measurement: " + loggedMeasurement);


        try {
            Sensor sensor = storageWrapper.getSensor(sensorName);

            if(sensor == null) {

                sensor = new Sensor();
                sensor.setName(sensorName);
                sensor.setObjectStorePath(sensorName);
                String dataTypeName = rawBasicApplicationProperties.getApplicationDataType();
                DataType dataType = dataTypeService.findDataTypeByName(dataTypeName);
                sensor.setDataType(dataType);
                sensor.setExportAsMetric(rawBasicApplicationProperties.getExportSensorDataAsMetric());
                storageWrapper.putSensor(sensor);
                log.info("Sensor saved");
            }

            String path = sensor.getObjectStorePath();
            if(Utils.isBlankOrNull(path)) {
                path = sensorName;
                sensor.setObjectStorePath(path);
                storageWrapper.putSensor(sensor);
                log.info("Sensor updated - ObjectStorePath set");
            }

            Measurement m = measurementFactory.create(measurement);

            processMeasurement(sensor, path, m);

        } catch (MeasurementException me) {
            log.error("Measurement was malformed. Processing aborted.");

        } catch(Exception e) {
            log.error("Unknown exception occurred.");
            //e.printStackTrace();
        }

    }
}
