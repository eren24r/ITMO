package org.web4.objects;

import org.web4.musicProject.Music;

public class PrototypeBean {
    private Music music;

    public PrototypeBean(Music music) {
        this.music = music;
    }

    public void doInit(){
        System.out.println("Init");
    }

    public void doDestroy(){
        System.out.println("Destroy");
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}

