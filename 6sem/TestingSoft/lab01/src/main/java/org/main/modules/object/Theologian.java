package org.main.modules.object;

public class Theologian {
    String name;
    String opinion;
    int age;
    boolean fake;

    public Theologian(String name, String opinion, int age) {
        this.name = name;
        this.opinion = opinion;
        this.age = age;
        this.fake = false;
    }

    public boolean isSenior() {
        if(age >= 50) {
            System.out.printf("This Theologian is a senior: " + this.name);
        }
        return age > 50;
    }

    public boolean isFake(){
        if(fake == true) {
            System.out.printf("This Theologian is a fake object: " + this.name);
        }
        return this.fake;
    }
}