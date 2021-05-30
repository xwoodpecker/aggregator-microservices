package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.broker.BrokerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public abstract class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    protected static final Logger log = LoggerFactory.getLogger(SetupDataLoader.class);

    boolean alreadySetup = false;

}