package com.pokejava.pokejavas;

import com.pokejava.*;

public class Grassie extends PokeJava {
    
    @Override
    // Grassie is slow and defensive, hp is higher, atk is lower
    public void setStats() { this.stats = new PokeStats(5*this.lvl+6, 2*this.lvl+2, 4*this.lvl+4, 2*this.lvl+2, 10*this.lvl+10); }

    @Override
    public PokeAttack[] initAttacks() {
        return new PokeAttack[] {
            new PokeAttack("Tackle","normal",40), 
            new PokeAttack("Plant-Power","grass",60), 
        };
    }

    // always female
    public Grassie(int lvl) { super("Grassie", "grass", new double[] {0, 1}, lvl); }

}
