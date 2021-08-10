package de.htw.saar.smartcity.aggregator.lib.broker;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * The type Activity manager.
 */
@Component
public class ActivityManager {

    /**
     * thread-safe array for saving time
     */
    private AtomicLongArray arr;

    /**
     * Instantiates a new Activity manager.
     */
    public ActivityManager() {
        long[] temp = new long[60];
        Arrays.fill(temp, 0);
        arr = new AtomicLongArray(temp);

        new Timer().scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * the reset task
     */
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            reset();
        }
    };


    /**
     * the reset function
     */
    // deprecated, no need for adding Scheduled-Annotation just for QoL
    //@Scheduled(cron="* * * * * *")
    private void reset() {
        int i = (int)(System.currentTimeMillis() / 1000) % 60;

        arr.set(i, 0);
    }

    /**
     * Add elapsed time for current time.
     *
     * @param now     the now
     * @param elapsed the elapsed
     */
    public void addTime(long now, long elapsed) {

        long i = now / 1000;
        long seconds = elapsed / 1000;
        long millis = elapsed % 1000;

        for(long s = 1; s <= seconds; s++) {
            arr.addAndGet((int) (i - s) % 60, 1000);
        }

        if(millis > 0) {
            arr.addAndGet((int) i % 60, millis);
        }
    }

    /**
     * Gets activity.
     *
     * @return the activity
     */
    public double getActivity() {

        double totalElapsed = 0;
        for(int i = 0; i < arr.length(); i++)
            totalElapsed += arr.get(i) / 1000.0;
        return totalElapsed  / 60.0;
    }
}