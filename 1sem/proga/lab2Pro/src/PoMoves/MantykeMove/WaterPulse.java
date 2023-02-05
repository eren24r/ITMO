package PoMoves.MantykeMove;

import lab.LabPro;
import ru.ifmo.se.pokemon.*;

public class WaterPulse extends SpecialMove {
    public WaterPulse(double power, int acc){
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
