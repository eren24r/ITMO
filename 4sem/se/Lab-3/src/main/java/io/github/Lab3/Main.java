package io.github.Lab3;

import io.github.Lab3.model.ClickInterval;
import io.github.Lab3.model.PointCounter;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class Main {
    public static void main(String[] args) throws Exception {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        String osName = osBean.getName();
        String osVersion = osBean.getVersion();

        System.out.println("Operating System Name: " + osName);
        System.out.println("Operating System Version: " + osVersion);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        PointCounter pointCounter = new PointCounter();
        ObjectName pointCounterName = new ObjectName("io.github.Lab3.model:type=PointCounter");
        mbs.registerMBean(pointCounter, pointCounterName);

        ClickInterval clickInterval = new ClickInterval();
        ObjectName clickIntervalName = new ObjectName("io.github.Lab3.model:type=ClickInterval");
        mbs.registerMBean(clickInterval, clickIntervalName);

        System.out.println("MBeans registered. Press Enter to exit...");
        System.in.read();
    }
}
