package de.htw.saar.smartcity.aggregator.lib.properties;

public interface BrokerApplicationProperties {

    String getBrokerHost();

    String getBrokerPortMQTT();

    String getBrokerPortAMQP();

    String getBrokerUserName();

    String getBrokerPassword();

    String getCaFile();

    String getClientCertFile();

    String getClientKeyFile();
}
