package com.pokejava.maps;

import com.pokejava.*;

public class Mystery_Place extends Map {

    @Override
    // Mystery Place above Rout3
    public String[][] getInitMatrixString() {
        return new String[][]{
            {null,null,"B","B","B","B","B","B","B"},
            {null,"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B","B","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B","B","B","B","B","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","SNOW","B",null,null,null,"B","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","SNOW","SNOW","G","B","B",null,null,null,"B","SNOW","SNOW","B"},
            {"B","SNOW","SNOW","SNOW","G","G","G","B",null,null,null,null,"B","SNOW","SNOW","B"},
            {"B","B","B","B","B","B","B",null,null,null,null,null,"B","B","B","B"}
        };
    }

    @Override
    public NPC[] getInitNpcs() {
        return new NPC[] { 
            new NPC(new Position(4, 2)), 
            new NPC(new Position(9, 4)),
            new NPC(new Position(6, 8)),

        };
    }

    public Mystery_Place(NPC trainer) { super(trainer, "Mystery Place"); }
}
