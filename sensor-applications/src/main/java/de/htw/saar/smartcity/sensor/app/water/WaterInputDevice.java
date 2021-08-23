package de.htw.saar.smartcity.sensor.app.water;

import org.iot.raspberry.grovepi.GroveDigitalPin;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.GroveUtil;

import java.io.IOException;

import static org.iot.raspberry.grovepi.GrovePiCommands.dRead_cmd;
import static org.iot.raspberry.grovepi.GrovePiCommands.unused;

/**
 * The type Water input device.
 */
@GroveDigitalPin
public class WaterInputDevice {

    private final GrovePi grovePi;
    private final int pin;

    /**
     * Instantiates a new Water input device.
     *
     * @param grovePi the grove pi
     * @param pin     the pin
     */
    public WaterInputDevice(GrovePi grovePi, int pin) {
        this.grovePi = grovePi;
        this.pin = pin;
    }

    /**
     * Get boolean.
     *
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean get() throws IOException {
        return grovePi.exec((io) -> {
            io.write(dRead_cmd, pin, unused, unused);
            io.sleep(200);

            int[] v = GroveUtil.unsign(io.read(new byte[4]));
            return v[1] == 0;
        });
    }

}