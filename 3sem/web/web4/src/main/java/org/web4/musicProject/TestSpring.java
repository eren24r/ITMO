package org.web4.musicProject;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        MusicPlayer music = context.getBean("musicPlayer", MusicPlayer.class);

        music.playMusics();

    }
}
