package Obj;

import Classes.Stock;
import Enums.StockType;

public class ArbuzCorp extends Stock {
    public ArbuzCorp() {
        super("ArbuzCorp Inc.", 150.4);
        this.setType(StockType.BIG);
    }
    @Override
    public void info() {
        this.describe("I am a Arbuz! I am a biggest Corp!");
    }
}
