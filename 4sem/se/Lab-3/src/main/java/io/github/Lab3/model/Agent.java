package io.github.Lab3.model;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Named
@ApplicationScoped
public class Agent {

    PointCounter pointCounter = new PointCounter();
    ClickInterval clickIntervall = new ClickInterval();

    public void add(double x, double y, double r){
        this.pointCounter.addPoint(x, y, r);
    }

    public void click(long time){
        this.clickIntervall.addClick(time);
    }


    @PostConstruct
    public void initAgent() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName mBean;
        ObjectName mBeanClick;

        try {
            ObjectName pointCounterName = new ObjectName("io.github.Lab3.model:type=PointCounter");
            mbs.registerMBean(pointCounter, pointCounterName);

            ObjectName clickIntervalName = new ObjectName("io.github.Lab3.model:type=ClickInterval");
            mbs.registerMBean(clickIntervall, clickIntervalName);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logSimpleAgentStarted() {
        System.out.println("SimpleAgent.logSimpleAgentStarted");
    }

    public void startup(@Observes @Initialized(ApplicationScoped.class) Object context) {
        Agent a = new Agent();
        a.logSimpleAgentStarted();
    }
}