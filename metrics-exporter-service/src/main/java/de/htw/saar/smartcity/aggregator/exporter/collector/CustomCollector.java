package de.htw.saar.smartcity.aggregator.exporter.collector;


import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CustomCollector extends Collector {

    private static int gauge = 0;



    private int getNewValue() {
        int rand = new Random().nextInt(5 + 5) - 5;
        gauge = gauge + rand;
        return gauge;
    }

    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();
        // With no labels.
        mfs.add(new GaugeMetricFamily("my_gauge", "help", getNewValue()));
        // With labels
        GaugeMetricFamily labeledGauge = new GaugeMetricFamily("my_other_gauge", "help", Arrays.asList("labelname"));
        labeledGauge.addMetric(Arrays.asList("foo"), 4);
        labeledGauge.addMetric(Arrays.asList("bar"), 5);
        mfs.add(labeledGauge);
        return mfs;
    }


}
