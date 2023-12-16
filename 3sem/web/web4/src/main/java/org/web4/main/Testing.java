package org.web4.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.web4.configes.SpringConfig;
import org.web4.objects.BeanWithoutXmL;
import org.web4.objects.PrototypeBean;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.PropertyResourceBundle;

public class Testing {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BeanWithoutXmL xml = context.getBean("someBean", BeanWithoutXmL.class);
        System.out.println(xml.getName());
        System.out.println();
        BeanWithoutXmL xml1 = context.getBean("someBean", BeanWithoutXmL.class);
        System.out.println(xml1.getName());

        AnnotationConfigApplicationContext antContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        PrototypeBean pro = antContext.getBean("prototypeBean", PrototypeBean.class);
        System.out.println(pro.getMusic().getName());

        context.close();
    }
}
