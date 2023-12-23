package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.npcs.*;

public class Route1 extends Map {

    @Override
    public String getInitName() { return "Route 1"; }

    @Override
    // route1 Startmap
    public String[][] getInitMatrixString() {
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
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new Route1LeftNPC(new Position(4, 2)), 
            new Route1RightNPC(new Position(2, 7)),
        };
    }
    
}
