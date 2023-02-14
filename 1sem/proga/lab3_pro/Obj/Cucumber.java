package Obj;

import Classes.Stock;

public class Cucumber extends Stock {
    public Cucumber(String name) {
        super(name, 120);
    }

    @Override
    public void info() {
        this.describe("I am a Cucumber! I biggest than Korotishek!");
    }
}
