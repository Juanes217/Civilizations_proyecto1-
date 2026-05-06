package Projecto;

public class RocketLauncherTower extends DefenseUnit implements Variables {
    
    private int initialArmor;

    // CONSTRUCTOR 1: Con tecnología (Cálculo directo en super)
    public RocketLauncherTower(int techDefense, int techAttack) {
        super(
            ARMOR_ROCKETLAUNCHERTOWER + (techDefense * PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY * ARMOR_ROCKETLAUNCHERTOWER / 100), 
            BASE_DAMAGE_ROCKETLAUNCHERTOWER + (techAttack * PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY * BASE_DAMAGE_ROCKETLAUNCHERTOWER / 100)
        );
        
        // Guardamos la armadura ya potenciada para los resets
        this.initialArmor = this.armor; 
    }

    // CONSTRUCTOR 2: Sin parámetros (Para enemigos)
    public RocketLauncherTower() {
        super(ARMOR_ROCKETLAUNCHERTOWER, BASE_DAMAGE_ROCKETLAUNCHERTOWER);
        this.initialArmor = ARMOR_ROCKETLAUNCHERTOWER;
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
        return FOOD_COST_ROCKETLAUNCHERTOWER; 
    }

    public int getWoodCost() { 
        return WOOD_COST_ROCKETLAUNCHERTOWER; 
    }

    public int getIronCost() { 
        return IRON_COST_ROCKETLAUNCHERTOWER; 
    }

    public int getManaCost() { 
        return MANA_COST_ROCKETLAUNCHERTOWER; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_ROCKETLAUNCHERTOWER; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER; 
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