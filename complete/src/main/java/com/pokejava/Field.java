package com.pokejava;

import java.util.Random;
//import java.util.stream.DoubleStream;

public class Field {
    // type="S" -> "street", "G" -> "grass", "W" -> "water", "B" -> "black", "E" -> "entry" to another map, "SNOW"
    private String type;
    public String getType() { return this.type; }
    private boolean isTherePoke = false;
    public boolean getIsTherePoke() { return this.isTherePoke; }
    private boolean isAccessable = true;
    public boolean getIsAccessable () { return this.isAccessable; }
    public void setIsAccessable(boolean isAccessable) { this.isAccessable = isAccessable; }
    
    public Field (String type){
        this.type = type;
        Random random = new Random();
        
        if (this.type != null && !this.type.isEmpty()) {
            if (this.type.equals("G") && random.nextFloat() < 0.3) {
                this.isTherePoke = true;
            }
            
            if (this.type.equals("W") || this.type.equals("B") || this.type.contains("NPC")
                || this.type.contains("houseL") || this.type.contains("houseR") || this.type.equals("houseMUH"))  {
                this.isAccessable = false;
            }
        }
    }

    // field types not random anymore
    /* static String randomType() {
        String[] types = {"street","grass","water","black","entry"}; // entry to another map
        double[] probs = {25, 40, 20}; // 25% Street, 40% Grass, 20% Blocked
        double totalProbs = DoubleStream.of(probs).sum();
        Random random = new Random();
        //int index = random.nextInt(types.length);

        double x = random.nextDouble() * totalProbs;
        for (int i = 0; i < probs.length; ++i) {
            x -= probs[i];
            if (x <= 0) {return types[i];}
        }
        return types[types.length - 1];
    }

    public static String type = randomType(); */

}