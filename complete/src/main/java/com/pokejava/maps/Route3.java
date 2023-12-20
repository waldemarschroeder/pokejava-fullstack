package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.npcs.Route3TopNpc;

public class Route3 extends Map {

    @Override
    // route3 right from route1
    public String[][] getInitMatrixString() {
        return new String[][]{
            {"B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B"},
            {"B","B","B","B","B","B","B","B","B","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","B","W","W","W","B","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","S","S","S","S","S","S","S","S","S","G","G","G","G","G","G","B"},
            {"B","S","S","S","S","S","S","S","S","S","S","S","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","G","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","B","G","G","G","W","W","W","G","G","G","G","G","G","G","G","B"},
            {"B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B"}
        };
    }

    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] {  
            new Route3TopNpc(new Position(3, 8)),
            new NPC(new Position(7, 6)),
            new NPC(new Position(12, 9)),
        };
    }

    public Route3(Position trainerPos) { super(trainerPos, "Route 3"); }
    
}
