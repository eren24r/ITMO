package org.main.modules.object;

public class Theologian {
    String name;
    String opinion;
    int age;

    public Theologian(String name, String opinion, int age) {
        this.name = name;
        this.opinion = opinion;
        this.age = age;
    }

    public boolean isSenior() {
        return age > 50;
    }
}