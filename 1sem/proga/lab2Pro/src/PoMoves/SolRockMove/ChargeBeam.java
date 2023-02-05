package PoMoves.SolRockMove;

import lab.LabPro;
import ru.ifmo.se.pokemon.*;

public class ChargeBeam extends SpecialMove {
    public ChargeBeam(double power, double acc){
        super(Type.ELECTRIC, power, acc);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }
    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        if(LabPro.chance(0.7)){
            Effect e = new Effect().stat(Stat.SPECIAL_ATTACK, 1);
            pokemon.addEffect(e);
        }
        super.applySelfEffects(pokemon);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Special Move: " + nm[nm.length - 1];
    }
}
