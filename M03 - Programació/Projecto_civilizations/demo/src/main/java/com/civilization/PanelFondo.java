package com.civilization;

import javax.swing.*;
import java.awt.*;

public class PanelFondo extends JPanel {
    private Image imagen;

    public PanelFondo(String fondo_1) {
        // Buscamos la imagen en la carpeta 'src/imagenes'
        try {
            // Intenta cargar la imagen como recurso del sistema
        	java.net.URL imgURL = getClass().getResource("/Imagenes/" + fondo_1);
        	if (imgURL != null) {
                this.imagen = new ImageIcon(imgURL).getImage();
            } else {
                // Si no la encuentra dentro de src, intenta buscarla en la raíz del proyecto
                this.imagen = new ImageIcon(fondo_1).getImage();
            }
        } catch (Exception e) {
            System.err.println("Error: No se pudo localizar el archivo: " + fondo_1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // --- MEJORA DE CALIDAD ---
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 1. DIBUJAR LA IMAGEN (Mapa de Civilization)
        if (imagen != null) {
            // La dibujamos para que siempre cubra todo el panel
            g2d.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fondo de emergencia (Color madera oscura) si la imagen no carga
            g2d.setColor(new Color(40, 30, 20));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // 2. CAPA DE CONTRASTE (Oscurecimiento para legibilidad)
        // El valor 135 da un tono oscuro muy elegante que hace resaltar tus botones dorados
        g2d.setColor(new Color(0, 0, 0, 135)); 
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}