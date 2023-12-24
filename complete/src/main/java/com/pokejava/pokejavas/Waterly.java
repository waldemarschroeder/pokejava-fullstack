package com.pokejava.pokejavas;

import com.pokejava.*;

public class Waterly extends PokeJava {
    
    @Override
    // Waterly is fast and defensive, hp and atk is lower
    public void setStats() { this.stats = new PokeStats(3*this.lvl+5, 2*this.lvl+2, 4*this.lvl+4, 4*this.lvl+4, 10*this.lvl+10); }

    @Override
    public PokeAttack[] initAttacks() {
        return new PokeAttack[] {
            new PokeAttack("Tackle","normal",40), 
            new PokeAttack("Aqua-Gun","water",60), 
        };
    }

    // without gender
    public Waterly(int lvl) { super("Waterly", "water", new double[] {0, 0}, lvl); }

}