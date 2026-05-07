package com.civilization;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements Variables {
    private Civilization civilization;
    private Timer timer;
    private ArrayList<MilitaryUnit>[] enemyArmy;
    private boolean enemyArmyCreated;
    
    // Probabilidades para el ejército enemigo (Swordsman, Spearman, Crossbow, Cannon)
    private static final double[] enemyUnitProbabilities = {35.0, 25.0, 20.0, 20.0};

    public Main() {
        // Inicializamos la civilización con valores base (puedes ajustarlos)
        civilization = new Civilization(0, 0, 10000, 10000, 10000, 0, 0, 0, 0, 0, 0, 0);
        timer = new Timer();
        enemyArmyCreated = false;
        initializeEnemyArmy();
    }

    private void initializeEnemyArmy() {
        enemyArmy = new ArrayList[4];
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }
    }

    // --- TIMERS ---

    private TimerTask taskAttack = new TimerTask() {
        public void run() {
            System.out.println("\n[ALERTA] ¡Un ejército enemigo se aproxima! Batalla en 60 segundos...");
            createEnemyArmy();
            
            new Timer().schedule(new TimerTask() {
                public void run() {
                    // Según el PDF, pasamos el ejército de nuestra civilización y el enemigo
                    // Usamos civilization.getArmy() (debes tener el getter en Civilization)
                    Battle battle = new Battle(civilization.getArmy(), enemyArmy);
                    battle.startBattle();
                    System.out.println("\n[SISTEMA] La batalla ha terminado. Revisa los reportes.");
                }
            }, 60000); 
        }
    };

    private TimerTask taskResources = new TimerTask() {
        public void run() {
            // Lógica de generación de recursos (puedes mover esto a un método en Civilization)
            civilization.setFood(civilization.getFood() + CIVILIZATION_FOOD_GENERATED);
            if (civilization.getFarm() >= 1) {
                civilization.setFood(civilization.getFood() + (civilization.getFarm() * CIVILIZATION_FOOD_GENERATED_PER_FARM));
            }
            civilization.setWood(civilization.getWood() + CIVILIZATION_WOOD_GENERATED);
            if (civilization.getCarpentry() >= 1) {
                civilization.setWood(civilization.getWood() + (civilization.getCarpentry() * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY));
            }
            civilization.setIron(civilization.getIron() + CIVILIZATION_IRON_GENERATED);
            if (civilization.getSmithy() >= 1) {
                civilization.setIron(civilization.getIron() + (civilization.getSmithy() * CIVILIZATION_IRON_GENERATED_PER_SMITHY));
            }
            if (civilization.getMagicTower() >= 1) {
                civilization.setMana(civilization.getMana() + (civilization.getMagicTower() * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER));
            }
        }
    };

    // --- LÓGICA DE UNIDADES Y EDIFICIOS ---

    public void crearUnidades() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- ENTRENAR UNIDADES ---");
            System.out.println("1. Swordsman | 2. Spearman | 3. Crossbow | 4. Cannon");
            System.out.println("5. Arrow Tower | 6. Catapult | 7. Rocket Launcher");
            System.out.println("8. Magician | 9. Priest | 10. Volver");
            int opcion = sc.nextInt();
            if (opcion == 10) break;

            System.out.print("Cantidad: ");
            int cant = sc.nextInt();

            try {
                switch (opcion) {
                    case 1: civilization.newSwordsman(cant); break;
                    case 2: civilization.newSpearman(cant); break;
                    case 3: civilization.newCrossbow(cant); break;
                    case 4: civilization.newCannon(cant); break;
                    case 5: civilization.newArrowTower(cant); break;
                    case 6: civilization.newCatapult(cant); break;
                    case 7: civilization.newRocketLauncher(cant); break;
                    case 8: civilization.newMagician(cant); break;
                    case 9: civilization.newPriest(cant); break;
                }
                System.out.println("¡Unidades entrenadas con éxito!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void crearEdificios() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- CONSTRUIR ---");
        System.out.println("1. Granja | 2. Herrería | 3. Carpintería | 4. Torre Mágica | 5. Iglesia | 6. Volver");
        int op = sc.nextInt();
        try {
            switch(op) {
                case 1: civilization.newFarm(); break;
                case 2: civilization.newSmithy(); break;
                case 3: civilization.newCarpentry(); break;
                case 4: civilization.newMagicTower(); break;
                case 5: civilization.newChurch(); break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- ENEMIGO ---

    public void createEnemyArmy() {
        initializeEnemyArmy();
        Random r = new Random();
        // Lógica simplificada para crear enemigos basados en probabilidades del PDF
        for (int i = 0; i < enemyUnitProbabilities.length; i++) {
            if (r.nextDouble() * 100 < enemyUnitProbabilities[i]) {
                int num = r.nextInt(50) + 1;
                for (int j = 0; j < num; j++) {
                    switch(i) {
                        case 0: enemyArmy[0].add(new Swordsman()); break;
                        case 1: enemyArmy[1].add(new Spearman()); break;
                        case 2: enemyArmy[2].add(new Crossbow()); break;
                        case 3: enemyArmy[3].add(new Cannon()); break;
                    }
                }
            }
        }
        enemyArmyCreated = true;
    }

    // --- MAIN ---

    public static void main(String[] args) {
        Main game = new Main();
        Scanner sc = new Scanner(System.in);
        
        // Iniciamos timers
        game.timer.schedule(game.taskResources, 0, 30000); // Recursos cada 30s
        game.timer.schedule(game.taskAttack, 300000, 180000); // Primer ataque a los 5m

        while (true) {
            System.out.println("\n========= CIVILIZATIONS =========");
            System.out.println("1. Entrenar Unidades");
            System.out.println("2. Construir");
            System.out.println("3. Mejorar Tecnología");
            System.out.println("4. Reportes de Batalla");
            System.out.println("5. Ver Amenaza Enemiga");
            System.out.println("6. Ver Recursos");
            System.out.println("7. Ver Mi Ejército");
            System.out.println("8. Salir");
            System.out.print("Opción: ");
            int op = sc.nextInt();

            if (op == 8) System.exit(0);

            switch(op) {
                case 1: game.crearUnidades(); break;
                case 2: game.crearEdificios(); break;
                case 3: /* llamar a mejorar tecnologia */ break;
                case 4: /* llamar a battle.getBattleReport() */ break;
                case 5: if(game.enemyArmyCreated) game.viewThreat(); else System.out.println("No hay amenazas detectadas."); break;
                case 6: game.mostrarRecursos(); break;
                case 7: game.civilization.printStats(); break;
            }
        }
    }

    public void mostrarRecursos() {
        System.out.printf("Comida: %d | Madera: %d | Hierro: %d | Maná: %d\n", 
            civilization.getFood(), civilization.getWood(), civilization.getIron(), civilization.getMana());
    }

    public void viewThreat() {
        System.out.println("--- EJÉRCITO ENEMIGO DETECTADO ---");
        System.out.println("Swordsman: " + enemyArmy[0].size());
        System.out.println("Spearman: " + enemyArmy[1].size());
        System.out.println("Crossbow: " + enemyArmy[2].size());
        System.out.println("Cannon: " + enemyArmy[3].size());
    }
}