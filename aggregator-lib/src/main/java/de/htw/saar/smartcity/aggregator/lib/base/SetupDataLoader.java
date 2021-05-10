package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.Microservice;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final MicroserviceService microserviceService;

    private final MicroserviceApplicationProperties microserviceApplicationProperties;

    public SetupDataLoader(MicroserviceService microserviceService, MicroserviceApplicationProperties microserviceApplicationProperties) {

        this.microserviceService = microserviceService;
        this.microserviceApplicationProperties = microserviceApplicationProperties;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup)
            return;

        createMicroserviceIfNotFound(microserviceApplicationProperties.getMicroserviceName(), microserviceApplicationProperties.getMicroserviceURL());

        alreadySetup = true;
    }


    @Transactional
    Microservice createMicroserviceIfNotFound(String microserviceName, String microServiceURL) {

        Microservice microservice = microserviceService.findMicroserviceByName(microserviceName);
        if(microservice == null) {
            microservice = new Microservice();
            microservice.setName(microserviceName);
            microservice.setUrl(microServiceURL);
            microserviceService.saveMicroservice(microservice);
        }
        return microservice;
    }
}