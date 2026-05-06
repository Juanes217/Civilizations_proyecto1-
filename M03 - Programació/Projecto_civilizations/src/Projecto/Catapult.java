package Projecto;

public class Catapult extends DefenseUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculos en super para evitar errores)
    public Catapult(int techDefense, int techAttack) {
        super(
            ARMOR_CATAPULT + (techDefense * PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY * ARMOR_CATAPULT / 100), 
            BASE_DAMAGE_CATAPULT + (techAttack * PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY * BASE_DAMAGE_CATAPULT / 100)
        );
        
        // Guardamos la armadura ya potenciada
        this.initialArmor = this.armor; 
    }

    // CONSTRUCTOR 2: Sin parámetros (Útil para el enemigo)
    public Catapult() {
        super(ARMOR_CATAPULT, BASE_DAMAGE_CATAPULT);
        this.initialArmor = ARMOR_CATAPULT;
    }

    // --- MÉTODOS DE MILITARYUNIT ---

    public int attack() {
        return this.baseDamage;
    }

    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
    }

    public int getActualArmor() {
        return this.armor;
    }

    public int getFoodCost() { 
        return FOOD_COST_CATAPULT; 
    }

    public int getWoodCost() { 
        return WOOD_COST_CATAPULT; 
    }

    public int getIronCost() { 
        return IRON_COST_CATAPULT; 
    }

    public int getManaCost() { 
        return MANA_COST_CATAPULT; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_CATAPULT; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CATAPULT; 
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