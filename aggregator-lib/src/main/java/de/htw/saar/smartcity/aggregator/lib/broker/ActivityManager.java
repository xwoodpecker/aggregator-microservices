package de.htw.saar.smartcity.aggregator.lib.broker;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLongArray;

@Component
public class ActivityManager {

    private AtomicLongArray arr;

    public ActivityManager() {
        long[] temp = new long[60];
        Arrays.fill(temp, 0);
        arr = new AtomicLongArray(temp);

        new Timer().scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            reset();
        }
    };


    //@Scheduled(cron="* * * * * *")
    private void reset() {
        int i = (int)(System.currentTimeMillis() / 1000) % 60;

        //System.out.println("Reset " + i);
        arr.set(i, 0);
    }

    public void addTime(long now, long elapsed) {

        long i = now / 1000;
        long seconds = elapsed / 1000;
        long millis = elapsed % 1000;

        for(long s = 1; s <= seconds; s++) {
            //System.out.println("Set " + (i - s) % 60 + " : " + 1000);
            arr.addAndGet((int) (i - s) % 60, 1000);
        }

        if(millis > 0) {
            //System.out.println("Set " + i % 60 + " : " + millis);
            arr.addAndGet((int) i % 60, millis);
        }
    }

    public double getActivity() {

        double totalElapsed = 0;
        for(int i = 0; i < arr.length(); i++)
            totalElapsed += arr.get(i) / 1000.0;
        return totalElapsed  / 60.0;
    }
}