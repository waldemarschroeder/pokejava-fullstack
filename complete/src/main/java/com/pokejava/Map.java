package com.pokejava;

import com.pokejava.Field.PokeOccur;
import com.pokejava.MapInterfaceDB.MapInterface;
import com.pokejava.NPC.InteractionInfo;

public class Map {

    private String name;
    public String getInitName() { return null; } // Override

    private NPC trainer;
    public NPC getTrainer() { return this.trainer; }
    public void setTrainer(NPC trainer) { this.trainer = trainer; }

    protected boolean trainerMayMove = true;
    public boolean getTrainerMayMove() { return this.trainerMayMove; }
    public void setTrainerMayMove(boolean trainerMayMove) { this.trainerMayMove = trainerMayMove; }

    protected PokeJava wildPoke;
    //public void setBattle(boolean battle) { this.battle = battle; }

    protected PokeOccur[] pokesOccur() { return null; } // Override

    private String[][] matrixString;
    protected String[][] getInitMatrixString() { return null; } // Override

    private Field[][] matrixField;

    public record MapInfo(String name, String[][] matrixString, Position trainerPos, String trainerDirection, boolean trainerMayMove, Position[] npcsPos, PokeJava wildPoke) {}
    public MapInfo getMapInfo() {
        return new MapInfo(this.name, this.matrixString, this.trainer.getPos(), this.trainer.getDirection(), this.trainerMayMove, this.getNpcsPos(), this.wildPoke);
    }

    private NPC[] npcs;
    protected NPC[] getInitNpcs() { return null; } // Override
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

    public Map() {

        this.matrixString = this.getInitMatrixString();
        this.name = this.getInitName();
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
                this.matrixField[i][j] = new Field(this.matrixString[i][j], this.pokesOccur());
            }
        }
        
        if (this.npcs != null) {
            for(int i = 0; i < this.npcs.length; i++) {
                fieldSetIsAccessable(this.npcs[i].getPos(), false);
            }
        }

    }

    public void fieldSetIsAccessable(Position p, boolean isAccessable) {
        this.matrixField[p.y()][p.x()].setIsAccessable(isAccessable);
    }
    
    public void moveObj(Position pold, Position target) {
        if (mayMove(target)) { 
            // old field is accessable again, the new one not
            fieldSetIsAccessable(pold, true);
            fieldSetIsAccessable(target, false);
        }
    }

    public boolean mayMove(Position target) {
        // Map must have a border of not accessable fields
        return this.matrixField [target.y()][target.x()].getIsAccessable();
    }

    public InteractionInfo npcInteraction(String userAnswer) {

        // is on target position a npc?
        Position target = trainer.getTargetPosition();

        for (NPC npc : npcs) {
            if (target.equals(npc.getPos())) { 
                InteractionInfo activeNpc = npc.interacted(userAnswer, trainer);
                if (activeNpc.possibleUserAnswers() != null) { this.trainerMayMove = false; }
                else if (activeNpc.battle() != null) { 
                    this.trainerMayMove = false; 
                    //this.battle = true;
                }
                else {
                    this.trainerMayMove = true; 
                    //this.battle = false;
                }
                return activeNpc; 
            }
        }
        return null;
        
    }

    public boolean wildPokeAttacked() {
        Position trainerPos = this.trainer.getPos();
        this.wildPoke = this.matrixField[trainerPos.y()][trainerPos.x()].getWildPoke();
        if (wildPoke != null) { return true; }
        else { return false; }
    }

    // true if one npc wants to interact
    public boolean npcsAutoAction() {
        // activate autoAction from every npc 
        for (NPC npc : npcs) { 
            if (npc.autoAction(this)) { return true; }
        }
        return false;
    }

    // true if one npc saw the trainer
    public boolean npcsTrainerSeen() {
        for (NPC npc : npcs) { 
            npc.setTrainerSeen(trainer); 
            if (npc.getTrainerSeen()) {
                setTrainerMayMove(false);
                npc.setDirectionToNpc(trainer);
                return true;
            }
        }
        return false;
    }

    
}
