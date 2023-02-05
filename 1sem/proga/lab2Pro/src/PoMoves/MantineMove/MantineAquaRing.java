package PoMoves.MantineMove;

import ru.ifmo.se.pokemon.*;

public class MantineAquaRing extends StatusMove {
    public MantineAquaRing(double power, double acc){
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
