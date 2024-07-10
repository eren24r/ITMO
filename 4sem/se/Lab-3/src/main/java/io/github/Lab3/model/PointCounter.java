package io.github.Lab3.model;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.NamedStoredProcedureQueries;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;

@Named
@ApplicationScoped
public class PointCounter extends NotificationBroadcasterSupport implements PointCounterMBean, Serializable {
    private static final long serialVersionUID = 1L;
     int totalPoints = 0;
     int pointsOutside = 0;
     long sequenceNumber = 0;

    @Override
    public void addPoint(double x, double y, double r) {
        totalPoints++;
        if (!AreaResultChecker.getResult(x, y, r)) {
            pointsOutside++;
        }
        if (totalPoints % 15 == 0) {
            try {
                String message = "Total points is now a multiple of 15: " + totalPoints;
                Notification notification = new Notification(
                        "PointCounter.totalPoints.multipleOf15",
                        this,
                        sequenceNumber++,
                        System.currentTimeMillis(),
                        message
                );
                sendNotification(notification);
                System.out.println("notification send!");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getPointsOutside() {
        return pointsOutside;
    }
}
