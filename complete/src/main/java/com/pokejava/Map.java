package com.pokejava;

import com.pokejava.NPC.InteractionInfo;

public class Map {

    private String name;
    public String getName() { return this.name; }

    protected Position trainerPos; 
    public Position getPosition() { return this.trainerPos; }
    
    protected String trainerDirection = "up"; // "up" init value
    
    public Position getTargetPosition() { 
        Position target;
        switch(this.trainerDirection) {
            case "left": target = new Position(this.trainerPos.y(), this.trainerPos.x()-1); break;
            case "right": target = new Position(this.trainerPos.y(), this.trainerPos.x()+1); break;
            case "up": target = new Position(this.trainerPos.y()-1, this.trainerPos.x()); break;
            case "down": target = new Position(this.trainerPos.y()+1, this.trainerPos.x()); break;
            default: target = this.trainerPos; break;
        }
        return target;
    }

    private String[][] matrixString;
    public String[][] getInitMatrixString() { return null; } // Override

    private Field[][] matrixField;

    public record MapInfo(String name, String[][] matrixString, Position trainerPos, String trainerDirection, boolean battle) {}
    public MapInfo getMapInfo() {
        return new MapInfo(this.name, this.matrixString, this.trainerPos, this.trainerDirection, wildPokeAttacked());
    }

    private NPC[] npcs;
    public NPC[] getInitNpcs() { return null; } // Override

    private static int findLongestArrayLength(String[][] matrix) {
        int maxLength = 0;

        for (String[] row : matrix) {
            int currentLength = row.length;
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }

        return maxLength;
    }

    public Map (Position trainerPos, String name) {
        this.trainerPos = trainerPos;
        this.matrixString = this.getInitMatrixString();
        this.name = name;
        this.npcs = this.getInitNpcs();

        if (this.npcs != null) {
            for(int i = 0; i < this.npcs.length; i++) {
                this.matrixString[this.npcs[i].getPos().y()][this.npcs[i].getPos().x()] += "NPC";
            }
        }

        MapInterface[] inters = MapInterfaceDB.inters(this.name);
        if (inters != null) {
            for(MapInterface inter : inters) {
                if (inter.type().equals("houseEntry")) {
                    this.matrixString[inter.p().y()][inter.p().x()] = "houseMLH";
                    this.matrixString[inter.p().y()][inter.p().x()-1] = "houseLLH";
                    this.matrixString[inter.p().y()][inter.p().x()+1] = "houseRLH";
                    this.matrixString[inter.p().y()-1][inter.p().x()] = "houseMUH";
                    this.matrixString[inter.p().y()-1][inter.p().x()-1] = "houseLUH";
                    this.matrixString[inter.p().y()-1][inter.p().x()+1] = "houseRUH";
                }
                if (inter.type().equals("mapExit")) {
                    this.matrixString[inter.p().y()][inter.p().x()] = "E";
                }
            }
        }

        this.matrixField = new Field[this.matrixString.length][findLongestArrayLength(this.matrixString)]; // [Y][X]
        // Remember that everything in the matrix is initialized to null so
        // you must initialize everything
        for (int i = 0; i < this.matrixString.length; i++) {
            for (int j = 0; j < this.matrixString[i].length; j++) {
                this.matrixField[i][j] = new Field(this.matrixString[i][j]);
            }
        }

    }

    public void mvTrainerInMap(String direction) {
        
        // update trainerDirection should be always possible
        this.trainerDirection = direction;
        
        Position target = getTargetPosition();

        // Map must have a border of not accessable fields
        if ( this.matrixField [target.y()][target.x()].getIsAccessable() ) {
            
            // update map.trainerPos
            this.trainerPos = target;

        } else { /*System.out.println("\nBLOCKED moving");*/ }
        
    }

    public InteractionInfo npcInteraction(String userAnswer, PokeJava[] pokes) {

        // is on target position a npc?
        Position target = getTargetPosition();

        for (NPC npc : npcs) {
            if (target.equals(npc.getPos())) { return npc.interacted(userAnswer, pokes); }
        }
        return null;
        
    }

    private boolean wildPokeAttacked() {
        return this.matrixField[this.trainerPos.y()][this.trainerPos.x()].getIsTherePoke();
    }

    
}
