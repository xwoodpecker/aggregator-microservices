package de.htw.saar.smartcity.aggregator.lib.broker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;

@Component
public class ActivityManager {

    private AtomicLongArray arr;
    //private AtomicInteger lastIndex = new AtomicInteger();

    public ActivityManager() {
        long[] temp = new long[60];
        Arrays.fill(temp, 0);
        arr = new AtomicLongArray(temp);
    }


    @Scheduled(cron="* * * * * *")
    private void reset() {
        int i = (int)(System.currentTimeMillis() / 1000) % 60;
        //System.out.println("Hello Reset i " + i + "- Time: " + LocalDateTime.now());
        arr.set(i, 0);
    }

    public void addTime(long now, long elapsed) {

        int i = (int)(now / 1000) % 60;
        //System.out.println("Hello Reset i " + i + "- Time: " + LocalDateTime.now());

        arr.addAndGet(i, elapsed);
        //lastIndex.set(i);
    }

    public double getActivity() {

        double totalElapsed = 0;
        for(int i = 0; i < arr.length(); i++)
            totalElapsed += arr.get(i) / 1000.0;
        return totalElapsed  / 60.0;
    }
}