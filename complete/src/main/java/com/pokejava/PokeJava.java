package com.pokejava;

public class PokeJava {
    
    private String specie;
    private String type;
    
    private String gender;
    // new double[] {0.5, 0.5} -> 50% m or f
    private String getInitGender(double[] probsGender) {
        // Check if the input array has exactly two elements
        if (probsGender.length != 2) {
            throw new IllegalArgumentException("The input array must have exactly two elements representing male and female probabilities.");
        }

        // Check if both probabilities are 0
        if (probsGender[0] == 0 && probsGender[1] == 0) {
            return "none";
        }
    
        // Calculate a random value between 0 and 1
        double randomValue = Math.random();
    
        // Determine the initial gender based on the probabilities
        if (randomValue < probsGender[0]) {
            return "male";
        } else {
            return "female";
        }
    }    

    protected int lvl;

    private int isHp;
    public void setIsHp(int isHp) { this.isHp = isHp; }
    // recover Hp
    public void recHp() { this.isHp = this.stats.maxHp(); }

    private int exp;
    public void incExp(int extraExp) { 
        this.exp += extraExp; 
        if (this.exp >= this.stats.expNextLvl()) {
            this.exp -= this.stats.expNextLvl();
            this.lvl++; 
            this.setStats();

        }
    }

    protected PokeStats stats;
    protected void setStats() {}; // Override

    // max 4 Attacks
    protected PokeAttack[] attacks = new PokeAttack[4];
    protected PokeAttack[] initAttacks() { return null; } // Override

    public record PokeStats (int maxHp, int atk, int def, int speed, int expNextLvl) {}

    public record PokeAttack(String name, String type, int power) {}

    public record PokeInfo(String specie, String type, String gender, int lvl, int isHp, int exp, PokeStats stats, PokeAttack[] attacks) {}
    public PokeInfo getPokeInfo() {
        return new PokeInfo(this.specie, this.type, this.gender, this.lvl, this.isHp, this.exp, this.stats, this.attacks);
    }

    public PokeJava(String specie, String type, double[] probsGender, int lvl) {
        this.specie = specie;
        this.type = type;
        this.gender = getInitGender(probsGender);
        this.lvl = lvl;
        this.setStats();
        this.isHp = stats.maxHp();
        this.attacks = initAttacks();

    }
        
    public String[] attackPoke(int attackChoice, PokeJava enemyPoke){
    
        PokeAttack attack = attacks[attackChoice];
        PokeInfo enemyPokeInfo = enemyPoke.getPokeInfo();

        int[] tmp = getEffIntAndDamage(attackChoice, enemyPoke);
        int effInt = tmp[0];
        int damage = tmp[1];
    
        String text1 = this.specie + " used " + attack.name() + " against " + enemyPokeInfo.specie();

        String text2 = null;
        if (effInt == 1){ text2 = "Attack was not effective"; }
        if (effInt == 2){ text2 = "Attack was normal effective"; }
        if (effInt == 4){ text2 = "Attack was very effective"; }
        
        String text3 = null;
        if (enemyPokeInfo.isHp() - damage > 0) {  enemyPoke.setIsHp(enemyPokeInfo.isHp() - damage); }
        else { 
            enemyPoke.setIsHp(0);
            text3 = enemyPokeInfo.specie() + " fainted";
        }

        return new String[] { text1, text2, text3};

    }

    private int[] getEffIntAndDamage(int attackChoice, PokeJava enemyPoke) {
        
        PokeAttack attack = attacks[attackChoice];
        PokeInfo enemyPokeInfo = enemyPoke.getPokeInfo();

        int effInt = 2;
        if (attack.type() == "fire" && enemyPokeInfo.type() == "water") { effInt = 1; }
        if (attack.type() == "fire" && enemyPokeInfo.type() == "grass") { effInt = 4; }
        if (attack.type() == "water" && enemyPokeInfo.type() == "fire") { effInt = 4; }
        if (attack.type() == "water" && enemyPokeInfo.type() == "grass") { effInt = 1; }
        if (attack.type() == "grass" && enemyPokeInfo.type() == "fire") { effInt = 1; }
        if (attack.type() == "grass" && enemyPokeInfo.type() == "water") { effInt = 4; }

        int typeBonus = 1;
        if (attack.type() == this.type) { typeBonus = 2; }
        int damage = 1 + (attack.power()*typeBonus*effInt*this.stats.atk() / enemyPoke.stats.def()) / 50;

        return new int[] {effInt, damage};
    }

    public int getIndexBestAttack(PokeJava enemyPoke) {
        int[] damages = new int[attacks.length];
        for (int i = 0; i < attacks.length; i++) {;
            damages[i] = getEffIntAndDamage(i, enemyPoke)[1];
        }
        return getIndexAtMax(damages);
    }
    
    private int getIndexAtMax(int[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
