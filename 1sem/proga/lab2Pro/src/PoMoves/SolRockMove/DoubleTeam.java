package PoMoves.SolRockMove;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam(double power, double acc){
        super(Type.NORMAL, power, acc);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().stat(Stat.EVASION, 1);
        p.addEffect(e);
        super.applySelfEffects(p);
    }

    protected String describe(){
        ///class.class.move
        String[] nm = this.getClass().toString().split("\\.");
        return "Status moves " + nm[nm.length-1];
    }

}
