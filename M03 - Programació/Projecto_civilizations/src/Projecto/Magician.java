package Projecto;

public class Magician extends SpecialUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculo del 6% de plus de ataque)
    public Magician(int techDefense, int techAttack) {
        // Suponiendo armadura base 0 si no tienes constante, pero daño base potenciado
        super(
            0, 
            BASE_DAMAGE_MAGICIAN + (techAttack * PLUS_ATTACK_MAGICIAN_BY_TECHNOLOGY * BASE_DAMAGE_MAGICIAN / 100)
        );
        this.initialArmor = this.armor;
    }

    // CONSTRUCTOR 2: Básico (Enemigo)
    public Magician() {
        super(0, BASE_DAMAGE_MAGICIAN);
        this.initialArmor = 0;
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
        return FOOD_COST_MAGICIAN; 
    }

    public int getWoodCost() { 
        return WOOD_COST_MAGICIAN; 
    }

    public int getIronCost() { 
        return IRON_COST_MAGICIAN; 
    }

    public int getManaCost() { 
        return MANA_COST_MAGICIAN; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_MAGICIAN; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_MAGICIAN; 
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