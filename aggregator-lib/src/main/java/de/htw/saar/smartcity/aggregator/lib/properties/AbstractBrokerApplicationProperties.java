package de.htw.saar.smartcity.aggregator.lib.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Abstract broker application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public abstract class AbstractBrokerApplicationProperties implements BrokerApplicationProperties{

    private String brokerTopic;

    private String brokerAddress;

    private String brokerHost;

    private String brokerPortMQTT;

    private String brokerPortAMQP;

    private String brokerUserName;

    private String brokerPassword;

    private String caFile;

    private String clientCertFile;

    private String clientKeyFile;


    //@Value("${BROKER_TOPIC}")
    private void setBrokerTopic(String BrokerTopic) {
        this.brokerTopic = BrokerTopic;
    }

    //@Value("${BROKER_ADDRESS}")
    private void setBrokerAddress(String BrokerAddress) {
        this.brokerAddress = BrokerAddress;
    }


    @Value("${BROKER_HOST}")
    private void setBrokerHost(String brokerHost) {
        this.brokerHost = brokerHost;
    }

    @Value("${BROKER_PORT_MQTT:#{null}}")
    private void setBrokerPortMQTT(String brokerPortMQTT) {
        this.brokerPortMQTT = brokerPortMQTT;
    }

    @Value("${BROKER_PORT_AMQP:#{null}}")
    private void setBrokerPortAMQP(String brokerPortAMQP) {
        this.brokerPortAMQP = brokerPortAMQP;
    }

    @Value("${BROKER_USERNAME}")
    private void setUserName(String BrokerUserName) {
        this.brokerUserName = BrokerUserName;
    }

    @Value("${BROKER_PASSWORD}")
    private void setPassword(String BrokerPassword) {
        this.brokerPassword = BrokerPassword;
    }

    @Value("${CA_FILE:#{null}}")
    private void setCaFile(String CaFile) {
        this.caFile = CaFile;
    }

    @Value("${CLIENT_CERT_FILE:#{null}}")
    private void setClientCertFile(String ClientCertFile) {
        this.clientCertFile = ClientCertFile;
    }

    @Value("${CLIENT_KEY_FILE:#{null}}")
    private void setClientKeyFile(String ClientKeyFile) {
        this.clientKeyFile = ClientKeyFile;
    }


    public String getBrokerHost() {
        return brokerHost;
    }

    @Override
    public String getBrokerPortMQTT() {
        return brokerPortMQTT;
    }

    @Override
    public String getBrokerPortAMQP() {
        return brokerPortAMQP;
    }

    public String getBrokerUserName() {
        return brokerUserName;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public String getCaFile() {
        return caFile;
    }

    public String getClientCertFile() {
        return clientCertFile;
    }

    public String getClientKeyFile() {
        return clientKeyFile;
    }


    /**@PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(MicroserviceApplicationProperties.class);
        log.info(this.toString());

    }**/

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ApplicationProperties{");
        sb.append("brokerTopic='").append(brokerTopic).append('\'');
        sb.append(", brokerAddress='").append(brokerAddress).append('\'');
        sb.append(", brokerHost='").append(brokerHost).append('\'');
        sb.append(", brokerPortAMQP='").append(brokerPortAMQP).append('\'');
        sb.append(", brokerPortMQTT='").append(brokerPortMQTT).append('\'');
        sb.append(", brokerUserName='").append(brokerUserName).append('\'');
        sb.append(", brokerPassword='").append(brokerPassword).append('\'');
        sb.append(", caFile='").append(caFile).append('\'');
        sb.append(", clientCertFile='").append(clientCertFile).append('\'');
        sb.append(", clientKeyFile='").append(clientKeyFile).append('\'');
        sb.append('}');
        return sb.toString();
    }
}