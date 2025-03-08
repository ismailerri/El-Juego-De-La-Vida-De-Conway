package vista.dialogos;

import javax.swing.JOptionPane;

// Dialogo para la configuracion inicial del juego
public class DialogoInicial {

    // Interfaz para comunicar eventos
    public interface InicializacionListener {
        void inicializarManual(int numCelulas, boolean generarVecinas);
        void inicializarAutomatica(int numGrupos);
        void configurarLimiteGeneraciones(int limite);
    }

    private InicializacionListener listener;
    private javax.swing.JFrame padre;

    // Constructor
    public DialogoInicial(javax.swing.JFrame padre, InicializacionListener listener) {
        this.padre = padre;
        this.listener = listener;
    }

    // Muestra el dialogo inicial completo
    public boolean mostrar() {
        // Primero, configurar si la secuencia sera infinita o finita
        Object[] opcionesSecuencia = {
                "Secuencia infinita de generaciones",
                "Secuencia finita (número limitado de generaciones)"
        };

        int seleccionSecuencia = JOptionPane.showOptionDialog(
                padre,
                "Seleccione el tipo de secuencia:",
                "Configuración de secuencia",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesSecuencia,
                opcionesSecuencia[0]
        );

        // Si se cerro la ventana, cancelar
        if (seleccionSecuencia == -1) {
            return false;
        }

        // Si se eligio secuencia finita, pedir numero de generaciones
        if (seleccionSecuencia == 1) {
            if (!mostrarDialogoLimiteGeneraciones()) {
                return false;
            }
        }

        // Ahora mostrar opciones de inicializacion
        Object[] opcionesInicializacion = {
                "Manual (al menos 5 células)",
                "Automático (N grupos)"
        };

        int seleccion = JOptionPane.showOptionDialog(
                padre,
                "Como desea inicializar el juego?",
                "Inicialización del Juego de la Vida",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesInicializacion,
                opcionesInicializacion[0]
        );

        if (seleccion == 0) {
            return mostrarDialogoInicializacionManual();
        } else if (seleccion == 1) {
            return mostrarDialogoInicializacionAutomatica();
        } else {
            return false;
        }
    }

    // Dialogo para configurar limite de generaciones
    private boolean mostrarDialogoLimiteGeneraciones() {
        String input = JOptionPane.showInputDialog(
                padre,
                "Ingrese el número de generaciones que desea simular:",
                "Configuración de generaciones",
                JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return false;
        }

        try {
            int limite = Integer.parseInt(input);
            if (limite <= 0) {
                JOptionPane.showMessageDialog(
                        padre,
                        "El número de generaciones debe ser positivo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return mostrarDialogoLimiteGeneraciones();
            }

            listener.configurarLimiteGeneraciones(limite);
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    padre,
                    "Por favor ingrese un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return mostrarDialogoLimiteGeneraciones();
        }
    }

    // Dialogo para inicializacion manual
    private boolean mostrarDialogoInicializacionManual() {
        Object[] opcionesManual = {
                "Células individuales",
                "Células con 4 vecinas cada una"
        };

        int seleccionManual = JOptionPane.showOptionDialog(
                padre,
                "Seleccione el tipo de inicialización manual:",
                "Inicialización Manual",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesManual,
                opcionesManual[0]
        );

        // Si se cerro la ventana, cancelar
        if (seleccionManual == -1) {
            return false;
        }

        boolean generarVecinas = (seleccionManual == 1);

        // Solicitar la cantidad de celulas a colocar (al menos 5)
        String inputCantidad = JOptionPane.showInputDialog(
                padre,
                "Ingrese la cantidad de células a colocar (mínimo 5):",
                "Cantidad de células",
                JOptionPane.PLAIN_MESSAGE
        );

        if (inputCantidad == null) {
            return false;
        }

        try {
            int numCelulas = Integer.parseInt(inputCantidad);
            if (numCelulas < 5) {
                JOptionPane.showMessageDialog(
                        padre,
                        "Debe colocar al menos 5 células.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return mostrarDialogoInicializacionManual();
            }

            listener.inicializarManual(numCelulas, generarVecinas);
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    padre,
                    "Por favor ingrese un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return mostrarDialogoInicializacionManual();
        }
    }

    // Dialogo para inicializacion automatica
    private boolean mostrarDialogoInicializacionAutomatica() {
        String input = JOptionPane.showInputDialog(
                padre,
                "Ingrese la cantidad de colonias/grupos a generar:\n" +
                        "(Cada colonia consiste en 5 células: 1 central y 4 vecinas)",
                "Inicialización Automática",
                JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return false;
        }

        try {
            int numGrupos = Integer.parseInt(input);

            if (numGrupos <= 0) {
                JOptionPane.showMessageDialog(
                        padre,
                        "El número de colonias debe ser positivo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return mostrarDialogoInicializacionAutomatica();
            }

            listener.inicializarAutomatica(numGrupos);

            // Informar al usuario sobre la inicialización
            JOptionPane.showMessageDialog(
                    padre,
                    "Se han generado " + numGrupos + " colonias aleatorias.\n" +
                            "Cada colonia consiste en 1 célula central con 4 vecinas alrededor.",
                    "Inicialización Completada",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    padre,
                    "Por favor ingrese un número valido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return mostrarDialogoInicializacionAutomatica();
        }
    }
}
