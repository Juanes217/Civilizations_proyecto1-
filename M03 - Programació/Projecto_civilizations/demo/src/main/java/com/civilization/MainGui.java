package com.civilization;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
// AGREGADO: Librerías necesarias para la comunicación con MySQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainGui extends JFrame {
    // Componentes principales del Imperio
    private Civilization reino;
    private StatsPanel barraRecursos;
    private TeachPanel seccionI_D;
    private ArmyPanel cuartelMilitar; 
    private BuildingPanel zonaConstruccion; 
    private BattlePanel frenteBatalla; 
    private JLabel lblResultado;
    
    // Consola de eventos
    private JTextArea diarioCronicas;
    private CardLayout gestorVistas = new CardLayout(); // CORREGIDO: Se quitó el espacio en blanco aquí
    private JPanel contenedorPrincipal = new JPanel(gestorVistas);
    
    private Random random = new Random();

    public MainGui() {
    	Timer timer = new Timer();
    	MainGui maingui = this;
        
        // Inicialización de lblResultado para evitar NullPointerException
        lblResultado = new JLabel("", SwingConstants.CENTER);

        // Inicialización del motor del juego
        reino = new Civilization(0, 0, 70000, 70000, 70000, 0, 0, 0, 0, 0, 0, 0); 
        
        TimerTask attack = new TimerTask() {
            @Override
            public void run() {
                // Ejecuta la batalla automática
                maingui.luchar(reino, maingui);
            }
        };

        // Cambiado a 60000 ms = 1 minuto (estaba en 1800000)
        timer.scheduleAtFixedRate(attack, 60000, 60000);
     
        configurarVentana();
        inicializarComponentes();
        montarInterfaz();
        
        // Bucle de producción cada segundo
        javax.swing.Timer loopPrincipal = new javax.swing.Timer(1000, e -> {
            reino.producirRecursos();
            actualizarTodo();
            
            // AGREGADO: Guardado automático persistente en MySQL
            try (Connection con = conectarBD()) {
                if (con != null) {
                    String sql = "UPDATE partida SET comida = ?, madera = ?, hierro = ?, batallas = ? WHERE id = 1";
                    try (PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, reino.getFood());
                        ps.setInt(2, reino.getWood());
                        ps.setInt(3, reino.getIron());
                        ps.setInt(4, reino.getBattles());
                        ps.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                // Silenciado para evitar bloquear la experiencia del juego en consola
            }
        });
        loopPrincipal.start();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("CIVILIZATIONS: EMPIRE MANAGER v1.0");
        setSize(1400, 900); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PanelFondo fondo = new PanelFondo("fondo_1.jpg"); 
        fondo.setLayout(new BorderLayout(0, 0));
        setContentPane(fondo);
    }

    private void inicializarComponentes() {
        barraRecursos = new StatsPanel(reino);
        seccionI_D = new TeachPanel(reino, this);
        cuartelMilitar = new ArmyPanel(reino, this);
        zonaConstruccion = new BuildingPanel(reino, this); 
        frenteBatalla = new BattlePanel(reino, this); 

        barraRecursos.setOpaque(false);
        contenedorPrincipal.setOpaque(false);
        
        configurarDiario();
    }

    private void montarInterfaz() {
        JPanel navegacion = new JPanel(new GridLayout(6, 1, 0, 20));
        navegacion.setOpaque(true);
        navegacion.setBackground(new Color(35, 25, 15, 220)); 
        navegacion.setPreferredSize(new Dimension(280, 0));
        navegacion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 5, new Color(184, 134, 11)),
            BorderFactory.createEmptyBorder(40, 20, 40, 20)
        ));

        JButton btnBuild = crearBotonEstilizado("\uD83C\uDFDB EDIFICIOS", new Color(100, 80, 20));
        JButton btnArmy = crearBotonEstilizado("\u2694 EJ\u00C9RCITO", new Color(120, 20, 20));
        JButton btnTeach = crearBotonEstilizado("\u2699 TECNOLOG\u00CDA", new Color(20, 80, 120));
        JButton btnBattle = crearBotonEstilizado("\u26A0 BATALLA", new Color(80, 20, 100));

        navegacion.add(new JLabel("<html><h2 style='color:white; text-align:center;'>MEN\u00DA REAL</h2></html>", SwingConstants.CENTER));
        navegacion.add(btnBuild);
        navegacion.add(btnArmy);
        navegacion.add(btnTeach);
        navegacion.add(btnBattle);
        // Añadimos el label de resultado al menú para que sea visible
        navegacion.add(lblResultado);

        contenedorPrincipal.add(zonaConstruccion, "BUILD");
        contenedorPrincipal.add(cuartelMilitar, "ARMY");
        contenedorPrincipal.add(seccionI_D, "TEACH");
        contenedorPrincipal.add(frenteBatalla, "BATTLE");

        btnBuild.addActionListener(e -> gestorVistas.show(contenedorPrincipal, "BUILD"));
        btnArmy.addActionListener(e -> gestorVistas.show(contenedorPrincipal, "ARMY"));
        btnTeach.addActionListener(e -> gestorVistas.show(contenedorPrincipal, "TEACH"));
        btnBattle.addActionListener(e -> gestorVistas.show(contenedorPrincipal, "BATTLE"));

        add(barraRecursos, BorderLayout.NORTH);     
        add(navegacion, BorderLayout.WEST); 
        add(contenedorPrincipal, BorderLayout.CENTER);  
    }

    private void configurarDiario() {
        diarioCronicas = new JTextArea(8, 40);
        diarioCronicas.setEditable(false);
        diarioCronicas.setBackground(new Color(20, 15, 10, 230)); 
        diarioCronicas.setForeground(new Color(210, 180, 140)); 
        diarioCronicas.setFont(new Font("Serif", Font.ITALIC, 16)); 
        
        JScrollPane scroll = new JScrollPane(diarioCronicas);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(184, 134, 11), 2), "CR\u00D3NICAS DEL REINO", 
            TitledBorder.CENTER, TitledBorder.TOP, null, new Color(184, 134, 11)));
        
        add(scroll, BorderLayout.SOUTH);
    }

    private JButton crearBotonEstilizado(String texto, Color colorResaltado) {
        JButton boton = new JButton(texto);
        Font fuente = new Font("SansSerif", Font.BOLD, 18);
        boton.setFont(fuente);
        boton.setForeground(new Color(240, 230, 190)); 
        boton.setBackground(new Color(60, 45, 30));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { 
                boton.setBackground(colorResaltado); 
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) { 
                boton.setBackground(new Color(60, 45, 30)); 
            }
        });
        return boton;
    }

    public void escribirLog(String msj) {
        diarioCronicas.append(" > " + msj + "\n");
        diarioCronicas.setCaretPosition(diarioCronicas.getDocument().getLength());
    }

    public void actualizarTodo() {
        if (barraRecursos != null) barraRecursos.actualizar(reino);
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGui::new);
    }
    
    private void luchar(Civilization civ, MainGui main) {
        int fuerzaJugador = 0;
        // Se asume que getArmy() devuelve un array de Listas (ArrayList<MilitaryUnit>[])
        for (int i = 0; i < civ.getArmy().length; i++) {
            fuerzaJugador += civ.getArmy()[i].size() * 10;
        }
        
        fuerzaJugador += civ.getTechnologyAttack() * 50;

        // Se quita el JOptionPane para que la batalla sea automática sin interrumpir
        if (fuerzaJugador <= 0) {
            main.escribirLog("Aviso: El enemigo pasó de largo porque no tenemos tropas.");
            return;
        }

        int fuerzaEnemigo = random.nextInt(450) + 50;
        main.escribirLog("CHOQUE DE ACERO: Fuerza Real (" + fuerzaJugador + ") vs Invasores (" + fuerzaEnemigo + ")");

        if (fuerzaJugador >= fuerzaEnemigo) {
            int botín = random.nextInt(2000) + 500;
            // AÑADIDO: Suma de comida
            civ.setFood(civ.getFood() + botín);
            civ.setWood(civ.getWood() + botín);
            civ.setIron(civ.getIron() + botín);
            civ.setBattles(civ.getBattles() + 1);
            
            main.escribirLog("¡GLORIA Y VICTORIA! El botín es de " + botín + " unidades.");
            lblResultado.setText("<html><center><font color='#50FF50'>¡VICTORIA!</font></center></html>");
            
            // AÑADIDO: Guardar los datos en el historial global y en el diario para la web
            BaseDatos.HistorialBatallas.guardarResultadoBatalla(civ);
            BaseDatos.HistorialBatallas.registrarLogBatalla("VICTORIA", botín, fuerzaJugador, fuerzaEnemigo);
        } else {
            main.escribirLog("¡INFORTUNIO! Nuestras líneas han caído.");
            lblResultado.setText("<html><center><font color='#FF5050'>¡DERROTA!</font></center></html>");
            
            for (int i = 0; i < civ.getArmy().length; i++) {
                if (!civ.getArmy()[i].isEmpty()) {
                    civ.getArmy()[i].remove(0); 
                }
            }
            
            // AÑADIDO: Guardar las pérdidas en el historial global y registrar el fallo para la web
            BaseDatos.HistorialBatallas.guardarResultadoBatalla(civ);
            BaseDatos.HistorialBatallas.registrarLogBatalla("DERROTA", 0, fuerzaJugador, fuerzaEnemigo);
        }
        // LLAMADA CLAVE: Actualiza la interfaz para que se vean los nuevos recursos
        main.actualizarTodo();
    }

    // AGREGADO: Proveedor de conexiones integrado para encapsular la lógica de la BD en esta vista
    private Connection conectarBD() {
        String url = "jdbc:mysql://localhost:3306/juego_civilizaciones";
        String usuario = "root";
        String contrasena = "P@ssw0rd";
        try {
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            return null;
        }
    }
}