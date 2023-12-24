package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.Field.PokeOccur;
import com.pokejava.pokejavas.*;

public class Route2 extends Map {

    @Override
    public String getInitName() { return "Route 2"; }

    @Override
    // route2 left from route1
    public String[][] getInitMatrixString() {
        return new String[][]{
            {"B","B","B","B",null,null,null,null,null,null,null,null,null,null,null,"B","B","B","B","B","B","B","B","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","B","B","G","G","G","G","G","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","B","B","G","G","G","G","G","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","B","G","G","S","G","G","G","B"},
            {"B","G","G","B","B","B","B","B","B","B","B","B","B","B","B","B","G","G","G","S","S","G","G","B"},
            {"B","G","G","G","G","G","G","G","G","G","G","G","G","G","G","S","S","S","S","S","S","S","S","B"},
            {"B","G","G","G","G","G","G","G","G","G","G","G","G","G","G","S","S","S","S","S","S","S","S","B"},
            {"B","G","G","B","B","B","B","B","B","B","B","B","B","B","B","B","G","G","G","G","G","S","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","G","G","G","G","G","G","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","G","G","G","G","G","W","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,null,"B","W","W","W","W","W","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,null,"B","W","W","W","W","W","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","W","W","W","W","W","W","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","W","W","W","W","W","W","W","B"},
            {"B","G","G","B",null,null,null,null,null,null,null,null,null,null,null,"B","W","W","W","W","W","W","W","B"},
            {"B","B","B","B",null,null,null,null,null,null,null,null,null,null,null,"B","B","B","B","B","B","B","B","B"}
        };
    }

    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new NPC(new Position(3, 19)), 
            new NPC(new Position(9, 17)),
        };
    }

    @Override
    protected PokeOccur[] pokesOccur() {
        return new PokeOccur[] {
            new PokeOccur(0.7, Waterly.class, new int[]{3, 5}),
            new PokeOccur(0.3, Grassie.class, new int[]{3, 4}),
        };
    }
    
}
