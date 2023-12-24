package com.pokejava.pokejavas;

import com.pokejava.*;

public class Firely extends PokeJava {
    
    @Override
    // Firely is fast and offensive, hp and def is lower
    public void setStats() { this.stats = new PokeStats(3*this.lvl+5, 4*this.lvl+4, 2*this.lvl+2, 4*this.lvl+4, 10*this.lvl+10); }

    @Override
    public PokeAttack[] initAttacks() { 
        return new PokeAttack[]{
            new PokeAttack("Tackle","normal",40), 
            new PokeAttack("Fire-Breath","fire",60), 
        };
    }

    // always male
    public Firely(int lvl) { super("Firely", "fire", new double[] {1, 0}, lvl); }

}
