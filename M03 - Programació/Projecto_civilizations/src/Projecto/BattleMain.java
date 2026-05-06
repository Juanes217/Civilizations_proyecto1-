package com.civilizations;

import java.util.ArrayList;

public class BattleMain {
    private ArrayList<MilitaryUnit>[] civilizationArmy;
    private ArrayList<MilitaryUnit>[] enemyArmy;

    public BattleMain(ArrayList<MilitaryUnit>[] civilizationArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
        this.civilizationArmy = civilizationArmy;
        this.enemyArmy = enemyArmy;
    }

    // Este es el método que tenías, está perfecto para ser llamado desde el juego real
    public void startBattle(int battleNumber) {
        Battle battle = new Battle(civilizationArmy, enemyArmy);

        System.out.println("--- INICIANDO LA BATALLA ---");
        battle.simulateBattle();

        System.out.println("DISSENY DE LA BATALLA (PASO A PASO):");
        System.out.println(battle.getBattleDevelopment());
        
        System.out.println("REPORTE FINAL:");
        System.out.println(battle.getBattleReport(battleNumber));
    }

    // AÑADIMOS ESTO para que puedas darle al botón de "Run" y probar que no explote nada
    public static void main(String[] args) {
        // 1. Crear ejércitos de prueba vacíos
        ArrayList<MilitaryUnit>[] civ = new ArrayList[9];
        ArrayList<MilitaryUnit>[] enem = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            civ[i] = new ArrayList<>();
            enem[i] = new ArrayList<>();
        }

        // 2. Añadir algunas unidades de prueba (esto depende de tus otras clases)
        civ[0].add(new Swordsman()); // Asumiendo que tienes la clase Swordsman
        enem[0].add(new Swordsman());

        // 3. Ejecutar
        BattleMain test = new BattleMain(civ, enem);
        test.startBattle(1);
    }
}