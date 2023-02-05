package MyPokemons;

import PoMoves.BudewMove.EnergyBall;
import PoMoves.BudewMove.SwordsDance;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Budew extends Pokemon {
    public Budew(String name, int lvl){
        super(name, lvl);
        super.setType(Type.GRASS, Type.POISON);
        super.setStats(40,30,35,50,70,55);

        EnergyBall eb = new EnergyBall(90, 100);
        SwordsDance sd = new SwordsDance(0,0);
        super.setMove(eb, sd);
    }
}
