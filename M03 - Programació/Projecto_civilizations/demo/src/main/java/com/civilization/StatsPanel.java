package com.civilization;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class StatsPanel extends JPanel {
    private JLabel lblMadera, lblHierro, lblComida, lblMana;
    private JLabel lblEdificios, lblEjercito;

    public StatsPanel(Civilization civ) {
        setOpaque(false); 
        setLayout(new GridLayout(2, 1));
        
        setBackground(new Color(0, 0, 0, 180)); 
        setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 215, 0, 150))); 

        JPanel pnlRecursos = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        pnlRecursos.setOpaque(false);

        // --- RECURSOS: Se mantienen madera y hierro, se arreglan comida y maná ---
        lblMadera = crearLabel("🪵 Madera: " + civ.getWood(), new Color(222, 184, 135)); 
        lblHierro = crearLabel("⛓️ Hierro: " + civ.getIron(), new Color(220, 220, 220)); 
        // Nuevos iconos compatibles:
        lblComida = crearLabel("\u269B Comida: " + civ.getFood(), new Color(144, 238, 144));  
        lblMana   = crearLabel("\u2727 Man\u00E1: "   + civ.getMana(), new Color(0, 191, 255));  

        pnlRecursos.add(lblMadera);
        pnlRecursos.add(lblHierro);
        pnlRecursos.add(lblComida);
        pnlRecursos.add(lblMana);

        JPanel pnlConteos = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
        pnlConteos.setOpaque(false);

        // Se mantienen tal cual los tienes en tu captura
        lblEdificios = crearLabel("🏛️ Edificios Totales: 0", new Color(255, 165, 0));
        lblEjercito = crearLabel("⚔️ Fuerza Militar: 0", new Color(255, 69, 0));

        pnlConteos.add(lblEdificios);
        pnlConteos.add(lblEjercito);

        add(pnlRecursos);
        add(pnlConteos);
        
        actualizar(civ);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    private JLabel crearLabel(String texto, Color color) {
        JLabel label = new JLabel(texto);
        label.setForeground(color);
        // Usamos SansSerif para que Linux pueda renderizar los símbolos nuevos correctamente
        label.setFont(new Font("SansSerif", Font.BOLD, 16)); 
        return label;
    }

    public void actualizar(Civilization civ) {
        lblMadera.setText("🪵 Madera: " + civ.getWood());
        lblHierro.setText("⛓️ Hierro: " + civ.getIron());
        lblComida.setText("\u269B Comida: " + civ.getFood());
        lblMana.setText("\u2727 Man\u00E1: " + civ.getMana());

        int totalEdificios = civ.getFarm() + civ.getSmithy() + civ.getCarpentry() + civ.getMagicTower() + civ.getChurch();
        lblEdificios.setText("🏛️ Edificios Totales: " + totalEdificios);

        int totalUnidades = 0;
        if (civ.getArmy() != null) {
            for (int i = 0; i < civ.getArmy().length; i++) {
                totalUnidades += civ.getArmy()[i].size();
            }
        }
        lblEjercito.setText("⚔️ Fuerza Militar: " + totalUnidades);
    }
}