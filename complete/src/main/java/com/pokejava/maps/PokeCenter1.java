package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.npcs.PokeCenterLady;

public class PokeCenter1 extends Map {

    @Override
    public String getInitName() { return "PokeCenter Route 3"; }

    @Override
    // PokeCenter1 in Route 3
    public String[][] getInitMatrixString() {
        return new String[][]{
            {"B","B","B","B","B","B","B","B","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","floor","floor","floor","floor","floor","floor","floor","B"},
            {"B","B","B","B","B","B","B","B","B"}
        };
    }
    
    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new PokeCenterLady(new Position(2, 4)), 
        };
    }
    
}
