package com.pokejava;

import com.pokejava.NPC.InteractionInfo;

public class Map {

    private String name;
    public String getName() { return this.name; }

    protected Position trainerPos; 
    public Position getPosition() { return this.trainerPos; }

    protected boolean trainerMayMove = true;
    protected boolean npcMoving = false;
    
    protected String trainerDirection = "up"; // "up" init value
    
    public Position getTargetPosition(Position p, String direction) { 
        Position target;
        switch(direction) {
            case "left": target = new Position(p.y(), p.x()-1); break;
            case "right": target = new Position(p.y(), p.x()+1); break;
            case "up": target = new Position(p.y()-1, p.x()); break;
            case "down": target = new Position(p.y()+1, p.x()); break;
            default: target = p; break;
        }
        return target;
    }

    private String[][] matrixString;
    public String[][] getInitMatrixString() { return null; } // Override

    private Field[][] matrixField;

    public record MapInfo(String name, String[][] matrixString, Position trainerPos, String trainerDirection, boolean trainerMayMove, boolean npcMoving, Position[] npcsPos, boolean battle) {}
    public MapInfo getMapInfo() {
        return new MapInfo(this.name, this.matrixString, this.trainerPos, this.trainerDirection, this.trainerMayMove, this.npcMoving, this.getNpcsPos(), wildPokeAttacked());
    }

    private NPC[] npcs;
    public NPC[] getInitNpcs() { return null; } // Override
    public Position[] getNpcsPos() {
        Position[] npcPositions = new Position[npcs.length]; // Assuming npcs is an array
        for (int i = 0; i < npcs.length; i++) {
            NPC npc = npcs[i];
            Position npcPosition = npc.getPos();
            npcPositions[i] = npcPosition;
        }
        return npcPositions;
    }

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
        
        if (this.npcs != null) {
            for(int i = 0; i < this.npcs.length; i++) {
                this.matrixField[this.npcs[i].getPos().y()][this.npcs[i].getPos().x()].setIsAccessable(false);
            }
        }

    }

    public void moveTrainer(String direction) {
        
        if (!trainerMayMove) { return; }

        // update trainerDirection should be always possible
        this.trainerDirection = direction;
        
        Position target = getTargetPosition(this.trainerPos, direction);

        // Map must have a border of not accessable fields
        if ( this.matrixField [target.y()][target.x()].getIsAccessable() ) {
            
            // update map.trainerPos
            this.trainerPos = target;

        } else { /*System.out.println("\nBLOCKED moving");*/ }
        
    }

    public void moveObj(NPC npc, String direction) {
        
        Position target = getTargetPosition(npc.getPos(), direction);

        // Map must have a border of not accessable fields
        if ( this.matrixField [target.y()][target.x()].getIsAccessable() ) {
            
            // update position
            npc.setPos(target);

        } else { /*System.out.println("\nBLOCKED moving");*/ }
        
    }

    public InteractionInfo npcInteraction(String userAnswer, PokeJava[] pokes) {

        // is on target position a npc?
        Position target = getTargetPosition(this.trainerPos, this.trainerDirection);

        for (NPC npc : npcs) {
            if (target.equals(npc.getPos())) { return npc.interacted(userAnswer, pokes); }
        }
        return null;
        
    }

    private boolean wildPokeAttacked() {
        return this.matrixField[this.trainerPos.y()][this.trainerPos.x()].getIsTherePoke();
    }

    
}
