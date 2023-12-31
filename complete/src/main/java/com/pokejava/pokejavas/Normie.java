package com.pokejava.pokejavas;

import com.pokejava.*;

public class Normie extends PokeJava {
    
    // Normie n1 = new Normie(lvl=5);
    // n1.lvlUp() ; n1.setStats()
    @Override
    // Normie stats are equally
    protected void setStats() { this.stats = new PokeStats(4*this.lvl+5, 3*this.lvl+3, 3*this.lvl+3, 3*this.lvl+3, 10*this.lvl+10); }

    @Override
    protected PokeAttack[] initAttacks() {
        return new PokeAttack[] {
            new PokeAttack("Tackle","normal",40), 
            new PokeAttack("Bodycheck","normal",60), 
        };
    }

    // 50, 50 -> male, female
    public Normie(int lvl) { super("Normie", "normal", new double[] {0.5, 0.5}, lvl); }

}
