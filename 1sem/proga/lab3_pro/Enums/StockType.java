package Enums;

public enum StockType {
    BIG("большой"),
    BABY("Малой"),
    NORMAL("Норм!");

    String translate;

    StockType(String translate) {
        this.translate = translate;
    }
}
