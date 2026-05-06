package Projecto;

public class Cannon extends AttackUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculo directo en super para evitar errores)
    public Cannon(int techDefense, int techAttack) {
        super(
            ARMOR_CANNON + (techDefense * PLUS_ARMOR_CANNON_BY_TECHNOLOGY * ARMOR_CANNON / 100), 
            BASE_DAMAGE_CANNON + (techAttack * PLUS_ATTACK_CANNON_BY_TECHNOLOGY * BASE_DAMAGE_CANNON / 100)
        );
        
        // La armadura inicial es la calculada con tecnología
        this.initialArmor = this.armor; 
    }

    // CONSTRUCTOR 2: Valores básicos (Enemigo)
    public Cannon() {
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
        this.initialArmor = ARMOR_CANNON;
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
        return FOOD_COST_CANNON; 
    }

    public int getWoodCost() { 
        return WOOD_COST_CANNON; 
    }

    public int getIronCost() { 
        return IRON_COST_CANNON; 
    }

    public int getManaCost() { 
        return MANA_COST_CANNON; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_CANNON; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CANNON; 
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