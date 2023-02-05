package PoMoves.RoseliaMove;

import lab.LabPro;
import ru.ifmo.se.pokemon.*;

public class EnergyBall extends SpecialMove {
    public EnergyBall(double power, int acc){
        super(Type.GRASS, power, acc);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        if(LabPro.chance(1.0)){
            Effect e = new Effect().stat(Stat.SPECIAL_DEFENSE, -1);
            pokemon.addEffect(e);
        }
        super.applyOppDamage(pokemon, v);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Special Move: " + nm[nm.length - 1];
    }
}
