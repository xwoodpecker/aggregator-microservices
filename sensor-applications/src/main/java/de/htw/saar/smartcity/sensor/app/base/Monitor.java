package de.htw.saar.smartcity.sensor.app.base;

import java.util.concurrent.Semaphore;

/**
 * The type Monitor used for running the application with semaphore
 */
public class Monitor {
  
  private Semaphore semaphore = new Semaphore(0);
  private boolean running = true;

    /**
     * Wait for stop.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void waitForStop() throws InterruptedException {
    semaphore.acquire();
    semaphore.release();
  }

    /**
     * Stop.
     */
    public void stop() {
    running = false;
    semaphore.release();
  }

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
    return running;
  }
}
