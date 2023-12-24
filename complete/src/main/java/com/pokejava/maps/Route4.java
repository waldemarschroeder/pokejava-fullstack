package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.Field.PokeOccur;
import com.pokejava.npcs.*;
import com.pokejava.pokejavas.*;

public class Route4 extends Map {

    @Override
    public String getInitName() { return "Route 4"; }

    @Override
    // route4 above route1
    public String[][] getInitMatrixString() {
        return new String[][]{
            {"B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B"},
            {"E","G","G","G","G","G","G","G","G","G","G","S","S","S","W","W","B"},
            {"B","G","G","G","G","G","G","G","G","G","G","S","S","S","W","W","B"},
            {"B","G","G","G","G","G","G","G","G","G","G","S","S","S","W","W","B"},
            {"B","B","B","B","B","B","B","B","B","S","S","S","S","W","W","S","B"},
            {null,null,null,null,null,null,null,null,"B","S","S","S","S","W","S","S","B"},
            {null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,"B","G","G","W","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,"B","W","W","W","S","S","G","G","B"},
            {null,null,null,null,null,null,null,null,"B","W","W","S","S","S","G","G","B"},
            {null,null,null,null,null,null,null,null,"B","W","W","S","S","S","G","G","B"},
            {null,null,null,null,null,null,null,null,"B","W","W","S","S","S","G","G","B"},
            {null,null,null,null,null,null,null,null,"B","B","B","B","B","B","B","B","B"}
        };
    }

    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new NPC(new Position(1, 4)), 
            new Route4RightNPC(new Position(1, 9)),
        };
    }

    @Override
    protected PokeOccur[] pokesOccur() {
        return new PokeOccur[] {
            new PokeOccur(0.7, Firely.class, new int[]{3, 6}),
            new PokeOccur(0.3, Waterly.class, new int[]{3, 6}),
        };
    }
    
}
