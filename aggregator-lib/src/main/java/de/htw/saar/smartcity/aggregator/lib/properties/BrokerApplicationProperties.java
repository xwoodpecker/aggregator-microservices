package de.htw.saar.smartcity.aggregator.lib.properties;

/**
 * The interface Broker application properties.
 */
public interface BrokerApplicationProperties {

    /**
     * Gets broker host.
     *
     * @return the broker host
     */
    String getBrokerHost();

    /**
     * Gets broker port mqtt.
     *
     * @return the broker port mqtt
     */
    String getBrokerPortMQTT();

    /**
     * Gets broker port amqp.
     *
     * @return the broker port amqp
     */
    String getBrokerPortAMQP();

    /**
     * Gets broker user name.
     *
     * @return the broker user name
     */
    String getBrokerUserName();

    /**
     * Gets broker password.
     *
     * @return the broker password
     */
    String getBrokerPassword();

    /**
     * Gets ca file.
     *
     * @return the ca file
     */
    String getCaFile();

    /**
     * Gets client cert file.
     *
     * @return the client cert file
     */
    String getClientCertFile();

    /**
     * Gets client key file.
     *
     * @return the client key file
     */
    String getClientKeyFile();
}
