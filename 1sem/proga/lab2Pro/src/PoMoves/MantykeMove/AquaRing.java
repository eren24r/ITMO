package PoMoves.MantykeMove;

import ru.ifmo.se.pokemon.*;

public class AquaRing extends StatusMove {
    public AquaRing(double power, double acc){
        super(Type.WATER, power, acc);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect.burn(pokemon);
        super.applySelfEffects(pokemon);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Status Move: " + nm[nm.length - 1];
    }
}
