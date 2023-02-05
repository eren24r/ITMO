package PoMoves.SolRockMove;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide(double power, double acc){
        super(Type.NORMAL, power, acc);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect e = new Effect().stat(Stat.SPECIAL_ATTACK, -1);
        pokemon.addEffect(e);
        super.applyOppEffects(pokemon);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Status Move: " + nm[nm.length - 1];
    }
}
