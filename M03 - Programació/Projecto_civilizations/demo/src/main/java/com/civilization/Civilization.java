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
        this(0, 0, 70000, 70000, 70000, 0, 0, 0, 0, 0, 0, 0);
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

    // --- GESTIÓN DE EDIFICIOS ---

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
    
    // --- PRODUCCIÓN PASIVA ---

    public void producirRecursos() {
        this.food += (this.farm * 20); 
        this.iron += (this.smithy * 15);
        this.wood += (this.carpentry * 15);
        this.mana += (this.magicTower * 5);
    }

    // --- RECOMPENSAS DE BATALLA (AÑADIDO) ---

    /**
     * Suma al tesoro los recursos saqueados tras una victoria militar.
     */
    public void recibirBotinGuerra(int comidaRecuperada, int manaRecuperado) {
        this.food += comidaRecuperada;
        this.mana += manaRecuperado;
        System.out.println("LOG: El imperio ha procesado un botín de " + comidaRecuperada + " comida y " + manaRecuperado + " maná.");
    }

    // --- MEJORAS TECNOLÓGICAS ---

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

    // --- SISTEMA DE RECLUTAMIENTO ---

    private void createUnits(int n, int type, int foodCost, int woodCost, int ironCost, String unitName) throws ResourceException {
        int maxFood = (foodCost <= 0) ? Integer.MAX_VALUE : food / foodCost;
        int maxWood = (woodCost <= 0) ? Integer.MAX_VALUE : wood / woodCost;
        int maxIron = (ironCost <= 0) ? Integer.MAX_VALUE : iron / ironCost;

        int numTropas = Math.min(n, Math.min(maxFood, Math.min(maxWood, maxIron)));
        
        if (numTropas <= 0) {
            throw new ResourceException("Recursos insuficientes para alistar " + unitName);
        }

        for (int i = 0; i < numTropas; i++) {
            switch (type) {
                case 0: army[0].add(new Swordsman()); break;
                case 1: army[1].add(new Spearman()); break;
                case 2: army[2].add(new Crossbow()); break;
                case 3: army[3].add(new Cannon()); break;
                case 4: army[4].add(new ArrowTower()); break;
                case 5: army[5].add(new Catapult()); break;
                case 6: army[6].add(new RocketLauncherTower()); break;
                case 7: army[7].add(new Magician()); break;
                case 8: army[8].add(new Priest()); break;
            }
        }

        food -= numTropas * foodCost;
        wood -= numTropas * woodCost;
        iron -= numTropas * ironCost;

        if (numTropas < n) {
            throw new ResourceException("Solo se pudieron crear " + numTropas + " " + unitName);
        }
    }

    public void newSwordsman(int n) throws ResourceException { createUnits(n, 0, FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN, IRON_COST_SWORDSMAN, "espadachines"); }
    public void newSpearman(int n) throws ResourceException { createUnits(n, 1, FOOD_COST_SPEARMAN, WOOD_COST_SPEARMAN, IRON_COST_SPEARMAN, "lanceros"); }
    public void newCrossbow(int n) throws ResourceException { createUnits(n, 2, FOOD_COST_CROSSBOW, WOOD_COST_CROSSBOW, IRON_COST_CROSSBOW, "ballesteros"); }
    public void newCannon(int n) throws ResourceException { createUnits(n, 3, FOOD_COST_CANNON, WOOD_COST_CANNON, IRON_COST_CANNON, "cañones"); }
    public void newArrowTower(int n) throws ResourceException { createUnits(n, 4, FOOD_COST_ARROWTOWER, WOOD_COST_ARROWTOWER, IRON_COST_ARROWTOWER, "torres de flechas"); }
    public void newCatapult(int n) throws ResourceException { createUnits(n, 5, FOOD_COST_CATAPULT, WOOD_COST_CATAPULT, IRON_COST_CATAPULT, "catapultas"); }
    public void newRocketLauncher(int n) throws ResourceException { createUnits(n, 6, FOOD_COST_ROCKETLAUNCHERTOWER, WOOD_COST_ROCKETLAUNCHERTOWER, IRON_COST_ROCKETLAUNCHERTOWER, "torres de cohetes"); }
    
    public void newMagician(int n) throws BuildingException, ResourceException {
        if (magicTower == 0) throw new BuildingException("Necesitas una Torre Mágica");
        createUnits(n, 7, FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, "magos");
    }
    
    public void newPriest(int n) throws BuildingException, ResourceException {
        if (church == 0) throw new BuildingException("Necesitas una Iglesia");
        createUnits(n, 8, FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, "sacerdotes");
    }
            
    public void printStats() {
        System.out.println("***************************CIVILIZATION STATS***************************");
        System.out.printf("Alimentos: %d | Madera: %d | Hierro: %d | Maná: %d\n", food, wood, iron, mana);
        System.out.printf("Granjas: %d | Herrerías: %d | Carpinterías: %d | Torres: %d | Iglesias: %d\n", 
                          farm, smithy, carpentry, magicTower, church);
        System.out.println("************************************************************************");
    }

    // --- GETTERS Y SETTERS ---
    public int getTechnologyDefense() { return this.technologyDefense; }
    public void setTechnologyDefense(int technologyDefense) { this.technologyDefense = technologyDefense; }
    public int getTechnologyAttack() { return this.technologyAttack; }
    public void setTechnologyAttack(int technologyAttack) { this.technologyAttack = technologyAttack; }
    public int getWood() { return this.wood; }
    public void setWood(int wood) { this.wood = wood; }
    public int getIron() { return this.iron; }
    public void setIron(int iron) { this.iron = iron; }
    public int getFood() { return this.food; }
    public void setFood(int food) { this.food = food; }
    public int getMana() { return this.mana; }
    public void setMana(int mana) { this.mana = mana; }
    public int getMagicTower() { return this.magicTower; }
    public void setMagicTower(int magicTower) { this.magicTower = magicTower; }
    public int getChurch() { return this.church; }
    public void setChurch(int church) { this.church = church; }
    public int getFarm() { return this.farm; }
    public void setFarm(int farm) { this.farm = farm; }
    public int getSmithy() { return this.smithy; }
    public void setSmithy(int smithy) { this.smithy = smithy; }
    public int getCarpentry() { return this.carpentry; }
    public void setCarpentry(int carpentry) { this.carpentry = carpentry; }
    public int getBattles() { return this.battles; }
    public void setBattles(int battles) { this.battles = battles; }
    public ArrayList<MilitaryUnit>[] getArmy() { return this.army; }
}