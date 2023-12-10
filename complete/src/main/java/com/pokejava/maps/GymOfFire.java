package com.pokejava.maps;

import com.pokejava.*;

public class GymOfFire extends Map {

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
            new NPC(new Position(2, 4)), 
            new NPC(new Position(4, 5)), 
            new NPC(new Position(6, 3)), 
        };
    }

    public GymOfFire(Position trainerPos) { super(trainerPos, "Gym of Fire"); }
    
}