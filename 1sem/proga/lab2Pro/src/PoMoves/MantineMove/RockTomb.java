package PoMoves.MantineMove;

import ru.ifmo.se.pokemon.*;

public class RockTomb extends PhysicalMove {
    public RockTomb(double power, int acc){
        super(Type.ROCK, power, acc);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        Effect e = new Effect().stat(Stat.SPEED, -1);
        pokemon.addEffect(e);
        super.applyOppDamage(pokemon, v);
    }

    protected String describe(){
        //PoMoves.namePok.Move
        String[] nm = this.getClass().toString().split("\\.");
        return "Physical Move: " + nm[nm.length - 1];
    }
}
