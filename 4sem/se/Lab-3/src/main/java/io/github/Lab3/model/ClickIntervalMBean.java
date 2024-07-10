package io.github.Lab3.model;

public interface ClickIntervalMBean {
    void addClick(long timestamp);
    double getAverageInterval();
}
