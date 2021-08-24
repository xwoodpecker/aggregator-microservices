package de.htw.saar.smartcity.aggregator.lib.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * The type Microservice application properties.
 */
public abstract class MicroserviceApplicationProperties implements BrokerApplicationProperties, MinioApplicationProperties, MemcachedApplicationProperties {

    private String brokerTopic;

    private String brokerAddress;

    private String brokerHost;

    private String brokerPortAMQP;

    private String brokerPortMQTT;

    private String brokerManagementPort;

    private String brokerUserName;

    private String brokerPassword;

    private String caFile;

    private String clientCertFile;

    private String clientKeyFile;

    private String minioEndpoint;

    private String minioAccessKey;

    private String minioSecretKey;

    private Boolean onlySaveMeasurementValue;

    /**
     * The Microservice queue.
     */
    protected String microserviceQueue;

    /**
     * The Microservice bucket.
     */
    protected String microserviceBucket;

    /**
     * The Memcached host.
     */
    protected String memcachedHost;

    /**
     * The Memcached port.
     */
    protected String memcachedPort;

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

    @Value("${BROKER_MANAGEMENT_PORT:#{null}}")
    private void setBrokerManagementPort(String brokerManagementPort) {
        this.brokerManagementPort = brokerManagementPort;
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

    @Value("${MINIO_ENDPOINT}")
    private void setMinioEndpoint(String minioEndpoint) {
        this.minioEndpoint = minioEndpoint;
    }

    @Value("${MINIO_ACCESSKEY}")
    private void setMinioAccessKey(String minioAccessKey) {
        this.minioAccessKey = minioAccessKey;
    }

    @Value("${MINIO_SECRETKEY}")
    private void setMinioSecretKey(String minioSecretKey) {
        this.minioSecretKey = minioSecretKey;
    }

    /**
     * Sets only save measurement value.
     *
     * @param onlySaveMeasurementValue the only save measurement value
     */
    @Value("${ONLY_SAVE_MEASUREMENT_VALUE}")
    public void setOnlySaveMeasurementValue(Boolean onlySaveMeasurementValue) {
        this.onlySaveMeasurementValue = onlySaveMeasurementValue;
    }

    /**
     * Sets microservice queue.
     *
     * @param microserviceQueue the microservice queue
     */
    protected abstract void setMicroserviceQueue(String microserviceQueue);

    /**
     * Sets microservice bucket.
     *
     * @param microserviceBucket the microservice bucket
     */
    protected abstract void setMicroserviceBucket(String microserviceBucket);

    @Value("${MEMCACHED_HOST}")
    private void setMemcachedHost(String memcachedHost) {
        this.memcachedHost = memcachedHost;
    }

    @Value("${MEMCACHED_PORT}")
    private void setMemcachedPort(String memcachedPort) {
        this.memcachedPort = memcachedPort;
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
        return brokerHost;
    }

    public String getBrokerPortAMQP() {
        return brokerPortAMQP;
    }

    public String getBrokerPortMQTT() {
        return brokerPortMQTT;
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

    public String getMinioEndpoint() {
        return minioEndpoint;
    }

    public String getMinioAccessKey() {
        return minioAccessKey;
    }

    public String getMinioSecretKey() {
        return minioSecretKey;
    }

    /**
     * Gets only save measurement value.
     *
     * @return the only save measurement value
     */
    public Boolean getOnlySaveMeasurementValue() {
        return onlySaveMeasurementValue;
    }

    /**
     * Gets microservice queue.
     *
     * @return the microservice queue
     */
    public String getMicroserviceQueue() {
        return microserviceQueue;
    }

    public String getMicroserviceBucket() {
        return this.microserviceBucket;
    }


    public String getMemcachedHost() {
        return this.memcachedHost;
    }

    public String getMemcachedPort() {
        return this.memcachedPort;
    }

    /**
     * Gets broker management port.
     *
     * @return the broker management port
     */
    public String getBrokerManagementPort() {
        return this.brokerManagementPort;
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
        sb.append(", brokerManagementPort='").append(brokerManagementPort).append('\'');
        sb.append(", brokerUserName='").append(brokerUserName).append('\'');
        sb.append(", brokerPassword='").append(brokerPassword).append('\'');
        sb.append(", caFile='").append(caFile).append('\'');
        sb.append(", clientCertFile='").append(clientCertFile).append('\'');
        sb.append(", clientKeyFile='").append(clientKeyFile).append('\'');
        sb.append(", minioEndpoint='").append(minioEndpoint).append('\'');
        sb.append(", minioAccessKey='").append(minioAccessKey).append('\'');
        sb.append(", minioSecretKey='").append(minioSecretKey).append('\'');
        sb.append(", microserviceQueue='").append(microserviceQueue).append('\'');
        sb.append(", microserviceBucket='").append(microserviceBucket).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
