package de.htw.saar.smartcity.aggregator.lib.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * The type Setup data loader.
 */
public abstract class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * The constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(SetupDataLoader.class);

    /**
     * The Already setup.
     */
    boolean alreadySetup = false;

}