package Projecto;

public class DefenseUnit implements MilitaryUnit, Variables {
	    int armor;
	    int initialArmor;
	    int baseDamage;
	    int experience;
	    boolean sanctified;

	    public DefenseUnit(int armor, int initialArmor, int baseDamage, int experience, boolean sanctified) {
	        this.armor = armor;
	        this.initialArmor = initialArmor;
	        this.baseDamage = baseDamage;
	        this.experience = experience;
	        this.sanctified = sanctified;
	    }
	    public DefenseUnit(int armor,int baseDamage){
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
	            case "ARROWTOWER":
	                return FOOD_COST_UNITS[4];
	            case "CATAPULT":
	                return FOOD_COST_UNITS[5];
	            case "ROCKETLAUNCHERTOWER":
	                return FOOD_COST_UNITS[6];
	                }
	    return 0;}
	       


	    public int getWoodCost() {
	        // Return the wood cost of creating a new unit.
	        switch (this.getClass().getSimpleName()) {
	            case "ARROWTOWER":
	                return WOOD_COST_UNITS[4];
	            case "CATAPULT":
	                return WOOD_COST_UNITS[5];
	            case "ROCKETLAUNCHERTOWER":
	                return WOOD_COST_UNITS[6];
	                }
	    return 0;}    

	    public int getIronCost() {
	        // Return the iron cost of creating a new unit.
	        switch (this.getClass().getSimpleName()) {
	            case "ARROWTOWER":
	                return IRON_COST_UNITS[4];
	            case "CATAPULT":
	                return IRON_COST_UNITS[5];
	            case "ROCKETLAUNCHERTOWER":
	                return IRON_COST_UNITS[6];
	            }
	        return 0;} 

	    public int getManaCost() {
	        // Return the mana cost of creating a new unit.
	        return 0;
	    }

	    public int getChanceGeneratinWaste() {
	        if (armor <= 0) {
	            switch (this.getClass().getSimpleName()) {
	                case "ARROWTOWER":
	                    return CHANCE_GENERATNG_WASTE_ARROWTOWER;
	                case "CATAPULT":
	                    return CHANCE_GENERATNG_WASTE_CATAPULT;
	                case "ROCKETLAUNCHERTOWER":
	                    return CHANCE_GENERATNG_WASTE_ROCKETLAUNCHERTOWER;
	            }
	        }
	        return 0;
	    }

	    public int getChanceAttackAgain() {
	        // Return the probability of attacking again.
	        switch (this.getClass().getSimpleName()) {
	            case "ARROWTOWER":
	                return CHANCE_ATTACK_AGAIN_ARROWTOWER;
	            case "CATAPULT":
	                return CHANCE_ATTACK_AGAIN_CATAPULT;
	            case "ROCKETLAUNCHERTOWER":
	                return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER;
	            case "Cannon":
	                return CHANCE_ATTACK_AGAIN_CANNON;
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

	    public boolean isSanctified() {
	        // Return the sanctified status of the unit.
	        return sanctified;
	    }

	    public void setSanctified(boolean sanctified) {
	        // Set the sanctified status of the unit.
	        this.sanctified = sanctified;
	    }

}
