package Obj;

import Classes.Corp;
import Enums.CorpType;

public class ArbuzCorp extends Corp {
    public ArbuzCorp() {
        super("ArbuzCorp Inc.", 150.4);
        this.setType(CorpType.BIG);
    }
    @Override
    public void info() {
        this.describe("I am a Arbuz! I am a biggest Corp!");
    }
}
