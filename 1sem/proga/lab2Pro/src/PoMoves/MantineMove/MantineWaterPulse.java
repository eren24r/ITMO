package PoMoves.MantineMove;

import lab.LabPro;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class MantineWaterPulse extends SpecialMove {
    public MantineWaterPulse(double power, int acc){
        super(Type.WATER, power, acc);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
        if(LabPro.chance(0.2)){
            Effect.flinch(pokemon);
        }
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Special Move: " + nm[nm.length - 1];
    }
}
