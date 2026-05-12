package com.civilization;

import java.util.ArrayList;
import java.util.Random;

public class Battle implements Variables {
    private ArrayList<MilitaryUnit>[][] ejercitos; 
    private StringBuilder desarrolloBatalla;
    private int unidadesInicialesCivilizacion = 0;
    private int unidadesInicialesEnemigo = 0;
    private int bajasCivilizacion = 0;
    private int bajasEnemigo = 0;
    private int[][] perdidasRecursos; // [Bando][0=Comida, 1=Madera, 2=Hierro]
    private Random aleatorio = new Random();

    public Battle(ArrayList<MilitaryUnit>[] ejercitoCivilizacion, ArrayList<MilitaryUnit>[] ejercitoEnemigo) {
        this.ejercitos = new ArrayList[2][9];
        this.ejercitos[0] = ejercitoCivilizacion;
        this.ejercitos[1] = ejercitoEnemigo;
        this.desarrolloBatalla = new StringBuilder();
        this.perdidasRecursos = new int[2][3]; 

        inicializarDatosBatalla();
    }

    private void inicializarDatosBatalla() {
        for (int i = 0; i < 9; i++) {
            unidadesInicialesCivilizacion += ejercitos[0][i].size();
            unidadesInicialesEnemigo += ejercitos[1][i].size();
        }
    }

    public void simulateBattle() {
        desarrolloBatalla.append("--- INICIO DE LA BATALLA ---\n");
        int turno = 0; 

        while (!batallaTerminada()) {
            int bandoAtacante = turno % 2;
            int bandoDefensor = (bandoAtacante == 0) ? 1 : 0;

            // Seleccionar grupo atacante (PDF: elegir hasta que no esté vacío)
            int grupoAtacante;
            do {
                grupoAtacante = elegirGrupoUnidad(bandoAtacante);
            } while (ejercitos[bandoAtacante][grupoAtacante].isEmpty());
            
            MilitaryUnit atacante = ejercitos[bandoAtacante][grupoAtacante].get(aleatorio.nextInt(ejercitos[bandoAtacante][grupoAtacante].size()));

            // Seleccionar grupo defensor
            int grupoDefensor;
            do {
                grupoDefensor = elegirGrupoUnidad(bandoDefensor);
            } while (ejercitos[bandoDefensor][grupoDefensor].isEmpty());
            
            MilitaryUnit defensor = ejercitos[bandoDefensor][grupoDefensor].get(aleatorio.nextInt(ejercitos[bandoDefensor][grupoDefensor].size()));

            // Ejecutar el combate
            pelear(atacante, defensor, bandoAtacante, grupoDefensor);
            
            turno++;
        }
        
        System.out.println(desarrolloBatalla.toString());
        System.out.println(obtenerGanador());
    }

    private void pelear(MilitaryUnit atacante, MilitaryUnit defensor, int bandoAtacante, int grupoDefensor) {
        int bandoDefensor = (bandoAtacante == 0) ? 1 : 0;
        int daño = atacante.attack();
        
        desarrolloBatalla.append(atacante.getClass().getSimpleName() + " ataca con " + daño + " a " + defensor.getClass().getSimpleName() + "\n");

        defensor.takeDamage(daño);

        if (defensor.getActualArmor() <= 0) {
            desarrolloBatalla.append("   ¡Baja: " + defensor.getClass().getSimpleName() + " ha sido destruido!\n");
            
            // Registrar pérdidas económicas según fórmula PDF
            perdidasRecursos[bandoDefensor][0] += defensor.getFoodCost();
            perdidasRecursos[bandoDefensor][1] += defensor.getWoodCost();
            perdidasRecursos[bandoDefensor][2] += defensor.getIronCost();

            ejercitos[bandoDefensor][grupoDefensor].remove(defensor);
            if (bandoDefensor == 0) bajasCivilizacion++; else bajasEnemigo++;
        }
    }

    private int elegirGrupoUnidad(int bando) {
        int prob = aleatorio.nextInt(100);
        if (prob < 35) return 0; // Espadachín
        if (prob < 60) return 1; // Lancero
        if (prob < 80) return 2; // Ballesta
        if (prob < 90) return 3; // Cañón
        if (prob < 93) return 4; // Torre Flechas
        if (prob < 95) return 5; // Catapulta
        if (prob < 97) return 6; // Torre Cohetes
        if (prob < 99) return 7; // Mago
        return 8; // Sacerdote
    }

    public String obtenerGanador() {
        // Fórmula del PDF: Hierro + Madera/5 + Comida/10
        double totalCiv = perdidasRecursos[0][2] + (perdidasRecursos[0][1] / 5.0) + (perdidasRecursos[0][0] / 10.0);
        double totalEnem = perdidasRecursos[1][2] + (perdidasRecursos[1][1] / 5.0) + (perdidasRecursos[1][0] / 10.0);

        String res = "\n======= RECUENTO FINAL DE LA BATALLA =======\n";
        res += "Unidades perdidas por ti: " + bajasCivilizacion + " (Valor: " + (int)totalCiv + ")\n";
        res += "Unidades enemigas destruidas: " + bajasEnemigo + " (Valor: " + (int)totalEnem + ")\n";

        if (totalCiv < totalEnem) {
            return res + "\n>>> ¡VICTORIA PARA LA CIVILIZACIÓN! <<<";
        } else if (totalCiv > totalEnem) {
            return res + "\n>>> ¡DERROTA! El enemigo ha vencido <<<";
        } else {
            return res + "\n>>> EMPATE TÉCNICO <<<";
        }
    }

    private boolean batallaTerminada() {
        int actualesCiv = 0;
        int actualesEnem = 0;
        for (int i = 0; i < 9; i++) {
            actualesCiv += ejercitos[0][i].size();
            actualesEnem += ejercitos[1][i].size();
        }
        // Condición PDF: Termina cuando un bando pierde el 80% (queda el 20%)
        return actualesCiv <= (unidadesInicialesCivilizacion * 0.2) || 
               actualesEnem <= (unidadesInicialesEnemigo * 0.2);
    }
}