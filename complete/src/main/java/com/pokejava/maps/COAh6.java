package com.pokejava.maps;

import com.pokejava.*;

public class COAh6 extends Map {

    @Override
    public String getInitName() { return "House 6 in Azure"; }

    @Override
    // House6 in City of Azure
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
            new NPC(new Position(1, 1)), 
        };
    }
    
}