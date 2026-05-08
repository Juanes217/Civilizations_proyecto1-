package com.civilization;
import java.util.ArrayList;

public class Civilization implements Variables {

    private int technologyDefense;
    private int technologyAttack;
    private int wood;
    private int iron;
    private int food;
    private int mana;
    private int magicTower;
    private int church;
    private int farm;
    private int smithy;
    private int carpentry;
    private int battles;
    private ArrayList<MilitaryUnit>[] army;

    private double currentUpgradeDefenseIronCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST;
    private double currentUpgradeAttackIronCost = UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST;
    public Civilization() {
    this(0, 0, 10000, 10000, 10000, 0, 0, 0, 0, 0, 0, 0);
}
    public Civilization(int technologyDefense, int technologyAttack, int wood, int iron, int food, int mana, int magicTower, int church, int farm, int smithy, int carpentry, int battles) {
        this.technologyDefense = technologyDefense;
        this.technologyAttack = technologyAttack;
        this.wood = wood;
        this.iron = iron;
        this.food = food;
        this.mana = mana;
        this.magicTower = magicTower;
        this.church = church;
        this.farm = farm;
        this.smithy = smithy;
        this.carpentry = carpentry;
        this.battles = battles;
        

        this.army = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            this.army[i] = new ArrayList<MilitaryUnit>();
        }
    }


    public void newFarm() throws ResourceException {
        if (food < FOOD_COST_FARM || wood < WOOD_COST_FARM || iron < IRON_COST_FARM) {
            throw new ResourceException("No tienes recursos para una Granja");
        }
        this.farm++;
        this.food -= FOOD_COST_FARM;
        this.wood -= WOOD_COST_FARM;
        this.iron -= IRON_COST_FARM;
    }

    public void newSmithy() throws ResourceException {
        if (food < FOOD_COST_SMITHY || wood < WOOD_COST_SMITHY || iron < IRON_COST_SMITHY) {
            throw new ResourceException("No tienes recursos para una Herrería");
        }
        this.smithy++;
        this.food -= FOOD_COST_SMITHY;
        this.wood -= WOOD_COST_SMITHY;
        this.iron -= IRON_COST_SMITHY;
    }

    public void newCarpentry() throws ResourceException {
        if (food < FOOD_COST_CARPENTRY || wood < WOOD_COST_CARPENTRY || iron < IRON_COST_CARPENTRY) {
            throw new ResourceException("No tienes recursos para una Carpintería");
        }
        this.carpentry++;
        this.food -= FOOD_COST_CARPENTRY;
        this.wood -= WOOD_COST_CARPENTRY;
        this.iron -= IRON_COST_CARPENTRY;
    }

    public void newMagicTower() throws ResourceException {
        if (food < FOOD_COST_MAGICTOWER || wood < WOOD_COST_MAGICTOWER || iron < IRON_COST_MAGICTOWER) {
            throw new ResourceException("No tienes recursos para una Torre Mágica");
        }
        this.magicTower++;
        this.food -= FOOD_COST_MAGICTOWER;
        this.wood -= WOOD_COST_MAGICTOWER;
        this.iron -= IRON_COST_MAGICTOWER;
    }

    public void newChurch() throws ResourceException {
        if (food < FOOD_COST_CHURCH || wood < WOOD_COST_CHURCH || iron < IRON_COST_CHURCH || mana < MANA_COST_CHURCH) {
            throw new ResourceException("No tienes recursos para una Iglesia");
        }
        this.church++;
        this.food -= FOOD_COST_CHURCH;
        this.wood -= WOOD_COST_CHURCH;
        this.iron -= IRON_COST_CHURCH;
        this.mana -= MANA_COST_CHURCH;
    }


    public void upgradeTechnologyDefense() throws ResourceException {
        if (this.iron >= currentUpgradeDefenseIronCost) {
            this.iron -= currentUpgradeDefenseIronCost;
            this.technologyDefense++;
            this.currentUpgradeDefenseIronCost += (this.currentUpgradeDefenseIronCost * 0.10);
            System.out.println("Defensa mejorada al nivel " + technologyDefense);
        } else {
            throw new ResourceException("Falta hierro para mejorar defensa. Necesario: " + (int)currentUpgradeDefenseIronCost);
        }
    }

    public void upgradeTechnologyAttack() throws ResourceException {
        if (this.iron >= currentUpgradeAttackIronCost) {
            this.iron -= currentUpgradeAttackIronCost;
            this.technologyAttack++;
            this.currentUpgradeAttackIronCost += (this.currentUpgradeAttackIronCost * 0.10);
            System.out.println("Ataque mejorado al nivel " + technologyAttack);
        } else {
            throw new ResourceException("Falta hierro para mejorar ataque. Necesario: " + (int)currentUpgradeAttackIronCost);
        }
    }


    private void createUnits(int n,int type,int foodCost,int woodCost,int ironCost,String unitName) throws ResourceException {
        int maxFood = food / foodCost;
        int maxWood = wood / woodCost;
        int maxIron = iron / ironCost;
        int numTropas = Math.min(n,Math.min(maxFood, Math.min(maxWood, maxIron)));
        if (numTropas <= 0) {
            throw new ResourceException("Not enough resources for " + unitName);
        }
        for (int i = 0; i < numTropas; i++) {
            switch (type) {
                case 0:
                    army[0].add(new Swordsman());
                    break;
                case 1:
                    army[1].add(new Spearman());
                    break;

                case 2:
                    army[2].add(new Crossbow());
                    break;

                case 3:
                    army[3].add(new Cannon());
                    break;

                case 4:
                    army[4].add(new ArrowTower());
                    break;

                case 5:
                    army[5].add(new Catapult());
                    break;

                case 6:
                    army[6].add(new RocketLauncherTower());
                    break;

                case 7:
                    army[7].add(new Magician());
                    break;

                case 8:
                    army[8].add(new Priest());
                    break;
            }
        }
        food -= numTropas * foodCost;
        wood -= numTropas * woodCost;
        iron -= numTropas * ironCost;

        System.out.println("You've trained " +numTropas +" " +unitName);
        if (numTropas < n) {
            throw new ResourceException("Only " + numTropas + " " + unitName + " created");
            }
        }

    public void newSwordsman(int n) throws ResourceException {
        createUnits(n, 0, FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN, IRON_COST_SWORDSMAN, "swordsmen");
    }
    public void newSpearman(int n) throws ResourceException {
        createUnits(n, 1, FOOD_COST_SPEARMAN, WOOD_COST_SPEARMAN, IRON_COST_SPEARMAN, "spearmen");
    }
    public void newCrossbow(int n) throws ResourceException {
        createUnits(n, 2, FOOD_COST_CROSSBOW, WOOD_COST_CROSSBOW, IRON_COST_CROSSBOW, "crossbows");
    }
    public void newCannon(int n) throws ResourceException {
        createUnits(n, 3, FOOD_COST_CANNON, WOOD_COST_CANNON, IRON_COST_CANNON, "cannons");
    }
    public void newArrowTower(int n) throws ResourceException {
        createUnits(n, 4, FOOD_COST_ARROWTOWER, WOOD_COST_ARROWTOWER, IRON_COST_ARROWTOWER, "arrow towers");
    }
    public void newCatapult(int n) throws ResourceException {
        createUnits(n, 5, FOOD_COST_CATAPULT, WOOD_COST_CATAPULT, IRON_COST_CATAPULT, "catapults");
    }
    public void newRocketLauncher(int n) throws ResourceException {
        createUnits(n, 6, FOOD_COST_ROCKETLAUNCHERTOWER, WOOD_COST_ROCKETLAUNCHERTOWER, IRON_COST_ROCKETLAUNCHERTOWER, "rocket launchers");
    }
    public void newMagician(int n) throws BuildingException, ResourceException {
        if (magicTower == 0) throw new BuildingException("You need a magic tower");
        createUnits(n, 7, FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, "magicians");
    }
    public void newPriest(int n) throws BuildingException, ResourceException {
        if (church == 0) throw new BuildingException("You need a church");
        createUnits(n, 8, FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, "priests");
    }
            
    
    
      

    // --- PRINT STATS ---

    public void printStats() {
        System.out.println("***************************CIVILIZATION STATS***************************");
        System.out.println("--------------------------------TECHNOLOGY------------------------------");
        System.out.printf("Attack: %d | Defense: %d\n", technologyAttack, technologyDefense);
        System.out.println("--------------------------------BUILDINGS-------------------------------");
        System.out.printf("Farm: %d | Smithy: %d | Carpentry: %d | Magic Tower: %d | Church: %d\n", 
                          farm, smithy, carpentry, magicTower, church);
        System.out.println("---------------------------------ARMY-----------------------------------");
        System.out.printf("Swordsman: %d | Spearman: %d | Crossbow: %d | Cannon: %d\n", 
                          army[0].size(), army[1].size(), army[2].size(), army[3].size());
        System.out.printf("Arrow Tower: %d | Catapult: %d | Rocket Launcher: %d\n", 
                          army[4].size(), army[5].size(), army[6].size());
        System.out.printf("Magician: %d | Priest: %d\n", army[7].size(), army[8].size());
        System.out.println("-------------------------------RESOURCES--------------------------------");
        System.out.printf("Food: %d | Wood: %d | Iron: %d | Mana: %d\n", food, wood, iron, mana);
        System.out.println("************************************************************************");
    }
     public int getTechnologyDefense() {
        return this.technologyDefense;
    }

    public void setTechnologyDefense(int technologyDefense) {
        this.technologyDefense = technologyDefense;
    }

    public int getTechnologyAttack() {
        return this.technologyAttack;
    }

    public void setTechnologyAttack(int technologyAttack) {
        this.technologyAttack = technologyAttack;
    }

    public int getWood() {
        return this.wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getIron() {
        return this.iron;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }

    public int getFood() {
        return this.food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMagicTower() {
        return this.magicTower;
    }

    public void setMagicTower(int magicTower) {
        this.magicTower = magicTower;
    }

    public int getChurch() {
        return this.church;
    }

    public void setChurch(int church) {
        this.church = church;
    }

    public int getFarm() {
        return this.farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public int getSmithy() {
        return this.smithy;
    }

    public void setSmithy(int smithy) {
        this.smithy = smithy;
    }

    public int getCarpentry() {
        return this.carpentry;
    }

    public void setCarpentry(int carpentry) {
        this.carpentry = carpentry;
    }

    public int getBattles() {
        return this.battles;
    }

    public void setBattles(int battles) {
        this.battles = battles;
    }
    public ArrayList<MilitaryUnit>[] getArmy() {
        return this.army;
    }
}