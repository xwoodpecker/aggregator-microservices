package de.htw.saar.smartcity.aggregator.lib.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public abstract class MicroserviceController {

    private final ApplicationProperties applicationProperties;

    protected MicroserviceController(ApplicationProperties applicationProperties) {

        this.applicationProperties = applicationProperties;
    }

    @ResponseBody
    @RequestMapping(value="/metrics", produces="text/plain")
    public String metrics() {
        Integer totalMessages = getQueueMessageCount();
        return "# HELP messages Number of messages in queue\n"
                + "# TYPE messages gauge\n"
                + "messages " + totalMessages;
    }

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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return 0;
    }
}
