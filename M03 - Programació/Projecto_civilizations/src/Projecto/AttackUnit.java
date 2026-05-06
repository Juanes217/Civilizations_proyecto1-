package Projecto;

public class AttackUnit implements MilitaryUnit , Variables {
	 int armor;
	    int initialArmor;
	    int baseDamage;
	    int experience;
	    boolean sanctified;

	    public AttackUnit(int armor, int initialArmor, int baseDamage, int experience, boolean sanctified) {
	        this.armor = armor;
	        this.initialArmor = initialArmor;
	        this.baseDamage = baseDamage;
	        this.experience = experience;
	        this.sanctified = sanctified;
	    }


	    public AttackUnit(int armor, int baseDamage) {
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
	    public int getFoodCost() {
	        // Return the food cost of creating a new unit.
	        switch (this.getClass().getSimpleName()) {
	            case "Swordsman":
	                return FOOD_COST_UNITS[0];
	            case "Spearman":
	                return FOOD_COST_UNITS[1];
	            case "Crossbow":
	                return FOOD_COST_UNITS[2];
	            case "Cannon":
	                return FOOD_COST_UNITS[3];
	        }
	    
	    return 0;}
	       


	    public int getWoodCost() {
	        // Return the wood cost of creating a new unit.
	        switch (this.getClass().getSimpleName()) {
	            case "Swordsman":
	                return WOOD_COST_UNITS[0];
	            case "Spearman":
	                return WOOD_COST_UNITS[1];
	            case "Crossbow":
	                return WOOD_COST_UNITS[2];
	            case "Cannon":
	                return WOOD_COST_UNITS[3];
	        }
	    
	    return 0;}    

	    public int getIronCost() {
	        // Return the iron cost of creating a new unit.
	        switch (this.getClass().getSimpleName()) {
	            case "Swordsman":
	                return IRON_COST_UNITS[0];
	            case "Spearman":
	                return IRON_COST_UNITS[1];
	            case "Crossbow":
	                return IRON_COST_UNITS[2];
	            case "Cannon":
	                return IRON_COST_UNITS[3];
	        }
	    
	    return 0;}    

	    public int getManaCost() {
	        // Return the mana cost of creating a new unit.
	        return 0;
	    }

	    public int getChanceGeneratinWaste() {
	        if (armor <= 0) {
	            switch (this.getClass().getSimpleName()) {
	                case "Swordsman":
	                    return CHANCE_GENERATNG_WASTE_SWORDSMAN;
	                case "Spearman":
	                    return CHANCE_GENERATNG_WASTE_SPEARMAN;
	                case "Crossbow":
	                    return CHANCE_GENERATNG_WASTE_CROSSBOW;
	                case "Cannon":
	                    return CHANCE_GENERATNG_WASTE_CANNON;
	            }
	        }
	        return 0;
	    }

	    public int getChanceAttackAgain() {
	        // Return the probability of attacking again.
	        switch (this.getClass().getSimpleName()) {
	            case "Swordsman":
	                return CHANCE_ATTACK_AGAIN_SWORDSMAN;
	            case "Spearman":
	                return CHANCE_ATTACK_AGAIN_SPEARMAN;
	            case "Crossbow":
	                return CHANCE_ATTACK_AGAIN_CROSSBOW;
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
	    public void SanctifiedBuff(){
	        if (sanctified){
	            baseDamage += baseDamage * (PLUS_ATTACK_UNIT_SANCTIFIED /100);
	            armor += armor * (PLUS_ARMOR_UNIT_SANCTIFIED /100) ;

	        }
	    }
}
