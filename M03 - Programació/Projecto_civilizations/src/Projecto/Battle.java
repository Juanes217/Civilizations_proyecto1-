package com.civilizations;

import java.util.ArrayList;
import java.util.Random;

public class Battle implements Variables {
    private ArrayList<MilitaryUnit>[] civilizationArmy;
    private ArrayList<MilitaryUnit>[] enemyArmy;
    private ArrayList<MilitaryUnit>[][] armies; 
    private StringBuilder battleDevelopment;
    private int[][] initialCostFleet; // [0] civilizacion, [1] enemigo
    private int initialNumberUnitsCivilization;
    private int initialNumberUnitsEnemy;
    private int[] wasteWoodIron; 
    private int enemyDrops;
    private int civilizationDrops;
    private int[][] resourcesLosses; // [0] civilización, [1] enemigo
    private int[][] initialArmies;
    private int[] actualNumberUnitsCivilization;
    private int[] actualNumberUnitsEnemy;
    private Random random = new Random();

    public Battle(ArrayList<MilitaryUnit>[] civilizationArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
        this.civilizationArmy = civilizationArmy;
        this.enemyArmy = enemyArmy;
        
        this.armies = new ArrayList[2][9];
        this.armies[0] = civilizationArmy;
        this.armies[1] = enemyArmy;

        this.battleDevelopment = new StringBuilder();
        this.initialCostFleet = new int[2][3];
        this.wasteWoodIron = new int[2];
        this.resourcesLosses = new int[2][4]; // La columna 3 es la ponderada
        this.initialArmies = new int[2][9];
        this.actualNumberUnitsCivilization = new int[9];
        this.actualNumberUnitsEnemy = new int[9];

        initBattleData();
    }

    private void initBattleData() {
        for (int i = 0; i < 9; i++) {
            // Civilización
            initialArmies[0][i] = armies[0][i].size();
            actualNumberUnitsCivilization[i] = armies[0][i].size();
            initialNumberUnitsCivilization += initialArmies[0][i];
            for (MilitaryUnit u : armies[0][i]) {
                initialCostFleet[0][0] += u.getFoodCost();
                initialCostFleet[0][1] += u.getWoodCost();
                initialCostFleet[0][2] += u.getIronCost();
            }
            // Enemigo
            initialArmies[1][i] = armies[1][i].size();
            actualNumberUnitsEnemy[i] = armies[1][i].size();
            initialNumberUnitsEnemy += initialArmies[1][i];
            for (MilitaryUnit u : armies[1][i]) {
                initialCostFleet[1][0] += u.getFoodCost();
                initialCostFleet[1][1] += u.getWoodCost();
                initialCostFleet[1][2] += u.getIronCost();
            }
        }
    }

    // Lógica de combate (Simplificada para el ejemplo, debes iterar ataques)
    public void simulateBattle() {
        while (!isBattleOver()) {
            // 1. Seleccionar atacante y defensor según probabilidades del PDF
            // 2. Ejecutar fight()
            // 3. Actualizar contadores
            // Nota: Aquí iría tu bucle de simulateRound
        }
    }

    private void fight(MilitaryUnit attacker, MilitaryUnit defender, int sideAttacker, int groupDefender) {
        // Al morir una unidad, sumamos sus costes a resourcesLosses
        // Ejemplo si muere el defensor (que es el enemigo index 1):
        if (defender.getActualArmor() <= 0) {
            resourcesLosses[1][0] += defender.getFoodCost();
            resourcesLosses[1][1] += defender.getWoodCost();
            resourcesLosses[1][2] += defender.getIronCost();
            armies[1][groupDefender].remove(defender);
            enemyDrops++;
        }
    }

    public String getWinner() {
        // Cálculo ponderado según PDF: Hierro + Madera/5 + Comida/10
        // Para evitar decimales, usamos la fórmula de comparación:
        resourcesLosses[0][3] = resourcesLosses[0][2] + (resourcesLosses[0][1] / 5) + (resourcesLosses[0][0] / 10);
        resourcesLosses[1][3] = resourcesLosses[1][2] + (resourcesLosses[1][1] / 5) + (resourcesLosses[1][0] / 10);

        if (resourcesLosses[0][3] < resourcesLosses[1][3]) {
            return "¡CIVILIZACIÓN GANA! (Menos pérdidas)";
        } else {
            return "¡EL ENEMIGO GANA! (Civilización perdió más)";
        }
    }

    private boolean isBattleOver() {
        int currentCiv = 0;
        int currentEnem = 0;
        for(int i=0; i<9; i++) {
            currentCiv += armies[0][i].size();
            currentEnem += armies[1][i].size();
        }
        // Condición del 20% inicial
        return currentCiv <= (initialNumberUnitsCivilization * 0.2) || 
               currentEnem <= (initialNumberUnitsEnemy * 0.2);
    }
}