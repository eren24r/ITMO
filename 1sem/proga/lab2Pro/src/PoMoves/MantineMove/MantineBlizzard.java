package PoMoves.MantineMove;

import lab.LabPro;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class MantineBlizzard extends SpecialMove {
    public MantineBlizzard(double power, int acc){
        super(Type.ICE, power, acc);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
        if(LabPro.chance(1.0)){
            Effect.freeze(pokemon);
        }
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Special Move: " + nm[nm.length - 1];
    }
}
