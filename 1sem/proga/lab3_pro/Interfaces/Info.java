package Interfaces;

public interface Info {
    default void describe(String txt){
        System.out.println(txt);
    };
    void info();
}
