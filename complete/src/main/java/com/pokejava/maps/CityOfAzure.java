package com.pokejava.maps;

import com.pokejava.*;
import com.pokejava.npcs.COApoolNPC;

public class CityOfAzure extends Map {

    @Override
    public String getInitName() { return "City of Azure"; }

    @Override
    // City of Azure above of Route 2, left of Route 4
    public String[][] getInitMatrixString() {
        return new String[][]{
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","G","G","G","S","S","S","S","S","S","S","S","S","S","S","S","S","S","G","G","G","G","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","G","G","G","S","S","G","G","G","S","S","S","S","S","S","S","S","S","G","G","G","G","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","G","G","G","S","S","S","S","S","S","S","S","S","B","B","S","S","S","G","G","G","G","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {"B","B","B","B","B","B","B",null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","S","S","B","W","W","B","S","S","S","S","S","S","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","B",null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","S","B","W","W","W","W","B","S","S","S","S","S","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","B","B","B","B","B","B","B","B","B","B","S","S","S","S","S","S","S","S","S","B","W","W","W","W","W","W","B","S","S","S","S","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","B","W","W","W","W","W","W","W","W","B","S","S","S","B","B","B","B","B","S","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","B","W","W","W","W","W","W","W","W","W","W","B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","B"},
            {"B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","B","W","W","W","W","W","W","W","W","W","W","B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","B"},
            {"B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","S","S","S","S","S","S","S","S","B","W","W","W","W","W","W","W","W","B","S","S","S","B","B","B","B","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","B","W","W","W","W","W","W","B","S","S","S","S","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","S","S","G","G","G","S","S","S","S","S","B","W","W","W","W","B","S","S","S","S","S","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","S","S","B","W","W","B","S","S","S","S","S","S","B","W","W","W","B","G","G","G","S","G","G","G","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","S","S","S","B","B","S","S","S","G","G","G","G","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","G","G","G","G","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","S","G","G","G","G","B","W","W","W","B","S","S","S","S","S","S","S","S","B"},
            {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B","B"}
        };
    }

    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new NPC(new Position(7, 12)), 
            new NPC(new Position(1, 35)),
            new COApoolNPC(new Position(12, 32)),
            new NPC(new Position(7, 45)),
            new NPC(new Position(13, 43)),
        };
    }
    
}
