package Projecto;

public class Swordsman extends AttackUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculo dentro de super para evitar errores)
    public Swordsman(int techDefense, int techAttack) {
        super(
            ARMOR_SWORDSMAN + (techDefense * PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY * ARMOR_SWORDSMAN / 100), 
            BASE_DAMAGE_SWORDSMAN + (techAttack * PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY * BASE_DAMAGE_SWORDSMAN / 100)
        );
        
        // Tras el super, ya podemos usar this.armor para guardar el valor inicial
        this.initialArmor = this.armor; 
    }

    // CONSTRUCTOR 2: Valores básicos (para el enemigo)
    public Swordsman() {
        super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
        this.initialArmor = ARMOR_SWORDSMAN;
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
        return FOOD_COST_SWORDSMAN; 
    }

    public int getWoodCost() { 
        return WOOD_COST_SWORDSMAN; 
    }

    public int getIronCost() { 
        return IRON_COST_SWORDSMAN; 
    }

    public int getManaCost() { 
        return MANA_COST_SWORDSMAN; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_SWORDSMAN; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_SWORDSMAN; 
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