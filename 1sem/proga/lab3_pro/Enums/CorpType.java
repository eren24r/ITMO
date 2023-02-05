package Enums;

public enum CorpType {
    BIG("большой"),
    BABY("Малой"),
    NORMAL("Норм!");

    String translate;

    CorpType(String translate) {
        this.translate = translate;
    }
}
