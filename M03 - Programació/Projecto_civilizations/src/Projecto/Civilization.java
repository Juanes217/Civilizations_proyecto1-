package Projecto;
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
        createUnits(n, 0, FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN, IRON_COST_SWORDSMAN, "Swordsman");
    }

    public void newMagician(int n) throws BuildingException, ResourceException {
        if (magicTower < 1) throw new BuildingException("Necesitas al menos una Torre Mágica");
        createUnits(n, 7, FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, "Magician");
    }

    public void newPriest(int n) throws BuildingException, ResourceException {
        if (church < 1) throw new BuildingException("Necesitas al menos una Iglesia");
        createUnits(n, 8, FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, "Priest");
    }
    
    // Deberías repetir el patrón de newSwordsman para Spearman, Crossbow, etc.

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

    // Getters y Setters...
}