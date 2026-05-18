package com.civilization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

public class BattlePanel extends JPanel {
    private JButton btnAtacar;
    private JLabel lblResultado;
    private Random random = new Random();

    public BattlePanel(Civilization civ, MainGui main) {
        // --- TRANSPARENCIA Y LAYOUT ---
        setOpaque(false);
        setLayout(new BorderLayout(20, 20));
        
        // Borde de "Campaña Militar"
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 3), " TIERRAS EN CONFLICTO ");
        border.setTitleColor(new Color(255, 69, 0)); // Naranja rojizo brillante
        border.setTitleFont(new Font("Serif", Font.BOLD, 22));
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(30, 40, 30, 40)));

        // --- ETIQUETA DE ESTADO (ESTILO PERGAMINO) ---
        lblResultado = new JLabel("ORDENA EL AVANCE DE LAS TROPAS", SwingConstants.CENTER);
        lblResultado.setForeground(new Color(240, 230, 190)); // Color hueso
        lblResultado.setFont(new Font("Serif", Font.ITALIC, 20));
        lblResultado.setPreferredSize(new Dimension(0, 100));
        
        // --- BOTÓN DE ATAQUE (ESTILO ESCUDO REAL) ---
        btnAtacar = new JButton("¡INICIAR BATALLA!");
        btnAtacar.setFont(new Font("Serif", Font.BOLD, 26));
        btnAtacar.setBackground(new Color(100, 20, 20)); // Rojo sangre oscuro
        btnAtacar.setForeground(Color.WHITE);
        btnAtacar.setFocusPainted(false);
        btnAtacar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Borde dorado grueso para el botón principal
        btnAtacar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Efecto visual al pasar el ratón
        btnAtacar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtacar.setBackground(new Color(150, 30, 30)); // Rojo más vivo
                btnAtacar.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtacar.setBackground(new Color(100, 20, 20));
                btnAtacar.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));
            }
        });

        btnAtacar.addActionListener(e -> {
            luchar(civ, main);
        });

        add(lblResultado, BorderLayout.NORTH);
        add(btnAtacar, BorderLayout.CENTER);
    }

    private void luchar(Civilization civ, MainGui main) {
        // 1. Calculamos la fuerza total (Tu lógica original)
        int fuerzaJugador = 0;
        for (int i = 0; i < civ.getArmy().length; i++) {
            fuerzaJugador += civ.getArmy()[i].size() * 10;
        }
        
        fuerzaJugador += civ.getTechnologyAttack() * 50;

        if (fuerzaJugador <= 0) {
            JOptionPane.showMessageDialog(this, "¡Tus generales informan que no hay tropas listas!", "SIN EJÉRCITO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Enemigo aleatorio
        int fuerzaEnemigo = random.nextInt(450) + 50;

        main.escribirLog("CHOQUE DE ACERO: Fuerza Real (" + fuerzaJugador + ") vs Invasores (" + fuerzaEnemigo + ")");

        // 3. Resultado (Estilizando los mensajes)
        if (fuerzaJugador >= fuerzaEnemigo) {
            int botín = random.nextInt(2000) + 500;
            civ.setWood(civ.getWood() + botín);
            civ.setIron(civ.getIron() + botín);
            civ.setBattles(civ.getBattles() + 1);
            
            main.escribirLog("¡GLORIA Y VICTORIA! El botín es de " + botín + " unidades de suministros.");
            //main.escribirLog("<html><center><font color='#50FF50'>¡VICTORIA ÉPICA!</font><br><small>Los bardos cantarán tu hazaña</small></center></html>");
        } else {
            main.escribirLog("¡INFORTUNIO! Nuestras líneas han caído ante el enemigo.");
            //lblResultado.setText("<html><center><font color='#FF5050'>¡DERROTA AMARGA!</font><br><small>Recluta más tropas para la venganza</small></center></html>");
            
            for (int i = 0; i < civ.getArmy().length; i++) {
                if (!civ.getArmy()[i].isEmpty()) {
                    civ.getArmy()[i].remove(0); 
                }
            }
        }
        
        main.actualizarTodo();
    }
}