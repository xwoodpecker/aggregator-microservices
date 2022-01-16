package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.HashMap;
import java.util.Map;

public enum TimeInterval {

    Hour("HOUR", 1000*60*60L),
    Day("DAY", 1000*60*60*24L);

    public final String label;
    public final long interval;

    private TimeInterval(String label, long interval) {
        this.label = label;
        this.interval = interval;
    }

    private static final Map<String, TimeInterval> BY_LABEL = new HashMap<>();
    private static final Map<Long, TimeInterval> BY_INTERVAL = new HashMap<>();

    static {
        for (TimeInterval ti : values()) {
            BY_LABEL.put(ti.label, ti);
            BY_INTERVAL.put(ti.interval, ti);
        }
    }
    public static TimeInterval valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static TimeInterval valueOfInterval(long interval) {
        return BY_INTERVAL.get(interval);
    }
}
