package MyPokemons;

import PoMoves.MantykeMove.AquaRing;
import PoMoves.MantykeMove.Blizzard;
import PoMoves.MantykeMove.WaterPulse;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Mantyke extends Pokemon {
    public  Mantyke(String name, int lvl){
        super(name, lvl);

        super.setType(Type.WATER, Type.FLYING);
        super.setStats(45,20,50,60,120,50);
        AquaRing ar = new AquaRing(0, 0);
        WaterPulse wp = new WaterPulse(60, 100);
        Blizzard b = new Blizzard(110, 70);
        super.setMove(ar, wp, b);
    }
}
