package com.civilization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BuildingPanel extends JPanel implements Variables { 
    private JButton btnFarm, btnSmithy, btnCarpentry, btnMagicTower, btnChurch;

    public BuildingPanel(Civilization civ, MainGui main) {
        setOpaque(false); 
        setLayout(new GridLayout(5, 1, 12, 12)); 
        
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 2), " ARQUITECTURA DEL REINO ");
        border.setTitleColor(new Color(255, 215, 0)); 
        border.setTitleFont(new Font("Serif", Font.BOLD, 18));
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        Color btnColor = new Color(50, 35, 20, 220);
        ToolTipManager.sharedInstance().setInitialDelay(200);

        // --- SOLUCIÓN: Usamos códigos Unicode básicos que no fallan ---
        // 🌾 = \u2740 (Flor/Espiga) | ⚒ = \u26CF (Pico/Herramientas) | 🪵 = \u270E (Lápiz/Madera) | 🕯 = \u2727 (Brillo) | ⛪ = \u26EA
        btnFarm = crearBotonConstruccion("\u2740 Construir Granja", btnColor, civ.getFarm(), 
                generarCosteHTML(FOOD_COST_FARM, WOOD_COST_FARM, IRON_COST_FARM, 0));
        
        btnSmithy = crearBotonConstruccion("\u26CF Construir Herrer\u00EDa", btnColor, civ.getSmithy(),
                generarCosteHTML(FOOD_COST_SMITHY, WOOD_COST_SMITHY, IRON_COST_SMITHY, 0));
        
        btnCarpentry = crearBotonConstruccion("\u270E Construir Carpinter\u00EDa", btnColor, civ.getCarpentry(),
                generarCosteHTML(FOOD_COST_CARPENTRY, WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, 0));
        
        btnMagicTower = crearBotonConstruccion("\u2727 Torre M\u00E1gica", btnColor, civ.getMagicTower(),
                generarCosteHTML(FOOD_COST_MAGICTOWER, WOOD_COST_MAGICTOWER, IRON_COST_MAGICTOWER, 0));
        
        btnChurch = crearBotonConstruccion("\u26EA Construir Iglesia", btnColor, civ.getChurch(),
                generarCosteHTML(FOOD_COST_CHURCH, WOOD_COST_CHURCH, IRON_COST_CHURCH, MANA_COST_CHURCH));

        // Eventos
        btnFarm.addActionListener(e -> ejecutarConstruccion(() -> civ.newFarm(), "Granja", main, civ));
        btnSmithy.addActionListener(e -> ejecutarConstruccion(() -> civ.newSmithy(), "Herrería", main, civ));
        btnCarpentry.addActionListener(e -> ejecutarConstruccion(() -> civ.newCarpentry(), "Carpintería", main, civ));
        btnMagicTower.addActionListener(e -> ejecutarConstruccion(() -> civ.newMagicTower(), "Torre Mágica", main, civ));
        btnChurch.addActionListener(e -> ejecutarConstruccion(() -> civ.newChurch(), "Iglesia", main, civ));

        add(btnFarm); add(btnSmithy); add(btnCarpentry); add(btnMagicTower); add(btnChurch);
    }

    // He limpiado también los iconos del HTML para evitar que el Tooltip salga con cuadraditos
    private String generarCosteHTML(int food, int wood, int iron, int mana) {
        return "<html><div style='padding:10px; background-color:#2e2315; color:#f0e6bc; border:2px solid #b8860b;'>"
                + "<b style='color:#ffd700; font-size:12px;'>RECURSOS REQUERIDOS:</b><hr>"
                + (food > 0 ? "Comida: <span style='color:#90ee90;'>" + food + "</span><br>" : "")
                + (wood > 0 ? "Madera: <span style='color:#daa520;'>" + wood + "</span><br>" : "")
                + (iron > 0 ? "Hierro: <span style='color:#c0c0c0;'>" + iron + "</span><br>" : "")
                + (mana > 0 ? "Mana: <span style='color:#00bfff;'>" + mana + "</span>" : "")
                + "</div></html>";
    }

    private JButton crearBotonConstruccion(String texto, Color color, int cantidad, String tooltip) {
        JButton btn = new JButton(texto + " (" + cantidad + ")");
        btn.setToolTipText(tooltip);
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setForeground(new Color(240, 230, 190)); 
        
        // --- CAMBIO CLAVE: SansSerif es más compatible en Linux para símbolos ---
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { 
                btn.setBackground(new Color(80, 60, 40, 255)); 
                btn.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) { 
                btn.setBackground(color); 
                btn.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 1));
            }
        });
        return btn;
    }

    private void ejecutarConstruccion(Construible accion, String nombre, MainGui main, Civilization civ) {
        try {
            accion.construir();
            main.escribirLog("EDICTO: Se ha alzado una nueva " + nombre + ".");
            actualizarBotones(civ);
            main.actualizarTodo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actualizarBotones(Civilization civ) {
        // Actualizamos manteniendo los códigos Unicode
        btnFarm.setText("\u2740 Construir Granja (" + civ.getFarm() + ")");
        btnSmithy.setText("\u26CF Construir Herrer\u00EDa (" + civ.getSmithy() + ")");
        btnCarpentry.setText("\u270E Construir Carpinter\u00EDa (" + civ.getCarpentry() + ")");
        btnMagicTower.setText("\u2727 Torre M\u00E1gica (" + civ.getMagicTower() + ")");
        btnChurch.setText("\u26EA Construir Iglesia (" + civ.getChurch() + ")");
    }

    private interface Construible { void construir() throws Exception; }
}