package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.Field.PokeOccur;
import com.pokejava.npcs.*;
import com.pokejava.pokejavas.*;

public class Route1 extends Map {

    @Override
    public String getInitName() { return "Route 1"; }

    @Override
    // route1 Startmap
    protected String[][] getInitMatrixString() {
        return new String[][]{
            {"B","B","B","B","B","B","B","B","B"},
            {"B","G","G","S","S","S","G","G","B"},
            {"B","G","G","S","S","G","G","G","B"},
            {"B","G","G","G","S","S","G","G","B"},
            {"B","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","S","S","B"},
            {"B","G","G","S","S","S","G","G","B"},
            {"B","G","G","G","S","G","G","G","B"},
            {"B","W","G","S","S","G","G","W","B"},
            {"B","W","W","G","S","S","W","W","B"},
            {"B","W","W","S","S","S","W","W","B"},
            {"B","B","B","B","B","B","B","B","B"}
        };
    }

    @Override
    protected NPC[] getInitNpcs() {
        return new NPC[] { 
            new Route1LeftNPC(new Position(4, 2)), 
            new Route1RightNPC(new Position(2, 7)),
        };
    }

    @Override
    protected PokeOccur[] pokesOccur() {
        return new PokeOccur[] {
            new PokeOccur(0.7, Firely.class, new int[]{2, 3}),
            new PokeOccur(0.3, Normie.class, new int[]{3, 4}),
        };
    }
    
}
