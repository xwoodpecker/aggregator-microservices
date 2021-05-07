package de.htw.saar.smartcity.aggregator.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:application.properties")
public abstract class ApplicationProperties  {

    private String BrokerTopic;

    private String BrokerAddress;

    private String BrokerHost;

    private String BrokerPort;

    private String BrokerUserName;

    private String BrokerPassword;

    private String CaFile;

    private String ClientCertFile;

    private String ClientKeyFile;

    private String MinioEndpoint;

    private String MinioAccessKey;

    private String MinioSecretKey;


    //@Value("${BROKER_TOPIC}")
    private void setBrokerTopic(String BrokerTopic) {
        this.BrokerTopic = BrokerTopic;
    }

    //@Value("${BROKER_ADDRESS}")
    private void setBrokerAddress(String BrokerAddress) {
        this.BrokerAddress = BrokerAddress;
    }


    @Value("${BROKER_HOST}")
    private void setBrokerHost(String brokerHost) {
        BrokerHost = brokerHost;
    }

    @Value("${BROKER_PORT}")
    private void setBrokerPort(String brokerPort) {
        BrokerPort = brokerPort;
    }

    @Value("${BROKER_USERNAME}")
    private void setUserName(String BrokerUserName) {
        this.BrokerUserName = BrokerUserName;
    }

    @Value("${BROKER_PASSWORD}")
    private void setPassword(String BrokerPassword) {
        this.BrokerPassword = BrokerPassword;
    }

    @Value("${CA_FILE}")
    private void setCaFile(String CaFile) {
        this.CaFile = CaFile;
    }

    @Value("${CLIENT_CERT_FILE}")
    private void setClientCertFile(String ClientCertFile) {
        this.ClientCertFile = ClientCertFile;
    }

    @Value("${CLIENT_KEY_FILE}")
    private void setClientKeyFile(String ClientKeyFile) {
        this.ClientKeyFile = ClientKeyFile;
    }

    @Value("${MINIO_ENDPOINT}")
    private void setMinioEndpoint(String minioEndpoint) {
        MinioEndpoint = minioEndpoint;
    }

    @Value("${MINIO_ACCESSKEY}")
    private void setMinioAccessKey(String minioAccessKey) {
        MinioAccessKey = minioAccessKey;
    }

    @Value("${MINIO_SECRETKEY}")
    private void setMinioSecretKey(String minioSecretKey) {
        MinioSecretKey = minioSecretKey;
    }

    /**
    public String getBrokerTopic() {
        return BrokerTopic;
    }

    public String getBrokerAddress() {
        return BrokerAddress;
    }
    **/

    public String getBrokerHost() {
        return BrokerHost;
    }

    public String getBrokerPort() {
        return BrokerPort;
    }

    public String getBrokerUserName() {
        return BrokerUserName;
    }

    public String getBrokerPassword() {
        return BrokerPassword;
    }

    public String getCaFile() {
        return CaFile;
    }

    public String getClientCertFile() {
        return ClientCertFile;
    }

    public String getClientKeyFile() {
        return ClientKeyFile;
    }

    public String getMinioEndpoint() {
        return MinioEndpoint;
    }

    public String getMinioAccessKey() {
        return MinioAccessKey;
    }

    public String getMinioSecretKey() {
        return MinioSecretKey;
    }


    @PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);
        log.info(this.toString());

    }

    @Override
    public String toString()
    {
        return new StringBuilder()
                .append("BrokerTopic=" + BrokerTopic)
                .append("\tBrokerAddress=" + BrokerAddress)
                .append("\tBrokerHost=" + BrokerHost)
                .append("\tBrokerPort=" + BrokerPort)
                .append("\tBrokerUserName=" + BrokerUserName)
                .append("\tBrokerPassword=" + BrokerPassword)
                .append("\tCaFile=" + CaFile)
                .append("\tClientCertFile=" + ClientCertFile)
                .append("\tClientKeyFile=" + ClientKeyFile)
                .append("\tMinioEndpoint=" + MinioEndpoint)
                .append("\tMinioAccessKey=" + MinioAccessKey)
                .append("\tMinioSecretKey=" + MinioSecretKey)
                .toString();
    }
}
