package io.github.Lab3.model;

public interface PointCounterMBean {
    void addPoint(double x, double y, double r);
    int getTotalPoints();
    int getPointsOutside();
}
