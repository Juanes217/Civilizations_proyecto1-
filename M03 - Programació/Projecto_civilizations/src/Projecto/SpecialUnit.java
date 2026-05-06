package Projecto;

public class SpecialUnit implements MilitaryUnit, Variables {
	int armor;
    int initialArmor;
    int baseDamage;
    int experience;

    public SpecialUnit (int armor, int initialArmor, int baseDamage, int experience) {
        this.armor = armor;
        this.initialArmor = initialArmor;
        this.baseDamage = baseDamage;
        this.experience = experience;
    }
    
    public SpecialUnit(int armor, int baseDamage) {
        this.armor = armor;
        this.baseDamage = baseDamage;
    }

    public int attack() {
        // Return the attack power of the unit.
        return baseDamage;
    }

    public void takeDamage(int receivedDamage) {
        // Reduce armor by the received damage.
        armor -= receivedDamage;
        if (armor <= 0) {
            armor = 0;
        }
    }

    public int getActualArmor() {
        // Return the current armor level after taking damage.
        return armor;
    }

    @Override
    public int getFoodCost() {
        // Return the food cost of creating a new unit.
        switch (this.getClass().getSimpleName()) {
            case "MAGICIAN":
                return FOOD_COST_UNITS[7];
            case "PRIEST":
                return FOOD_COST_UNITS[8];

                }
    return 0;}
       
    public int getWoodCost() {
        // Return the wood cost of creating a new unit.
        switch (this.getClass().getSimpleName()) {
            case "MAGICIAN":
                return WOOD_COST_UNITS[7];
            case "PRIEST":
                return WOOD_COST_UNITS[8];

                }
    return 0;}    

    public int getIronCost() {
        // Return the iron cost of creating a new unit.
        switch (this.getClass().getSimpleName()) {
            case "MAGICIAN":
                return IRON_COST_UNITS[7];
            case "PRIEST":
                return IRON_COST_UNITS[8];

            }
        return 0;} 

    public int getManaCost() {
        // Return the mana cost of creating a new unit.
        return 0;
    }

    public int getChanceGeneratinWaste() {
        if (armor <= 0) {
            switch (this.getClass().getSimpleName()) {
                case "MAGICIAN":
                    return CHANCE_GENERATNG_WASTE_MAGICIAN;
                case "PRIEST":
                    return CHANCE_GENERATNG_WASTE_PRIEST;

            }
        }
        return 0;
    }

    public int getChanceAttackAgain() {
        // Return the probability of attacking again.
        switch (this.getClass().getSimpleName()) {
            case "MAGICIAN":
                return CHANCE_ATTACK_AGAIN_MAGICIAN;
            case "PRIEST":
                return CHANCE_ATTACK_AGAIN_PRIEST;
        }
        return 0;
    }

    public void resetArmor() {
        // Reset armor to its initial value.
        armor = initialArmor;
    }

    public void setExperience(int n) {
        // Set the experience to a new value.
        experience = n;
    }

    public int getExperience() {
        // Return the current experience of the unit.
        return experience;
    }

    public int getInitialArmor() {
        // Return the initial armor value.
        return initialArmor;
    }

    public int getBaseDamage() {
        // Return the base damage value.
        return baseDamage;
    }
}
