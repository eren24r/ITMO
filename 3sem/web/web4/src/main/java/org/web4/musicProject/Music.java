package org.web4.musicProject;

public class Music implements MusicInterface {
    private String name;
    private int volume;

    public Music(String name, int volume){
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void play(){
        System.out.println("Now playing is " + this.name + " volume: " + this.volume);
    }
}
