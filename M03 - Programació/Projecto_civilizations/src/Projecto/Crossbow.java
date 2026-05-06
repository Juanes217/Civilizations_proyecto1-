package Projecto;

public class Crossbow extends AttackUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculo directo para evitar errores de compilación)
    public Crossbow(int techDefense, int techAttack) {
        super(
            ARMOR_CROSSBOW + (techDefense * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY * ARMOR_CROSSBOW / 100), 
            BASE_DAMAGE_CROSSBOW + (techAttack * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY * BASE_DAMAGE_CROSSBOW / 100)
        );
        
        // Guardamos el valor final calculado para resetear después de batalla
        this.initialArmor = this.armor; 
    }

    // CONSTRUCTOR 2: Valores básicos (Enemigo)
    public Crossbow() {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
        this.initialArmor = ARMOR_CROSSBOW;
    }

    // --- MÉTODOS DE LA INTERFAZ MILITARYUNIT ---

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
        return FOOD_COST_CROSSBOW; 
    }

    public int getWoodCost() { 
        return WOOD_COST_CROSSBOW; 
    }

    public int getIronCost() { 
        return IRON_COST_CROSSBOW; 
    }

    public int getManaCost() { 
        return MANA_COST_CROSSBOW; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_CROSSBOW; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CROSSBOW; 
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