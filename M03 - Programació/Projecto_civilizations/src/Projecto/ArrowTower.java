package Projecto; // Cámbialo a com.civilizations si es el que usas, pero que sea igual en todas

public class ArrowTower extends DefenseUnit implements Variables {
    
    private int initialArmor;

    // Constructor único (según el enunciado)
    // Le pasamos los niveles de tecnología para calcular los stats
    public ArrowTower(int techDefense, int techAttack) {
        // Cálculo directo en el super: Base + (Nivel * Plus * Base / 100)
        super(
            ARMOR_ARROWTOWER + (techDefense * PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY * ARMOR_ARROWTOWER / 100), 
            BASE_DAMAGE_ARROWTOWER + (techAttack * PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY * BASE_DAMAGE_ARROWTOWER / 100)
        );
        
        // La armadura inicial es la calculada (con tecnología)
        this.initialArmor = this.armor; 
    }

    // Constructor sin parámetros (por si lo necesitas para el enemigo)
    public ArrowTower() {
        super(ARMOR_ARROWTOWER, BASE_DAMAGE_ARROWTOWER);
        this.initialArmor = ARMOR_ARROWTOWER;
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
        return FOOD_COST_ARROWTOWER; 
    }

    public int getWoodCost() { 
        return WOOD_COST_ARROWTOWER; 
    }

    public int getIronCost() { 
        return IRON_COST_ARROWTOWER; 
    }

    public int getManaCost() { 
        return MANA_COST_ARROWTOWER; 
    }

    public int getChanceGeneratingWaste() { 
        return CHANCE_GENERATNG_WASTE_ARROWTOWER; 
    }

    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_ARROWTOWER; 
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