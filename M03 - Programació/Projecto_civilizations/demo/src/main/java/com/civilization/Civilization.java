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

    // Variables para el coste dinámico de tecnología (según el enunciado)
    private double currentUpgradeDefenseIronCost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST;
    private double currentUpgradeAttackIronCost = UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST;

    // Constructor corregido: Ahora asigna los valores recibidos
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

    // --- MÉTODOS DE CONSTRUCCIÓN ---

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

    // --- MÉTODOS DE TECNOLOGÍA (Lógica incremental del 10%) ---

    public void upgradeTechnologyDefense() throws ResourceException {
        if (this.iron >= currentUpgradeDefenseIronCost) {
            this.iron -= currentUpgradeDefenseIronCost;
            this.technologyDefense++;
            // Incrementamos el coste para la siguiente vez un 10%
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

    // --- MÉTODOS DE UNIDADES (Lógica: crear lo que se pueda y lanzar excepción) ---

    private void createUnits(int n, int type, int fCost, int wCost, int iCost, String name) throws ResourceException {
        int maxByFood = (fCost > 0) ? this.food / fCost : Integer.MAX_VALUE;
        int maxByWood = (wCost > 0) ? this.wood / wCost : Integer.MAX_VALUE;
        int maxByIron = (iCost > 0) ? this.iron / iCost : Integer.MAX_VALUE;

        int canCreate = Math.min(n, Math.min(maxByFood, Math.min(maxByWood, maxByIron)));

        for (int i = 0; i < canCreate; i++) {
            switch(type) {
                case 0: army[0].add(new Swordsman()); break;
                case 1: army[1].add(new Spearman()); break;
                case 2: army[2].add(new Crossbow()); break;
                case 3: army[3].add(new Cannon()); break;
                case 4: army[4].add(new ArrowTower()); break;
                case 5: army[5].add(new Catapult()); break;
                case 6: army[6].add(new RocketLauncherTower()); break;
                case 7: army[7].add(new Magician()); break;
                case 8: army[8].add(new Priest(0, 0)); break;            }
        }

        this.food -= canCreate * fCost;
        this.wood -= canCreate * wCost;
        this.iron -= canCreate * iCost;

        if (canCreate < n) {
            throw new ResourceException("Solo se han añadido " + canCreate + " " + name);
        }
    }

public void newSwordsman(int n) throws ResourceException {
        int foodCost = FOOD_COST_SWORDSMAN * n;
        int woodCost = WOOD_COST_SWORDSMAN * n;
        int ironCost = IRON_COST_SWORDSMAN * n;
    
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You dont have enough resources");
        } else {
            // CALCULAR MAX SWORDSMAN
            int maxSwordsmenFood = food / FOOD_COST_SWORDSMAN;
            int maxSwordsmenWood = wood / WOOD_COST_SWORDSMAN;
            int maxSwordsmenIron = iron / IRON_COST_SWORDSMAN;
            int maxSwordsmen = Math.min(maxSwordsmenFood, Math.min(maxSwordsmenWood, maxSwordsmenIron));
    
            int trainedSwordsmen = Math.min(n, maxSwordsmen);
    
            // RESTAR RECURSOS
            int foodCostForTrained = FOOD_COST_SWORDSMAN * trainedSwordsmen;
            int woodCostForTrained = WOOD_COST_SWORDSMAN * trainedSwordsmen;
            int ironCostForTrained = IRON_COST_SWORDSMAN * trainedSwordsmen;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            // AÑADIR A LISTA[0]
            for (int i = 0; i < trainedSwordsmen; i++) {
                army[0].add(new Swordsman());
            }
    
            System.out.println("You've trained " + trainedSwordsmen + " swordsmen.");
        }
    }

    
    
    
    public void newSpearman(int n) throws ResourceException {
        int foodCost = FOOD_COST_SPEARMAN * n;
        int woodCost = WOOD_COST_SPEARMAN * n;
        int ironCost = IRON_COST_SPEARMAN * n;
    
        // Verificar si se tienen suficientes recursos para al menos un spearman
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You dont have enough resources");
                } else {
            // Calcular el número máximo de spearmen que se pueden entrenar con los recursos disponibles
            int maxSpearmenFood = food / FOOD_COST_SPEARMAN;
            int maxSpearmenWood = wood / WOOD_COST_SPEARMAN;
            int maxSpearmenIron = iron / IRON_COST_SPEARMAN;
            int maxSpearmen = Math.min(maxSpearmenFood, Math.min(maxSpearmenWood, maxSpearmenIron));
    
            // Entrenar la cantidad máxima posible de spearmen
            int trainedSpearmen = Math.min(n, maxSpearmen);
    
            // Reducir los recursos necesarios
            int foodCostForTrained = FOOD_COST_SPEARMAN * trainedSpearmen;
            int woodCostForTrained = WOOD_COST_SPEARMAN * trainedSpearmen;
            int ironCostForTrained = IRON_COST_SPEARMAN * trainedSpearmen;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            // Añadir los spearmen al ejército
            for (int i = 0; i < trainedSpearmen; i++) {
                army[1].add(new Spearman()); // Asumiendo que 1 es la posición para spearmen en el ejército
            }
    
            System.out.println("You've trained " + trainedSpearmen + " spearmen.");
        }
    }
    
    public void newCrossbow(int n) throws ResourceException {
        int foodCost = FOOD_COST_CROSSBOW * n;
        int woodCost = WOOD_COST_CROSSBOW * n;
        int ironCost = IRON_COST_CROSSBOW * n;
    
        // Verificar si se tienen suficientes recursos
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You dont have enough resources");
        } else {
            // Calcular el número máximo de crossbows que se pueden entrenar con los recursos disponibles
            int maxCrossbowsFood = food / FOOD_COST_CROSSBOW;
            int maxCrossbowsWood = wood / WOOD_COST_CROSSBOW;
            int maxCrossbowsIron = iron / IRON_COST_CROSSBOW;
            int maxCrossbows = Math.min(maxCrossbowsFood, Math.min(maxCrossbowsWood, maxCrossbowsIron));
    
            // Entrenar la cantidad máxima posible de crossbows
            int trainedCrossbows = Math.min(n, maxCrossbows);
    
            // Reducir los recursos necesarios
            int foodCostForTrained = FOOD_COST_CROSSBOW * trainedCrossbows;
            int woodCostForTrained = WOOD_COST_CROSSBOW * trainedCrossbows;
            int ironCostForTrained = IRON_COST_CROSSBOW * trainedCrossbows;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            // Añadir los crossbows al ejército
            for (int i = 0; i < trainedCrossbows; i++) {
                army[2].add(new Crossbow()); // Asumiendo que 2 es la posición para crossbows en el ejército
            }
    
            System.out.println("You've trained " + trainedCrossbows + " crossbows.");
        }
    }
    
    // Implementa métodos similares para los otros tipos de unidades
    
    public void newCannon(int n) throws ResourceException {
        int foodCost = FOOD_COST_CANNON * n;
        int woodCost = WOOD_COST_CANNON * n;
        int ironCost = IRON_COST_CANNON * n;
    
        // Verificar si se tienen suficientes recursos
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You don't have enough resources");
        } else {
            // Calcular el número máximo de cannons que se pueden entrenar con los recursos disponibles
            int maxCannonsFood = food / FOOD_COST_CANNON;
            int maxCannonsWood = wood / WOOD_COST_CANNON;
            int maxCannonsIron = iron / IRON_COST_CANNON;
            int maxCannons = Math.min(maxCannonsFood, Math.min(maxCannonsWood, maxCannonsIron));
    
            // Entrenar la cantidad máxima posible de cannons
            int trainedCannons = Math.min(n, maxCannons);
    
            // Reducir los recursos necesarios
            int foodCostForTrained = FOOD_COST_CANNON * trainedCannons;
            int woodCostForTrained = WOOD_COST_CANNON * trainedCannons;
            int ironCostForTrained = IRON_COST_CANNON * trainedCannons;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            // Añadir los cannons al ejército
            for (int i = 0; i < trainedCannons; i++) {
                army[3].add(new Cannon()); // Asumiendo que 3 es la posición para cannons en el ejército
            }
    
            System.out.println("You've trained " + trainedCannons + " cannons.");
        }
    }
    public void newArrowTower(int n) throws ResourceException {
        int foodCost = FOOD_COST_ARROWTOWER * n;
        int woodCost = WOOD_COST_ARROWTOWER * n;
        int ironCost = IRON_COST_ARROWTOWER * n;
    
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You don't have enough resources");
        } else {
            int maxArrowTowersFood = food / FOOD_COST_ARROWTOWER;
            int maxArrowTowersWood = wood / WOOD_COST_ARROWTOWER;
            int maxArrowTowersIron = iron / IRON_COST_ARROWTOWER;
            int maxArrowTowers = Math.min(maxArrowTowersFood, Math.min(maxArrowTowersWood, maxArrowTowersIron));
    
            int trainedArrowTowers = Math.min(n, maxArrowTowers);
    
            int foodCostForTrained = FOOD_COST_ARROWTOWER * trainedArrowTowers;
            int woodCostForTrained = WOOD_COST_ARROWTOWER * trainedArrowTowers;
            int ironCostForTrained = IRON_COST_ARROWTOWER * trainedArrowTowers;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            for (int i = 0; i < trainedArrowTowers; i++) {
                army[4].add(new ArrowTower());
            }
    
            System.out.println("You've trained " + trainedArrowTowers + " arrow towers.");
        }
    }
    
    public void newCatapult(int n) throws ResourceException {
        int foodCost = FOOD_COST_CATAPULT * n;
        int woodCost = WOOD_COST_CATAPULT * n;
        int ironCost = IRON_COST_CATAPULT * n;
    
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You don't have enough resources");
        } else {
            int maxCatapultsFood = food / FOOD_COST_CATAPULT;
            int maxCatapultsWood = wood / WOOD_COST_CATAPULT;
            int maxCatapultsIron = iron / IRON_COST_CATAPULT;
            int maxCatapults = Math.min(maxCatapultsFood, Math.min(maxCatapultsWood, maxCatapultsIron));
    
            int trainedCatapults = Math.min(n, maxCatapults);
    
            int foodCostForTrained = FOOD_COST_CATAPULT * trainedCatapults;
            int woodCostForTrained = WOOD_COST_CATAPULT * trainedCatapults;
            int ironCostForTrained = IRON_COST_CATAPULT * trainedCatapults;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            for (int i = 0; i < trainedCatapults; i++) {
                army[5].add(new Catapult());
            }
    
            System.out.println("You've trained " + trainedCatapults + " catapults.");
        }
    }
    
    public void newRocketLauncher(int n) throws ResourceException {
        int foodCost = FOOD_COST_ROCKETLAUNCHERTOWER * n;
        int woodCost = WOOD_COST_ROCKETLAUNCHERTOWER * n;
        int ironCost = IRON_COST_ROCKETLAUNCHERTOWER * n;
    
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You don't have enough resources");
        } else {
            int maxROCKETLAUNCHERTOWERsFood = food / FOOD_COST_ROCKETLAUNCHERTOWER;
            int maxROCKETLAUNCHERTOWERsWood = wood / WOOD_COST_ROCKETLAUNCHERTOWER;
            int maxROCKETLAUNCHERTOWERsIron = iron / IRON_COST_ROCKETLAUNCHERTOWER;
            int maxROCKETLAUNCHERTOWERs = Math.min(maxROCKETLAUNCHERTOWERsFood, Math.min(maxROCKETLAUNCHERTOWERsWood, maxROCKETLAUNCHERTOWERsIron));
    
            int trainedROCKETLAUNCHERTOWERs = Math.min(n, maxROCKETLAUNCHERTOWERs);
    
            int foodCostForTrained = FOOD_COST_ROCKETLAUNCHERTOWER * trainedROCKETLAUNCHERTOWERs;
            int woodCostForTrained = WOOD_COST_ROCKETLAUNCHERTOWER * trainedROCKETLAUNCHERTOWERs;
            int ironCostForTrained = IRON_COST_ROCKETLAUNCHERTOWER * trainedROCKETLAUNCHERTOWERs;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            for (int i = 0; i < trainedROCKETLAUNCHERTOWERs; i++) {
                army[6].add(new RocketLauncherTower());
            }
    
            System.out.println("You've trained " + trainedROCKETLAUNCHERTOWERs + " rocket launchers.");
        }
    }
    
    public void newMagician(int n) throws BuildingException, ResourceException {
        int foodCost = FOOD_COST_MAGICIAN * n;
        int woodCost = WOOD_COST_MAGICIAN * n;
        int ironCost = IRON_COST_MAGICIAN * n;
        if (magicTower == 0){
            throw new BuildingException("You need a magic tower");
        }
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You don't have enough resources");
        } else {
            int maxMagiciansFood = food / FOOD_COST_MAGICIAN;
            int maxMagiciansWood = wood / WOOD_COST_MAGICIAN;
            int maxMagiciansIron = iron / IRON_COST_MAGICIAN;
            int maxMagicians = Math.min(maxMagiciansFood, Math.min(maxMagiciansWood, maxMagiciansIron));
    
            int trainedMagicians = Math.min(n, maxMagicians);
    
            int foodCostForTrained = FOOD_COST_MAGICIAN * trainedMagicians;
            int woodCostForTrained = WOOD_COST_MAGICIAN * trainedMagicians;
            int ironCostForTrained = IRON_COST_MAGICIAN * trainedMagicians;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            for (int i = 0; i < trainedMagicians; i++) {
                army[7].add(new Magician());
            }
    
            System.out.println("You've trained " + trainedMagicians + " magicians.");
        }
    }
    
    public void newPriest(int n) throws BuildingException, ResourceException {
        int foodCost = FOOD_COST_PRIEST * n;
        int woodCost = WOOD_COST_PRIEST * n;
        int ironCost = IRON_COST_PRIEST * n;
        if (church == 0){
            throw new BuildingException("You don't have a church.");
        }
        if (food < foodCost || wood < woodCost || iron < ironCost) {
            throw new ResourceException("You don't have enough resources");
        } else {
            int maxPriestsFood = food / FOOD_COST_PRIEST;
            int maxPriestsWood = wood / WOOD_COST_PRIEST;
            int maxPriestsIron = iron / IRON_COST_PRIEST;
            int maxPriests = Math.min(maxPriestsFood, Math.min(maxPriestsWood, maxPriestsIron));
    
            int trainedPriests = Math.min(n, maxPriests);
    
            int foodCostForTrained = FOOD_COST_PRIEST * trainedPriests;
            int woodCostForTrained = WOOD_COST_PRIEST * trainedPriests;
            int ironCostForTrained = IRON_COST_PRIEST * trainedPriests;
            food -= foodCostForTrained;
            wood -= woodCostForTrained;
            iron -= ironCostForTrained;
    
            for (int i = 0; i < trainedPriests; i++) {
                army[8].add(new Priest());
            }
    
            System.out.println("You've trained " + trainedPriests + " priests.");
        }
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
}