package Obj;

import Classes.Korotishka;
import Interfaces.Info;

public class Sedenkiy extends Korotishka {
    public Sedenkiy() {
        super("Sedenkiy", 100000);
    }

    @Override
    public void info() {
        this.describe("Sedenkiy богатый! Ему подарил Миг!");
    }

}
