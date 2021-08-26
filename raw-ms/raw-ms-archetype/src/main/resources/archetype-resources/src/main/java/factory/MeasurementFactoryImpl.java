package ${package}.factory;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MeasurementFactoryImpl implements MeasurementFactory {

    public Measurement<Object> create(String measurement) throws MeasurementException {

        //todo: implement logic here
        return new Measurement<Object>();

    }
}
