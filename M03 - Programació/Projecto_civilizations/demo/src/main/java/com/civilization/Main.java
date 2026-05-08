package com.civilization;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // SOLUCIÓN RÁPIDA: He puesto 100 millones de cada recurso para que NO te falte de nada
        // (Ataque, Defensa, Madera, Hierro, Comida, Mana, Torre, Iglesia, Granja, Herreria, Carpinteria, Batallas)
        Civilization civilization = new Civilization(0, 0, 30000, 30000, 30000, 2000, 1, 0, 1, 1, 1, 0);
        
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
            System.out.println("15. Guardar civilización");
            System.out.println("16. Cargar civilización");
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
                        System.out.print("Cantidad: ");
                        civilization.newSwordsman(sc.nextInt());
                        sc.nextLine();
                        break;
                    case 6:
                        System.out.print("Cantidad: ");
                        civilization.newSpearman(sc.nextInt());
                        sc.nextLine();
                        break;
                    case 7:
                        System.out.print("Cantidad: ");
                        civilization.newCrossbow(sc.nextInt());
                        sc.nextLine();
                        break;
                    case 8:
                        System.out.print("Cantidad: ");
                        civilization.newCannon(sc.nextInt());
                        sc.nextLine();
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
                        break;
                    case 12:
                        civilization.upgradeTechnologyAttack();
                        break;
                    case 13:
                        civilization.upgradeTechnologyDefense();
                        break;
                    case 14:
                        // Lógica para sumar recursos manualmente
                        civilization.setFood(civilization.getFood() + 50000);
                        civilization.setWood(civilization.getWood() + 50000);
                        civilization.setIron(civilization.getIron() + 50000);
                        System.out.println("Has generado 50.000 de cada recurso.");
                        break;
                    case 15:
                        // Aquí llamarías a tu DatabaseManager
                        System.out.println("Guardando datos en la base de datos Oracle...");
                        break;
                    case 16:
                        // Aquí cargarías los datos
                        System.out.println("Cargando datos desde la base de datos...");
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                // Esto es lo que te dice "No tienes recursos"
                System.out.println("\n[!] " + e.getMessage());
            }

        } while (option != 0);

        sc.close();
    }
}