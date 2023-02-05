package PoMoves.RoseliaMove;

import lab.LabPro;
import ru.ifmo.se.pokemon.*;

public class PinMissile extends PhysicalMove {
    public PinMissile(double power, int acc){
        super(Type.BUG, power, acc);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        if(LabPro.chance((double) (3/8))){
            Effect e = new Effect().turns(2);
            pokemon.addEffect(e);
        }
        super.applySelfEffects(pokemon);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Physical Move: " + nm[nm.length - 1];
    }
}
