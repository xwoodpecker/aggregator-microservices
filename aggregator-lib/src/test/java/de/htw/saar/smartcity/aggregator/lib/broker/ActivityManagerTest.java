package de.htw.saar.smartcity.aggregator.lib.broker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager.class)
public class ActivityManagerTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActivityManagerTest.class);

    @Autowired
    private ActivityManager activityManager;

    @Test
    public void testEverySecond() throws InterruptedException {
        for(int i = 0; i < 62; i++) {
            Thread.sleep(999L);
            long now = System.currentTimeMillis();
            activityManager.addTime(now, 999L);
        }

        Double activity = activityManager.getActivity();
        Double minimum = (999*58.0)/(1000*60.0);
        log.info("real: " + activity + " min: " + minimum);
        assertThat(activity, greaterThan(minimum));
    }

    @Test
    public void testEveryTwoSeconds() throws InterruptedException {
        for(int i = 0; i < 31; i++) {
            Thread.sleep(1999L);
            long now = System.currentTimeMillis();
            activityManager.addTime(now, 1999L);
        }

        Double activity = activityManager.getActivity();
        Double minimum = (999*58.0)/(1000*60.0);
        log.info("real: " + activity + " min: " + minimum);
        assertThat(activity, greaterThan(minimum));
    }


    @Test
    public void testFiftyPercent() throws InterruptedException {
        for(int i = 0; i < 62; i++) {
            Thread.sleep(999L);
            long now = System.currentTimeMillis();
            activityManager.addTime(now, 499L);
        }

        Double activity = activityManager.getActivity();
        Double minimum = (499*58.0)/(1000*60.0);
        log.info("real: " + activity + " min: " + minimum);
        assertThat(activity, greaterThan(minimum));
    }
}
