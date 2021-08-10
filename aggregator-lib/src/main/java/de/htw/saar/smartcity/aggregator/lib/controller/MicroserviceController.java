package de.htw.saar.smartcity.aggregator.lib.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * The type Microservice controller.
 */
@Deprecated
public abstract class MicroserviceController {

    private static final Logger log = LoggerFactory.getLogger(MicroserviceController.class);

    private final MicroserviceApplicationProperties applicationProperties;

    /**
     * Instantiates a new Microservice controller.
     *
     * @param applicationProperties the application properties
     */
    protected MicroserviceController(MicroserviceApplicationProperties applicationProperties) {

        this.applicationProperties = applicationProperties;
    }

    /**
     * Metrics string.
     *
     * @return the string
     */
    @ResponseBody
    @RequestMapping(value="/metrics", produces="text/plain")
    public String metrics() {
        Integer totalMessages = getQueueMessageCount();
        return "# HELP microservice_queue_message_count Number of messages in queue\n"
                + "# TYPE microservice_queue_message_count gauge\n"
                + "microservice_queue_message_count " + totalMessages;
    }

    /**
     * get the number of messages in the dedicated queue over a rest call to the rabbitmq broker
     *
     * @return count of messages in the queue
     */
    private Integer getQueueMessageCount() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://%s:%s/",
                applicationProperties.getBrokerHost(),
                applicationProperties.getBrokerManagementPort())
                + "api/queues/%2F/"
                + applicationProperties.getMicroserviceQueue();

        String credentials = applicationProperties.getBrokerUserName() + ":" + applicationProperties.getBrokerPassword();
        String base64String = null;
        try {
            base64String = Base64.encodeBase64String(credentials.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            //should never occur!
            //e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + base64String);

        RequestEntity<String> requestEntity = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(url)
        );
        ResponseEntity<String> response
                = restTemplate.exchange(requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            JsonNode messageNode = jsonNode.findValue("messages");
            Integer messageCount = messageNode.asInt();
            return messageCount;

        } catch (JsonProcessingException e) {
            log.error("JSON could not be processed. Malformed!");
            //e.printStackTrace();
        }

        return 0;
    }
}
