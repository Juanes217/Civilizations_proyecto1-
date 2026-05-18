package com.civilization;

import java.util.ArrayList;
import java.util.Random;

public class Enemy implements Variables {

    private ArrayList<MilitaryUnit>[] enemyArmy;

    private static final double[] unitProbabilities = {
        35.0, 25.0, 20.0, 20.0
    };

    public Enemy() {
        initializeEnemyArmy();
    }

    private void initializeEnemyArmy() {
        enemyArmy = new ArrayList[4];
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }
    }

    private void emptyEnemyArmy() {
        for (ArrayList<MilitaryUnit> units : enemyArmy) {
            units.clear();
        }
    }


    public void createEnemyArmy() {
        Random random = new Random();
        boolean armyCreated = false;
        int attempts = 0;
        while ((!armyCreated || !isWithinResourceLimits()) && attempts < 100) {
            attempts++;
            emptyEnemyArmy();
            for (int i = 0; i < unitProbabilities.length; i++) {
                double probability = unitProbabilities[i];
                if (random.nextDouble() < probability / 100) {
                    int numUnits = random.nextInt(100);
                    switch (i) {
                        case 0:
                            addUnits(new Swordsman(), numUnits);
                            break;
                        case 1:
                            addUnits(new Spearman(), numUnits);
                            break;
                        case 2:
                            addUnits(new Crossbow(), numUnits);
                            break;
                        case 3:
                            addUnits(new Cannon(), numUnits);
                            break;
                    }
                }
            }

            armyCreated = hasUnits();
        }
    }

    private void addUnits(MilitaryUnit unit, int count) {
        for (int i = 0; i < count; i++) {
            if (unit instanceof Swordsman) {
                enemyArmy[0].add(new Swordsman());
            } else if (unit instanceof Spearman) {
                enemyArmy[1].add(new Spearman());
            } else if (unit instanceof Crossbow) {
                enemyArmy[2].add(new Crossbow());
            } else if (unit instanceof Cannon) {
                enemyArmy[3].add(new Cannon());
            }
        }
    }


    private boolean hasUnits() {
        for (ArrayList<MilitaryUnit> units : enemyArmy) {
            if (!units.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private boolean isWithinResourceLimits() {
        int woodCost = 0;
        int ironCost = 0;
        int foodCost = 0;

        for (ArrayList<MilitaryUnit> units : enemyArmy) {
            for (MilitaryUnit unit : units) {
                woodCost += unit.getWoodCost();
                ironCost += unit.getIronCost();
                foodCost += unit.getFoodCost();
            }
        }

        return !(woodCost > WOOD_BASE_ENEMY_ARMY * ENEMY_FLEET_INCREASE
                || ironCost > IRON_BASE_ENEMY_ARMY * ENEMY_FLEET_INCREASE
                || foodCost > FOOD_BASE_ENEMY_ARMY * ENEMY_FLEET_INCREASE);
    }

    public void viewThreat() {
        System.out.println("EjÃ©rcito enemigo:");
        for (int i = 0; i < enemyArmy.length; i++) {
            ArrayList<MilitaryUnit> units = enemyArmy[i];
            if (!units.isEmpty()) {
                String unitName = "";
                switch (i) {
                    case 0:
                        unitName = "Swordsman";
                        break;
                    case 1:
                        unitName = "Spearman";
                        break;
                    case 2:
                        unitName = "Crossbow";
                        break;
                    case 3:
                        unitName = "Cannon";
                        break;
                }
                System.out.println(unitName + ": " + units.size());
            }
        }
    }


    public ArrayList<MilitaryUnit>[] getEnemyArmy() {
        return enemyArmy;
    }
}