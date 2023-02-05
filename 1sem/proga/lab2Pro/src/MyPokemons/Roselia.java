package MyPokemons;

import PoMoves.RoseliaMove.EnergyBall;
import PoMoves.RoseliaMove.PinMissile;
import PoMoves.RoseliaMove.SwordsDance;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Roselia extends Pokemon {
    public Roselia(String name, int lvl){
        super(name,lvl);
        super.setType(Type.GRASS, Type.POISON);
        super.setStats(50,60,45,100,80,65);
        EnergyBall eb = new EnergyBall(90, 100);
        SwordsDance sd = new SwordsDance(0,0);
        PinMissile pm = new PinMissile(25, 95);
        super.setMove(eb, sd, pm);
    }
}
