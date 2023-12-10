package com.pokejava;

public class PokeJava {
    
    private String specie;
    public String getSpecie() { return this.specie; }

    private String type;
    public String getType() { return this.type; }

    protected int lvl;
    //public int getLvl() { return this.lvl; }
    public void lvlUp() { 
        if (this.exp >= this.stats.expNextLvl()) {
            this.exp -= this.stats.expNextLvl();
            this.lvl++; 
            this.setStats();

        }
    }

    private int isHp;
    public int getIsHp() { return this.isHp; }
    public void setIsHp(int isHp) { this.isHp = isHp; }
    // recover Hp
    public void recHp() { this.isHp = this.stats.maxHp(); }

    private int exp;
    //public int getExp() { return this.exp; }
    public void incExp(int extraExp) { this.exp += extraExp; }

    protected PokeStats stats;
    public PokeStats getStats() { return this.stats; }
    public void setStats() {}; // Override

    // max 4 Attacks
    protected PokeAttack[] attacks = new PokeAttack[4];
    public PokeAttack[] getAttacks() { return this.attacks; }
    public PokeAttack[] initAttacks() { return null; } // Override

    public record PokeInfo(String specie, String type, int lvl, int isHp, int exp, PokeStats stats, PokeAttack[] attacks) {}
    public PokeInfo getPokeInfo() {
        return new PokeInfo(this.specie, this.type, this.lvl, this.isHp, this.exp, this.stats, this.attacks);
    }

    public PokeJava(String specie, String type, int lvl) {
        this.specie = specie;
        this.type = type;
        this.lvl = lvl;
        this.setStats();
        this.isHp = stats.maxHp();
        this.attacks = initAttacks();

    }
        
    public String[] attackPoke(int attackChoice, PokeJava enemyPoke){
    
        PokeAttack attack = attacks[attackChoice];

        int[] tmp = getEffIntAndDamage(attackChoice, enemyPoke);
        int effInt = tmp[0];
        int damage = tmp[1];
    
        String text1 = this.specie + " used " + attack.name() + " against " + enemyPoke.getSpecie();

        String text2 = null;
        if (effInt == 1){ text2 = "Attack was not effective"; }
        if (effInt == 2){ text2 = "Attack was normal effective"; }
        if (effInt == 4){ text2 = "Attack was very effective"; }
        
        String text3 = null;
        if (enemyPoke.getIsHp() - damage > 0) {  enemyPoke.setIsHp(enemyPoke.getIsHp() - damage); }
        else { 
            enemyPoke.setIsHp(0);
            text3 = enemyPoke.getSpecie() + " fainted";
        }

        return new String[] { text1, text2, text3};

    }

    public int[] getEffIntAndDamage(int attackChoice, PokeJava enemyPoke) {
        
        PokeAttack attack = attacks[attackChoice];

        int effInt = 2;
        if (attack.type() == "fire" && enemyPoke.getType() == "water") { effInt = 1; }
        if (attack.type() == "fire" && enemyPoke.getType() == "grass") { effInt = 4; }
        if (attack.type() == "water" && enemyPoke.getType() == "fire") { effInt = 4; }
        if (attack.type() == "water" && enemyPoke.getType() == "grass") { effInt = 1; }
        if (attack.type() == "grass" && enemyPoke.getType() == "fire") { effInt = 1; }
        if (attack.type() == "grass" && enemyPoke.getType() == "water") { effInt = 4; }

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

