package com.pokejava.maps;

import com.pokejava.*;

public class COAh2 extends Map {

    @Override
    // House2 in City of Azure
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
            new NPC(new Position(6, 3)), 
        };
    }

    public COAh2(Position trainerPos) { super(trainerPos, "House 2 in Azure"); }
    
}