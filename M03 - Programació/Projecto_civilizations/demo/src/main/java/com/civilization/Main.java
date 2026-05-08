package com.civilization;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Inicializamos el escáner
        Scanner sc = new Scanner(System.in);
        
        // CORRECCIÓN: Se pasan los 12 parámetros iniciales (Defensa, Ataque, Madera, Hierro, Comida, Mana, etc.)
        // He puesto 10000 de recursos base para que puedas probar las opciones.
        Civilization civilization = new Civilization(0, 0, 10000, 10000, 10000, 0, 0, 0, 0, 0, 0, 0);
        
        int option;
        
        do {
            System.out.println("\n===== CIVILIZATIONS =====");
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
            System.out.println("11. Simular batalla");
            System.out.println("12. Mejorar tecnología de ataque");
            System.out.println("13. Mejorar tecnología de defensa");
            System.out.println("14. Generar recursos");
            System.out.println("15. Guardar civilización en base de datos");
            System.out.println("16. Cargar civilización");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            option = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer después de leer la opción

            // CORRECCIÓN: Bloque try-catch para manejar las excepciones de recursos/edificios
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
                        System.out.print("Cantidad de espadachines: ");
                        int n5 = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        civilization.newSwordsman(n5);
                        break;
                    case 6:
                        System.out.print("Cantidad de lanceros: ");
                        int n6 = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        civilization.newSpearman(n6);
                        break;
                    case 7:
                        System.out.print("Cantidad de ballestas: ");
                        int n7 = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        civilization.newCrossbow(n7);
                        break;
                    case 8:
                        System.out.print("Cantidad de cañones: ");
                        int n8 = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        civilization.newCannon(n8);
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
                        System.out.println("Simulando batalla...");
                        // Aquí llamarías a: new Battle(civilization.getArmy(), enemyArmy).startBattle();
                        break;
                    case 12:
                        // CORRECCIÓN: Nombre exacto del método en Civilization.java
                        civilization.upgradeTechnologyAttack();
                        break;
                    case 13:
                        // CORRECCIÓN: Nombre exacto del método en Civilization.java
                        civilization.upgradeTechnologyDefense();
                        break;
                    case 14:
                        System.out.println("Recursos generados manualmente.");
                        // Aquí podrías añadir lógica para sumar recursos base
                        break;
                    case 15:
                        System.out.println("Guardando en la base de datos...");
                        break;
                    case 16:
                        System.out.println("Cargando civilización...");
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                // Captura ResourceException o BuildingException y muestra el mensaje
                System.out.println("\n[ERROR] No se pudo realizar la acción: " + e.getMessage());
            }

        } while (option != 0);

        sc.close();
    }
}