package io.github.Lab3.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

@Named
@ApplicationScoped
public class ClickInterval implements ClickIntervalMBean, Serializable {
    private static final long serialVersionUID = 1L;
    private final Queue<Long> clickTimes = new LinkedList<>();
    private final int maxClicks = 10;  // We'll keep track of the last 10 clicks

    @Override
    public void addClick(long timestamp) {
        if (clickTimes.size() == maxClicks) {
            clickTimes.poll();
        }
        clickTimes.add(timestamp);
    }

    @Override
    public double getAverageInterval() {
        if (clickTimes.size() < 2) {
            return 0.0;
        }

        Long[] times = clickTimes.toArray(new Long[0]);
        long totalInterval = 0;
        for (int i = 1; i < times.length; i++) {
            totalInterval += times[i] - times[i - 1];
        }
        return Math.abs(totalInterval / (double) (times.length - 1));
    }
}
