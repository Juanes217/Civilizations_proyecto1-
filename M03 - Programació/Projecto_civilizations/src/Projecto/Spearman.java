package Projecto;

public class Spearman extends AttackUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculo corregido y dentro del super)
    public Spearman(int techDefense, int techAttack) {
        // El super DEBE ser la primera línea. 
        // Calculamos: Base + (Nivel * Plus * Base / 100)
        super(
            ARMOR_SPEARMAN + (techDefense * PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY * ARMOR_SPEARMAN / 100), 
            BASE_DAMAGE_SPEARMAN + (techAttack * PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY * BASE_DAMAGE_SPEARMAN / 100)
        );
        
        // Guardamos la armadura con la que nace (con tecnología) para poder resetearla
        this.initialArmor = this.armor; 
    }

    // CONSTRUCTOR 2: Valores básicos (para el enemigo)
    public Spearman() {
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
        this.initialArmor = ARMOR_SPEARMAN;
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
        return FOOD_COST_SPEARMAN; 
    }

    public int getWoodCost() { 
        return WOOD_COST_SPEARMAN; 
    }

    public int getIronCost() { 
        return IRON_COST_SPEARMAN; 
    }

    public int getManaCost() { 
        return MANA_COST_SPEARMAN; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_SPEARMAN; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_SPEARMAN; 
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