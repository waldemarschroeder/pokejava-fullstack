package com.pokejava;

import java.util.Random;

public class Field {

    private String type;
    private boolean isTherePoke = false;
    private PokeJava wildPoke;
    private boolean isAccessable = true;

    // Define the record for Pokemon occurrences
    public record PokeOccur(double classProb, Class<? extends PokeJava> pokeClass, int[] lvlRange) {}

    public Field(String type, PokeOccur[] pokesOccur) {
        this.type = type;
        Random random = new Random();

        if (this.type != null && !this.type.isEmpty()) {
            if (this.type.equals("G") && random.nextFloat() < GrassProbabilityThreshold.PROBABILITY) {
                this.isTherePoke = true;
                if (pokesOccur == null) return;
                this.wildPoke = randomWildPoke(pokesOccur);
            }

            if (this.type.equals("W") || this.type.equals("B") || this.type.contains("NPC")
                    || this.type.contains("houseL") || this.type.contains("houseR") || this.type.equals("houseMUH")) {
                this.isAccessable = false;
            }
        }
    }

    private PokeJava randomWildPoke(PokeOccur[] pokesOccur) {
        Random random = new Random();
        double rand = random.nextDouble();
        double cumulativeProb = 0.0;

        for (int i = 0; i < pokesOccur.length; i++) {
            cumulativeProb += pokesOccur[i].classProb();
            if (rand < cumulativeProb) {
                int[] lvlRange = pokesOccur[i].lvlRange();
                int randomLevel = random.nextInt(lvlRange[1] - lvlRange[0] + 1) + lvlRange[0];
                try {
                    return pokesOccur[i].pokeClass().getConstructor(int.class).newInstance(randomLevel);
                } catch (Exception e) {
                    // Handle the exception, log or rethrow if necessary
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getType() { return this.type; }

    public boolean getIsTherePoke() { return this.isTherePoke; }

    public PokeJava getWildPoke() { return this.wildPoke; }

    public boolean getIsAccessable() { return this.isAccessable; }
    public void setIsAccessable(boolean isAccessable) { this.isAccessable = isAccessable; }
    
    // Define a constant for Grass probability threshold
    private static class GrassProbabilityThreshold {
        public static final double PROBABILITY = 0.3;
    }
}
