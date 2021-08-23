package de.htw.saar.smartcity.sensor.app.base;

import de.htw.saar.smartcity.sensor.app.broker.MqttPublisherImpl;
import org.iot.raspberry.grovepi.GrovePi;

/**
 * The interface Sensor agent.
 */
public interface SensorAgent {

  /**
   * Run.
   *
   * @param grovePi   the grove pi
   * @param monitor   the monitor
   * @param publisher the publisher
   * @throws Exception the exception
   */
  void run(GrovePi grovePi, Monitor monitor, MqttPublisherImpl publisher) throws Exception;

}
