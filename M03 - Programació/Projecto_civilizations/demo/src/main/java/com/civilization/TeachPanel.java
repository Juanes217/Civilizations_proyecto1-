package com.civilization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TeachPanel extends JPanel {
    private JButton btnUpgradeAttack;
    private JButton btnUpgradeDefense;
    private JLabel lblAttackLevel;
    private JLabel lblDefenseLevel;

    public TeachPanel(Civilization civ, MainGui main) {
        // --- TRANSPARENCIA PARA VER EL MAPA ---
        setOpaque(false);
        setLayout(new GridLayout(2, 1, 15, 15)); 
        
        // Borde estilo "Códice Real"
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 2), " ACADEMIA DE CIENCIAS ");
        border.setTitleColor(new Color(255, 215, 0)); // Oro
        border.setTitleFont(new Font("Serif", Font.BOLD, 18));
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // --- SECCIÓN DE ATAQUE (Estilo Pergamino de Guerra) ---
        JPanel pnlAttack = crearSeccionTech("⚔️ TÁCTICAS DE ASALTO", new Color(120, 40, 40, 200));
        btnUpgradeAttack = crearBotonTech("DESARROLLAR FILO", new Color(80, 20, 20));
        lblAttackLevel = crearLabelNivel(civ.getTechnologyAttack());
        pnlAttack.add(lblAttackLevel, BorderLayout.NORTH);
        pnlAttack.add(btnUpgradeAttack, BorderLayout.CENTER);

        // --- SECCIÓN DE DEFENSA (Estilo Pergamino de Fortificación) ---
        JPanel pnlDefense = crearSeccionTech("🛡️ CIENCIA DEL BLINDAJE", new Color(40, 70, 110, 200));
        btnUpgradeDefense = crearBotonTech("REFORZAR ACERO", new Color(20, 40, 80));
        lblDefenseLevel = crearLabelNivel(civ.getTechnologyDefense());
        pnlDefense.add(lblDefenseLevel, BorderLayout.NORTH);
        pnlDefense.add(btnUpgradeDefense, BorderLayout.CENTER);

        // Eventos (Respetando tu lógica original)
        btnUpgradeAttack.addActionListener(e -> {
            try {
                civ.upgradeTechnologyAttack();
                main.escribirLog("CRÓNICA: Maestros armeros alcanzan el Nivel " + civ.getTechnologyAttack());
                actualizarLabels(civ);
                main.actualizarTodo();
            } catch (ResourceException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "FALTA MATERIAL", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnUpgradeDefense.addActionListener(e -> {
            try {
                civ.upgradeTechnologyDefense();
                main.escribirLog("CRÓNICA: La guardia real ahora tiene defensa Nivel " + civ.getTechnologyDefense());
                actualizarLabels(civ);
                main.actualizarTodo();
            } catch (ResourceException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "FALTA MATERIAL", JOptionPane.WARNING_MESSAGE);
            }
        });

        add(pnlAttack);
        add(pnlDefense);
    }

    private JPanel crearSeccionTech(String titulo, Color colorFondo) {
        JPanel pnl = new JPanel(new BorderLayout(5, 5));
        pnl.setOpaque(true);
        pnl.setBackground(new Color(40, 30, 20, 160)); // Fondo sepia oscuro traslúcido
        pnl.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 1), titulo, 0, 0, 
            new Font("Serif", Font.BOLD, 14), new Color(240, 230, 190)));
        return pnl;
    }

    private JButton crearBotonTech(String texto, Color colorBase) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(colorBase);
        btn.setForeground(new Color(240, 230, 190)); // Color hueso
        btn.setFont(new Font("Serif", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 1));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { 
                btn.setBackground(colorBase.brighter()); 
                btn.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) { 
                btn.setBackground(colorBase); 
                btn.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 1));
            }
        });
        return btn;
    }

    private JLabel crearLabelNivel(int nivel) {
        JLabel lbl = new JLabel("GRADO DE ESTUDIO: " + nivel, SwingConstants.CENTER);
        lbl.setForeground(Color.YELLOW);
        lbl.setFont(new Font("Serif", Font.BOLD, 16));
        return lbl;
    }

    public void actualizarLabels(Civilization civ) {
        lblAttackLevel.setText("GRADO DE ESTUDIO: " + civ.getTechnologyAttack());
        lblDefenseLevel.setText("GRADO DE ESTUDIO: " + civ.getTechnologyDefense());
    }
}