package de.htw.saar.smartcity.virtualization.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Agent;
import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class PictureAgent extends Agent {

    private String encodedPicture;

    /**
     * Instantiates a new Agent.
     *
     * @param publisher
     * @param sensorName the sensor name
     */
    public PictureAgent(MqttPublisher publisher, String sensorName, Integer interval) {
        super(publisher, sensorName, interval);

        try {
            File f = new ClassPathResource("/images/image1.jpeg").getFile();
            byte[] bytes = new FileInputStream(f).readAllBytes();
            encodedPicture = Base64.getEncoder().encodeToString(bytes);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    protected String getNextValue() {
        return encodedPicture;
    }
}
