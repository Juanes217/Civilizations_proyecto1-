package Projecto;

public class Priest extends SpecialUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR ÚNICO (Como pide el enunciado)
    // Aunque el cura tiene 0 ataque y 0 armadura base en tus constantes, 
    public Priest(int armor, int baseDamage) {
        super(armor, baseDamage);
            armor = 0;
            baseDamage = 0;
    }

    // --- MÉTODOS OBLIGATORIOS DE MILITARYUNIT ---

    public int attack() {
        return 0; // Los sacerdotes no atacan
    }

    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
    }

    public int getActualArmor() {
        return this.armor;
    }

    public int getFoodCost() { 
        return FOOD_COST_PRIEST; 
    }

    public int getWoodCost() { 
        return WOOD_COST_PRIEST; 
    }

    public int getIronCost() { 
        return IRON_COST_PRIEST; 
    }

    public int getManaCost() { 
        return MANA_COST_PRIEST; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_PRIEST; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_PRIEST; 
    }

    public void resetArmor() {
        this.armor = this.initialArmor;
    }

    public void setExperience(int n) {
        this.experience = n;
    }

    public int getExperience() {
        return this.experience;
    }
}