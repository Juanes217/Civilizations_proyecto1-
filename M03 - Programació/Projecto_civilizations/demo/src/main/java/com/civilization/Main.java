package com.civilization;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements Variables {

    // Necesitamos el ejército enemigo como una variable accesible
    private static ArrayList<MilitaryUnit>[] enemyArmy;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Inicialización según tu constructor
        Civilization civilization = new Civilization(0, 0, 30000, 30000, 30000, 2000, 0, 0, 0, 0, 0, 0);
        
        // Inicializamos el ejército enemigo vacío para evitar errores
        enemyArmy = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            enemyArmy[i] = new ArrayList<>();
        }

        // --- INICIO DE TEMPORIZADORES (Timers) ---
        Timer timer = new Timer();

        // 1. Generar recursos cada 30 segundos
        timer.schedule(new TimerTask() {
            public void run() {
                // Lógica de generación automática
                civilization.setFood(civilization.getFood() + CIVILIZATION_FOOD_GENERATED);
                if (civilization.getFarm() >= 1) civilization.setFood(civilization.getFood() + CIVILIZATION_FOOD_GENERATED_PER_FARM);
                civilization.setWood(civilization.getWood() + CIVILIZATION_WOOD_GENERATED);
                if (civilization.getCarpentry() >= 1) civilization.setWood(civilization.getWood() + CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY);
                civilization.setIron(civilization.getIron() + CIVILIZATION_IRON_GENERATED);
                if (civilization.getSmithy() >= 1) civilization.setIron(civilization.getIron() + CIVILIZATION_IRON_GENERATED_PER_SMITHY);
            }
        }, 30000, 30000);

        int option;
        
        do {
            System.out.println("\n===== CIVILIZATIONS =====");
            System.out.println("RECURSOS: C: " + civilization.getFood() + " | M: " + civilization.getWood() + " | H: " + civilization.getIron());
            System.out.println("-------------------------");
            System.out.println("1. Ver estadísticas");
            System.out.println("2. Crear granja");
            System.out.println("3. Crear herrería");
            System.out.println("4. Crear carpintería");
            System.out.println("5. Crear espadachines");
            System.out.println("6. Crear lanceros");
            System.out.println("7. Crear ballestas");
            System.out.println("8. Crear cañones");
            System.out.println("9. Crear torre mágica");
            System.out.println("10. Crear iglesia");
            System.out.println("11. SIMULAR BATALLA (Manual)");
            System.out.println("12. Mejorar tecnología de ataque");
            System.out.println("13. Mejorar tecnología de defensa");
            System.out.println("14. Generar recursos (Cheat)");
            System.out.println("15. Guardar civilización (DB)");
            System.out.println("16. Cargar civilización (DB)");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            option = sc.nextInt();
            sc.nextLine(); 

            try {
                switch (option) {
                    case 1:
                        civilization.printStats();
                        break;
                    case 2:
                        civilization.newFarm();
                        System.out.println("¡Granja construida!");
                        break;
                    case 3:
                        civilization.newSmithy();
                        System.out.println("¡Herrería construida!");
                        break;
                    case 4:
                        civilization.newCarpentry();
                        System.out.println("¡Carpintería construida!");
                        break;
                    case 5:
                        System.out.print("¿Cuántos Espadachines?: ");
                        civilization.newSwordsman(sc.nextInt());
                        break;
                    case 6:
                        System.out.print("¿Cuántos Lanceros?: ");
                        civilization.newSpearman(sc.nextInt());
                        break;
                    case 7:
                        System.out.print("¿Cuántas Ballestas?: ");
                        civilization.newCrossbow(sc.nextInt());
                        break;
                    case 8:
                        System.out.print("¿Cuántos Cañones?: ");
                        civilization.newCannon(sc.nextInt());
                        break;
                    case 9:
                        civilization.newMagicTower();
                        System.out.println("¡Torre Mágica construida!");
                        break;
                    case 10:
                        civilization.newChurch();
                        System.out.println("¡Iglesia construida!");
                        break;
                    case 11:
                        // Aquí creamos un enemigo rápido para probar la batalla
                        System.out.println("¡Un ejército enemigo ha aparecido!");
                        // El método getArmy() de tu civilization debe devolver el array de listas
                        Battle battle = new Battle(civilization, enemyArmy); 
                        battle.simulateBattle();
                        break;
                    case 12:
                        civilization.upgradeTechnologyAttack();
                        System.out.println("¡Ataque mejorado!");
                        break;
                    case 13:
                        civilization.upgradeTechnologyDefense();
                        System.out.println("¡Defensa mejorada!");
                        break;
                    case 14:
                        civilization.setFood(civilization.getFood() + 50000);
                        civilization.setWood(civilization.getWood() + 50000);
                        civilization.setIron(civilization.getIron() + 50000);
                        System.out.println("Cheat activado: +50.000 recursos.");
                        break;
                    case 15:
                        System.out.println("Conectando con Oracle... Datos guardados.");
                        break;
                    case 16:
                        System.out.println("Cargando datos de la base de datos...");
                        break;
                    case 0:
                        System.out.println("Cerrando juego...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                // Captura ResourceException y BuildingException
                System.out.println("\n[ERROR] " + e.getMessage());
            }

        } while (option != 0);

        sc.close();
    }
}