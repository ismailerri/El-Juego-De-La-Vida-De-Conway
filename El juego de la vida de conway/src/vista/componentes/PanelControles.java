package vista.componentes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import modelo.Configuracion;
import util.Constantes;
import util.EstiloUI;

// Panel que contiene los controles del juego (botones, slider de velocidad, etc.)
public class PanelControles extends JPanel {
    // Componentes UI
    private JLabel etiquetaEstado;
    private JLabel etiquetaCelulasVivas;
    private JLabel etiquetaReglas;
    private JSlider sliderVelocidad;

    // Interfaz para comunicar eventos al controlador
    public interface ControlesListener {
        void iniciarSimulacion();
        void pausarSimulacion();
        void siguienteGeneracion();
        void mostrarConfiguracion();
        void reiniciarJuego();
        void volverMenu();
        void velocidadCambiada(int velocidad);
    }

    private ControlesListener listener;

    // Constructor
    public PanelControles(Configuracion config, ControlesListener listener) {
        this.listener = listener;

        setLayout(new BorderLayout());
        setBackground(Constantes.COLOR_FONDO);

        // Panel de estado
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstado.setBackground(Constantes.COLOR_BARRA_SUPERIOR);
        panelEstado.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Etiquetas de estado
        etiquetaEstado = EstiloUI.crearEtiqueta("Generación: 0");
        etiquetaCelulasVivas = EstiloUI.crearEtiqueta("Células vivas: 0");
        etiquetaReglas = EstiloUI.crearEtiqueta("Reglas: " + config.getReglaFormatoConway());

        // Añadir etiquetas con separadores
        panelEstado.add(etiquetaEstado);
        JLabel separador1 = EstiloUI.crearEtiqueta(" | ");
        panelEstado.add(separador1);
        panelEstado.add(etiquetaCelulasVivas);
        JLabel separador2 = EstiloUI.crearEtiqueta(" | ");
        panelEstado.add(separador2);
        panelEstado.add(etiquetaReglas);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Constantes.COLOR_FONDO);

        // Crear botones
        JButton btnIniciar = EstiloUI.crearBoton("Iniciar");
        JButton btnPausar = EstiloUI.crearBoton("Pausar");
        JButton btnSiguiente = EstiloUI.crearBoton("Siguiente");
        JButton btnConfiguracion = EstiloUI.crearBoton("Configuración");
        JButton btnReiniciar = EstiloUI.crearBoton("Reiniciar");
        JButton btnMenu = EstiloUI.crearBoton("Menú");

        // Añadir listeners a los botones
        btnIniciar.addActionListener(e -> listener.iniciarSimulacion());
        btnPausar.addActionListener(e -> listener.pausarSimulacion());
        btnSiguiente.addActionListener(e -> listener.siguienteGeneracion());
        btnConfiguracion.addActionListener(e -> listener.mostrarConfiguracion());
        btnReiniciar.addActionListener(e -> listener.reiniciarJuego());
        btnMenu.addActionListener(e -> listener.volverMenu());

        // Añadir botones al panel
        panelBotones.add(btnIniciar);
        panelBotones.add(btnPausar);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnConfiguracion);
        panelBotones.add(btnReiniciar);
        panelBotones.add(btnMenu);

        // Panel de velocidad
        JPanel panelVelocidad = new JPanel();
        panelVelocidad.setBackground(Constantes.COLOR_FONDO);
        panelVelocidad.setLayout(new BoxLayout(panelVelocidad, BoxLayout.Y_AXIS));

        JLabel etiquetaVelocidad = EstiloUI.crearEtiqueta("Velocidad:");
        etiquetaVelocidad.setAlignmentX(Component.CENTER_ALIGNMENT);

        sliderVelocidad = new JSlider(JSlider.HORIZONTAL, 100, 2000, config.getVelocidad());
        sliderVelocidad.setInverted(true); // Valores mas pequeños = mas rapido
        sliderVelocidad.setBackground(Constantes.COLOR_FONDO);
        sliderVelocidad.setForeground(Constantes.COLOR_TEXTO);
        sliderVelocidad.setMajorTickSpacing(500);
        sliderVelocidad.setMinorTickSpacing(100);
        sliderVelocidad.setPaintTicks(true);
        sliderVelocidad.setPaintLabels(true);
        sliderVelocidad.setAlignmentX(Component.CENTER_ALIGNMENT);

        sliderVelocidad.addChangeListener(e -> {
            listener.velocidadCambiada(sliderVelocidad.getValue());
        });

        panelVelocidad.add(etiquetaVelocidad);
        panelVelocidad.add(sliderVelocidad);

        // Panel principal de controles
        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBackground(Constantes.COLOR_FONDO);
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelControles.add(panelBotones, BorderLayout.NORTH);
        panelControles.add(panelVelocidad, BorderLayout.SOUTH);

        // Añadir todo al panel principal
        add(panelEstado, BorderLayout.NORTH);
        add(panelControles, BorderLayout.SOUTH);
    }

    // Actualiza las estadisticas mostradas
    public void actualizarEstadisticas(int generacion, int celulasVivas) {
        etiquetaEstado.setText("Generación: " + generacion);
        etiquetaCelulasVivas.setText("Células vivas: " + celulasVivas);
    }

    // Actualiza la regla mostrada
    public void actualizarReglas(String regla) {
        etiquetaReglas.setText("Reglas: " + regla);
    }
}
