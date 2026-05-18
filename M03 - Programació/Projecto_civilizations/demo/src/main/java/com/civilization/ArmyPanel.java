package com.civilization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ArmyPanel extends JPanel {
    private JTextField txtCantidad;
    private JButton btnSwordsman, btnSpearman, btnCrossbow, btnCannon, 
                    btnArrowTower, btnCatapult, btnRocket, btnMagician, btnPriest;

    public ArmyPanel(Civilization civ, MainGui main) {
        setOpaque(false); 
        setLayout(new GridLayout(11, 1, 8, 8)); 
        
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 2), " CUARTEL GENERAL ");
        border.setTitleColor(new Color(255, 215, 0)); 
        border.setTitleFont(new Font("Serif", Font.BOLD, 18));
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlInput.setOpaque(false);
        
        JLabel lblCant = new JLabel("LEVA DE SOLDADOS:");
        lblCant.setFont(new Font("Serif", Font.BOLD, 14));
        lblCant.setForeground(new Color(240, 230, 190)); 
        
        txtCantidad = new JTextField("1", 5);
        txtCantidad.setBackground(new Color(40, 30, 20)); 
        txtCantidad.setForeground(Color.YELLOW);
        txtCantidad.setCaretColor(Color.WHITE);
        txtCantidad.setHorizontalAlignment(JTextField.CENTER);
        txtCantidad.setFont(new Font("Serif", Font.BOLD, 16));
        txtCantidad.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 1));
        
        pnlInput.add(lblCant);
        pnlInput.add(txtCantidad);
        add(pnlInput);

        Color colorInfanteria = new Color(70, 50, 30, 220); 
        Color colorMaquinaria = new Color(90, 30, 30, 220); 
        Color colorMagia = new Color(30, 50, 90, 220);     

        // --- SOLUCIÓN DEFINITIVA: Símbolos de Plano Básico ---
        // Estos códigos son universales y no requieren fuentes modernas.
        btnSwordsman = crearBotonReclutar("\u2694 Espadach\u00EDn", colorInfanteria, () -> civ.newSwordsman(obtenerCantidad()), main);
        btnSpearman = crearBotonReclutar("\u21CF Lancero", colorInfanteria, () -> civ.newSpearman(obtenerCantidad()), main);
        btnCrossbow = crearBotonReclutar("\u27B4 Ballestero", colorInfanteria, () -> civ.newCrossbow(obtenerCantidad()), main);
        
        btnCannon = crearBotonReclutar("\u260C Ca\u00F1\u00F3n", colorMaquinaria, () -> civ.newCannon(obtenerCantidad()), main);
        btnArrowTower = crearBotonReclutar("\u26E9 Torre Flechas", colorMaquinaria, () -> civ.newArrowTower(obtenerCantidad()), main);
        btnCatapult = crearBotonReclutar("\u2604 Catapulta", colorMaquinaria, () -> civ.newCatapult(obtenerCantidad()), main);
        btnRocket = crearBotonReclutar("\u2191 Torre Cohete", colorMaquinaria, () -> civ.newRocketLauncher(obtenerCantidad()), main);
        
        btnMagician = crearBotonReclutar("\u2727 Mago", colorMagia, () -> civ.newMagician(obtenerCantidad()), main);
        btnPriest = crearBotonReclutar("\u263C Sacerdote", colorMagia, () -> civ.newPriest(obtenerCantidad()), main);

        add(btnSwordsman);
        add(btnSpearman);
        add(btnCrossbow);
        add(btnCannon);
        add(btnArrowTower);
        add(btnCatapult);
        add(btnRocket);
        add(btnMagician);
        add(btnPriest);
    }

    private JButton crearBotonReclutar(String nombre, Color colorBase, ReclutableAccion accion, MainGui main) {
        JButton btn = new JButton(nombre);
        btn.setFocusPainted(false);
        btn.setBackground(colorBase);
        btn.setForeground(new Color(240, 230, 190)); 
        
        // Usamos "SansSerif" que es el nombre genérico más compatible en Java para símbolos.
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

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

        btn.addActionListener(e -> {
            try {
                int n = obtenerCantidad();
                if(n > 0) {
                    accion.ejecutar();
                    main.escribirLog("ALISTAMIENTO: " + n + " reclutas listos.");
                    main.actualizarTodo();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "AVISO", JOptionPane.WARNING_MESSAGE);
            }
        });
        return btn;
    }

    private int obtenerCantidad() {
        try {
            int n = Integer.parseInt(txtCantidad.getText());
            return Math.max(n, 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private interface ReclutableAccion {
        void ejecutar() throws Exception;
    }
}