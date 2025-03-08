package controlador;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.BorderLayout;
import modelo.Configuracion;
import modelo.Grid;
import util.Constantes;
import vista.MenuPrincipal;
import vista.componentes.PanelControles;
import vista.componentes.PanelTablero;
import vista.dialogos.DialogoConfiguracion;
import vista.dialogos.DialogoInicial;

// Clase principal que actua como controlador del juego
public class Juego extends JFrame implements
        MenuPrincipal.MenuListener,
        PanelTablero.TableroListener,
        PanelControles.ControlesListener,
        DialogoConfiguracion.ValidadorConfig,
        DialogoInicial.InicializacionListener {

    // Modelo
    private Grid gridModelo;
    private Configuracion config;

    // Vista
    private PanelTablero panelTablero;
    private PanelControles panelControles;
    private MenuPrincipal menuPrincipal;
    private DialogoConfiguracion dialogoConfiguracion;
    private DialogoInicial dialogoInicial;

    // Estado
    private int generacion = 0;
    private Timer temporizador;
    private AtomicBoolean ejecutando = new AtomicBoolean(false);
    private AtomicInteger limiteGeneraciones = new AtomicInteger(0); // 0 = infinito

    // Constructor
    public Juego() {
        // Inicializar modelo
        config = new Configuracion();
        gridModelo = new Grid(config);

        // Inicializar dialogos
        dialogoConfiguracion = new DialogoConfiguracion(this);
        dialogoInicial = new DialogoInicial(this, this);

        // Configuracion de la ventana
        setTitle(Constantes.TITULO_APLICACION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Constantes.COLOR_FONDO);

        // Inicializar temporizador
        temporizador = new Timer(config.getVelocidad(), e -> avanzarGeneracion());

        // Mostrar menu principal
        mostrarMenuPrincipal();

        // Configurar tamaño y posición
        pack();
        setLocationRelativeTo(null);
    }

    // METODOS DEL JUEGO

    // Avanza a la siguiente generacion
    private void avanzarGeneracion() {
        gridModelo.siguienteGeneracion();
        generacion++;
        actualizarEstadisticas();

        // Verificar limite de generaciones
        if (limiteGeneraciones.get() > 0 && generacion >= limiteGeneraciones.get()) {
            temporizador.stop();
            ejecutando.set(false);
            JOptionPane.showMessageDialog(
                    this,
                    "Se ha alcanzado el límite de " + limiteGeneraciones.get() + " generaciones.",
                    "Simulación completada",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Actualiza las etiquetas de estadisticas
    private void actualizarEstadisticas() {
        if (panelControles != null) {
            panelControles.actualizarEstadisticas(generacion, gridModelo.getCelulasVivas());
        }
        if (panelTablero != null) {
            panelTablero.repaint();
        }
    }

    // MÉTODOS DE CONTROL DE VISTA

    // Muestra el menu principal
    private void mostrarMenuPrincipal() {
        // Detener simulacion si esta en curso
        temporizador.stop();
        ejecutando.set(false);

        // Crear menu principal
        menuPrincipal = new MenuPrincipal(this);

        // Mostrar menu
        getContentPane().removeAll();
        getContentPane().add(menuPrincipal);
        revalidate();
        repaint();
    }

    // Inicia el juego y muestra el tablero
    private void iniciarJuego() {
        // Crear componentes de la vista
        panelTablero = new PanelTablero(gridModelo, config, this);
        panelControles = new PanelControles(config, this);

        // Configurar panel principal
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        add(panelTablero, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        // Actualizar estadisticas
        actualizarEstadisticas();

        // Actualizar UI
        revalidate();
        repaint();
    }

    // IMPLEMENTACIÓN DE INTERFACES
    // MenuListener
    @Override
    public void iniciarJuegoOriginal() {
        config = new Configuracion();
        gridModelo = new Grid(config);
        generacion = 0;

        String infoOriginal = generarInfoJuegoOriginal();
        if (JOptionPane.showOptionDialog(
                this,
                infoOriginal,
                "Juego Original",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Aceptar", "Cancelar"},
                "Aceptar"
        ) == JOptionPane.YES_OPTION) {
            setTitle("Juego de la Vida de Conway - Reglas Originales (23/3)");
            mostrarDialogoInicial();
        }
    }

    @Override
    public void iniciarJuegoPersonalizado() {
        Configuracion nuevaConfig = dialogoConfiguracion.mostrar(this, config);
        if (nuevaConfig != null) {
            config = nuevaConfig;
            gridModelo = new Grid(config);
            generacion = 0;
            setTitle("Juego de la Vida de Conway - Reglas Personalizadas");
            mostrarDialogoInicial();
        }
    }

    @Override
    public void salir() {
        System.exit(0);
    }

    // TableroListener
    @Override
    public void celdaCambiada() {
        actualizarEstadisticas();
    }

    // ControlesListener
    @Override
    public void iniciarSimulacion() {
        ejecutando.set(true);
        temporizador.start();
    }

    @Override
    public void pausarSimulacion() {
        temporizador.stop();
        ejecutando.set(false);

        Object[] opciones = {"Continuar simulación", "Volver al menú principal"};
        int seleccion = JOptionPane.showOptionDialog(
                this,
                "Simulación pausada en generación " + generacion + "\n" +
                        "Células vivas: " + gridModelo.getCelulasVivas() + "\n\n" +
                        "¿Qué desea hacer?",
                "Juego pausado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == 0) {
            temporizador.start();
            ejecutando.set(true);
        } else {
            mostrarMenuPrincipal();
        }
    }

    @Override
    public void siguienteGeneracion() {
        avanzarGeneracion();
    }

    @Override
    public void mostrarConfiguracion() {
        boolean estabaEjecutando = ejecutando.get();
        if (estabaEjecutando) {
            temporizador.stop();
            ejecutando.set(false);
        }

        Configuracion nuevaConfig = dialogoConfiguracion.mostrar(this, config);
        if (nuevaConfig != null) {
            // Guardar configuración anterior para comparar
            int anchoAnterior = config.getAncho();
            int altoAnterior = config.getAlto();

            // Actualizar configuración
            config = nuevaConfig;

            // Si cambio el tamaño del tablero, crear nuevo grid
            if (anchoAnterior != config.getAncho() || altoAnterior != config.getAlto()) {
                temporizador.stop();
                generacion = 0;
                gridModelo = new Grid(config);
                mostrarDialogoInicial();
                return;
            }

            // Actualizar velocidad del temporizador
            temporizador.setDelay(config.getVelocidad());

            // Actualizar UI
            panelTablero.actualizarModelo(gridModelo, config);
            panelControles.actualizarReglas(config.getReglaFormatoConway());
        }

        if (estabaEjecutando) {
            temporizador.start();
            ejecutando.set(true);
        }
    }

    @Override
    public void reiniciarJuego() {
        temporizador.stop();
        ejecutando.set(false);
        generacion = 0;
        mostrarDialogoInicial();
    }

    @Override
    public void volverMenu() {
        mostrarMenuPrincipal();
    }

    @Override
    public void velocidadCambiada(int velocidad) {
        config.setVelocidad(velocidad);
        temporizador.setDelay(velocidad);
    }

    // ValidadorConfig
    @Override
    public int validarNumero(String texto, int min, int max, String campo) throws IllegalArgumentException {
        try {
            int valor = Integer.parseInt(texto);
            if (valor < min || valor > max) {
                throw new IllegalArgumentException(
                        campo + " debe estar entre " + min + " y " + max
                );
            }
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    campo + " debe ser un número entero valido."
            );
        }
    }

    @Override
    public String validarReglas(String reglas) throws IllegalArgumentException {
        for (char c : reglas.toCharArray()) {
            if (c < '0' || c > '8') {
                throw new IllegalArgumentException(
                        "Las reglas solo pueden contener números del 0 al 8."
                );
            }
        }
        return reglas;
    }

    // InicializacionListener
    @Override
    public void inicializarManual(int numCelulas, boolean generarVecinas) {
        // Limpiar grid
        for (int x = 0; x < config.getAncho(); x++) {
            for (int y = 0; y < config.getAlto(); y++) {
                gridModelo.setCelda(x, y, false);
            }
        }

        // Solicitar posiciones
        for (int i = 0; i < numCelulas; i++) {
            String input = JOptionPane.showInputDialog(
                    this,
                    "Ingrese la posición " + (i+1) + " de " + numCelulas + " (formato: X,Y)\n" +
                            "Valores válidos para X: 0-" + (config.getAncho()-1) + "\n" +
                            "Valores válidos para Y: 0-" + (config.getAlto()-1),
                    "Posición " + (i+1),
                    JOptionPane.PLAIN_MESSAGE
            );

            if (input == null) {
                mostrarMenuPrincipal();
                return;
            }

            try {
                String[] coords = input.split(",");
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());

                if (x >= 0 && x < config.getAncho() && y >= 0 && y < config.getAlto()) {
                    // Colocar la celula
                    gridModelo.setCelda(x, y, true);

                    // Si se eligio la opción de generar vecinas, hacerlo
                    if (generarVecinas) {
                        gridModelo.generarVecinasAlrededor(x, y);
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Posición fuera de rango. Intente nuevamente.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    i--;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Formato incorrecto. Use X,Y (ejemplo: 10,15)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                i--;
            }
        }

        generacion = 0;
        iniciarJuego();
    }

    @Override
    public void inicializarAutomatica(int numGrupos) {
        gridModelo.inicializarAleatoria(numGrupos);
        generacion = 0;
        iniciarJuego();
    }

    @Override
    public void configurarLimiteGeneraciones(int limite) {
        limiteGeneraciones.set(limite);
    }

    // MÉTODOS AUXILIARES

    // Genera texto informativo sobre el juego original
    private String generarInfoJuegoOriginal() {
        return "En las reglas originales de Conway (23/3):\n\n" +
                "- Una célula viva con 2 o 3 vecinas vivas sobrevive en la siguiente generación\n" +
                "- Una célula viva con menos de 2 vecinas vivas muere por soledad\n" +
                "- Una célula viva con más de 3 vecinas vivas muere por superpoblación\n" +
                "- Una célula muerta con exactamente 3 vecinas vivas nace (se convierte en viva)\n\n" +
                "Formato de reglas: 23/3\n" +
                "- 23: La célula sobrevive con 2 o 3 vecinas\n" +
                "- 3: La célula nace con exactamente 3 vecinas\n\n" +
                "Tablero: " + config.getAncho() + "x" + config.getAlto() + " celdas\n" +
                "Velocidad: " + config.getVelocidad() + " milisegundos por generación";
    }

    // Muestra el dialogo inicial
    private void mostrarDialogoInicial() {
        // Reiniciar contadores
        generacion = 0;
        limiteGeneraciones.set(0);

        // Mostrar dialogo
        if (dialogoInicial.mostrar()) {
            iniciarJuego();
        } else {
            mostrarMenuPrincipal();
        }
    }
}
