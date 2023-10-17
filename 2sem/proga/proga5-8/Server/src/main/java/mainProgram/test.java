package mainProgram;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;


public class test {
    public static void main(String[] args) {
        Stream.of("helium", "neon", "argon", "krypton", "xenon", "radon")
                .filter(s -> s.length() != 6)
                .map(s -> s)
                .skip(2)
                .sorted()
                .forEachOrdered(System.out::print);
    }
}
