package lab;
import MyPokemons.*;
import ru.ifmo.se.pokemon.*;

public class LabPro{
    public static void main(String[] args) {
        Battle b = new Battle();
        SolRock sr = new SolRock("Pok1", 5);
        Mantyke mt = new Mantyke("Pok2", 5);
        Roselia rs = new Roselia("Pok3", 5);
        Mantine mtin = new Mantine("Pok4", 5);
        Roserade rr = new Roserade("Pok5", 5);
        Budew bb = new Budew("Pok6", 5);
        b.addFoe(sr);
        b.addFoe(mt);
        b.addFoe(rs);
        b.addAlly(mtin);
        b.addAlly(rr);
        b.addAlly(bb);
        b.go();
    }
    public static boolean chance(double d) {
        return d >= Math.random();
    }
}