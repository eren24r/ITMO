package org.web4.objects;

import jdk.jfr.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.web4.musicProject.ClassicMusic;
import org.web4.musicProject.Music;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/*@Component("someBean")
@Scope("singleton")*/
public class BeanWithoutXmL {

    @PostConstruct
    public void doInit(){
        System.out.println("Init");
    }

    @PreDestroy
    public void doDestroy(){
        System.out.println("Destroy");
    }

    @Value("${names.name}")
    private String name;
    private Music music;

    /*@Autowired*/
    private BeanWithoutXmL(@Qualifier("classicMusic") Music music){
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }

    /*@Autowired*/
    @Qualifier("classicMusic")
    public void setMusic(Music music) {
        this.music = music;
    }

    /*@Autowired*/
    public void setClassicMusic(ClassicMusic music){
        this.music = music;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
