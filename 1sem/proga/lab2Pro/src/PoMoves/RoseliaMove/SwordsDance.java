package PoMoves.RoseliaMove;

import ru.ifmo.se.pokemon.*;

public class SwordsDance extends StatusMove {
    public SwordsDance(double power, int acc){
        super(Type.NORMAL, power, acc);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect e = new Effect().stat(Stat.ATTACK, 2);
        super.applySelfEffects(pokemon);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Status Move: " + nm[nm.length - 1];
    }
}
