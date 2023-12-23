package com.pokejava.maps;

import com.pokejava.*;

public class COAh3 extends Map {

    @Override
    public String getInitName() { return "House 3 in Azure"; }

    @Override
    // House3 in City of Azure
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
            new NPC(new Position(3, 4)), 
        };
    }
    
}