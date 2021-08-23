package de.htw.saar.smartcity.sensor.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Sensor application.
 */
@SpringBootApplication
public class SensorApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SensorApplication.class, args).close();
	}

}
