package MyPokemons;

import PoMoves.MantineMove.MantineAquaRing;
import PoMoves.MantineMove.MantineBlizzard;
import PoMoves.MantineMove.MantineWaterPulse;
import PoMoves.MantineMove.RockTomb;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Mantine extends Pokemon {
     public Mantine(String name, int lvl){
         super(name, lvl);
         super.setType(Type.WATER, Type.FLYING);
         super.setStats(85,40,70,80,140,70);
         MantineAquaRing mar = new MantineAquaRing(0, 0);
         MantineWaterPulse mwp = new MantineWaterPulse(60, 100);
         MantineBlizzard mb = new MantineBlizzard(110, 70);
         RockTomb rt = new RockTomb(60,95);
         super.setMove(mar, mwp, mb, rt);
     }
}
