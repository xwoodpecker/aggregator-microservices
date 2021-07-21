package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class Publisher extends BrokerConnection {

    public Publisher(BrokerApplicationProperties applicationProperties) throws Exception {

        super(applicationProperties);

        channel.exchangeDeclare(Constants.GROUP_EXCHANGE, "topic");
    }

    public void publish (String routingKey, String message)  {
        try {
            channel.basicPublish(Constants.GROUP_EXCHANGE, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error during publish on group_exchange");
            //e.printStackTrace();
        }
    }

}
