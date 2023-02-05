package MyPokemons;

import PoMoves.RoseradeMove.EnergyBall;
import PoMoves.RoseradeMove.PinMissile;
import PoMoves.RoseradeMove.SwordsDance;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Roserade extends Pokemon {
    public Roserade(String name, int lvl){
        super(name, lvl);
        super.setType(Type.GRASS, Type.POISON);
        super.setStats(60,70, 65, 125, 105, 90);
        EnergyBall eb = new EnergyBall(90, 100);
        SwordsDance sd = new SwordsDance(0, 0);
        PinMissile pm = new PinMissile(25, 95);
        super.setMove(eb, sd, pm);
    }
}
