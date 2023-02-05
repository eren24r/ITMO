package Obj;

import Classes.Corp;

public class Cucumber extends Corp {
    public Cucumber(String name) {
        super(name, 120);
    }

    @Override
    public void info() {
        this.describe("I am a Cucumber! I biggest than Korotishek!");
    }
}
