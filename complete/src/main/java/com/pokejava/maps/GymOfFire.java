package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.npcs.*;

public class GymOfFire extends Map {

    @Override
    public String getInitName() { return "Gym of Fire"; }

    @Override
    // PokeCenter1 in Route 3
    public String[][] getInitMatrixString() {
        return new String[][]{
            {"B","B","B","B","B","B","B","B","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","floor","floor","floor","S","floor","floor","floor","B"},
            {"B","B","B","B","B","B","B","B","B"}
        };
    }
    
    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new GymOfFireLeader(new Position(2, 4)), 
            new GymOfFireSec(new Position(4, 5)), 
            new GymOfFireFirst(new Position(6, 3)), 
        };
    }
    
}