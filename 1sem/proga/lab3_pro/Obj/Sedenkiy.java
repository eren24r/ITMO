package Obj;

import Classes.Client;

public class Sedenkiy extends Client {
    public Sedenkiy() {
        super("Sedenkiy", 100000);
    }

    @Override
    public void info() {
        this.describe("Sedenkiy богатый! Ему подарил Миг!");
    }

}
