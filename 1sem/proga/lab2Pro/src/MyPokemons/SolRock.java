package MyPokemons;

import PoMoves.SolRockMove.ChargeBeam;
import PoMoves.SolRockMove.Confide;
import PoMoves.SolRockMove.DoubleTeam;
import PoMoves.SolRockMove.StoneEdge;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class SolRock extends Pokemon {
    public SolRock(String name, int level){
        super(name, level);

        super.setType(Type.ROCK, Type.PSYCHIC);
        super.setStats(90,95,85,55,65,70);
        DoubleTeam dt = new DoubleTeam(0, 0);
        StoneEdge se = new StoneEdge(100, 80);
        Confide c = new Confide(0,0);
        ChargeBeam cb = new ChargeBeam(50, 90);
        super.setMove(dt, se, c, cb);
    }
}