package de.htw.saar.smartcity.sensor.app.airquality;

import org.iot.raspberry.grovepi.GroveAnalogPin;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.GroveUtil;
import org.iot.raspberry.grovepi.devices.GroveAnalogInputDevice;

import java.io.IOException;

/**
 * The type Air quality input device.
 */
@GroveAnalogPin
public class AirQualityInputDevice extends GroveAnalogInputDevice<Integer> {

    /**
     * Instantiates a new Air quality input device.
     *
     * @param grovePi the grove pi
     * @param pin     the pin
     * @throws IOException the io exception
     */
    public AirQualityInputDevice(GrovePi grovePi, int pin) throws IOException {
        super(grovePi.getAnalogIn(pin, 4));
    }

    @Override
    public Integer get(byte[] data) {
        int[] v = GroveUtil.unsign(data);
        return v[2];
    }
}