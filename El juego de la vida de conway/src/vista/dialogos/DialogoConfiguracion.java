package vista.dialogos;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Configuracion;
import util.Constantes;

// Dialogo para configurar las reglas del juego
public class DialogoConfiguracion {

    // Interfaz para validar valores
    public interface ValidadorConfig {
        int validarNumero(String texto, int min, int max, String campo);
        String validarReglas(String reglas);
    }

    private ValidadorConfig validador;

    // Constructor
    public DialogoConfiguracion(ValidadorConfig validador) {
        this.validador = validador;
    }

    // Muestra el dialogo de configuracion y devuelve la nueva configuracion o null si se cancela
    public Configuracion mostrar(javax.swing.JFrame padre, Configuracion configActual) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Constantes.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField campoAncho = new JTextField(String.valueOf(configActual.getAncho()));
        JTextField campoAlto = new JTextField(String.valueOf(configActual.getAlto()));
        JTextField campoVelocidad = new JTextField(String.valueOf(configActual.getVelocidad()));
        JTextField campoSupervivencia = new JTextField(configActual.getReglasSupervivencia());
        JTextField campoNacimiento = new JTextField(configActual.getReglasNacimiento());

        // Estilo de los componentes
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        JLabel lblAncho = new JLabel("Ancho:");
        lblAncho.setForeground(Constantes.COLOR_TEXTO);
        lblAncho.setFont(labelFont);

        JLabel lblAlto = new JLabel("Alto:");
        lblAlto.setForeground(Constantes.COLOR_TEXTO);
        lblAlto.setFont(labelFont);

        JLabel lblVelocidad = new JLabel("Velocidad (ms):");
        lblVelocidad.setForeground(Constantes.COLOR_TEXTO);
        lblVelocidad.setFont(labelFont);

        JLabel lblSupervivencia = new JLabel("Reglas Supervivencia:");
        lblSupervivencia.setForeground(Constantes.COLOR_TEXTO);
        lblSupervivencia.setFont(labelFont);

        JLabel lblNacimiento = new JLabel("Reglas Nacimiento:");
        lblNacimiento.setForeground(Constantes.COLOR_TEXTO);
        lblNacimiento.setFont(labelFont);

        panel.add(lblAncho);
        panel.add(campoAncho);
        panel.add(lblAlto);
        panel.add(campoAlto);
        panel.add(lblVelocidad);
        panel.add(campoVelocidad);
        panel.add(lblSupervivencia);
        panel.add(campoSupervivencia);
        panel.add(lblNacimiento);
        panel.add(campoNacimiento);

        Object[] opciones = {"Aceptar", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(
                padre, panel, "Configuración del Juego",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Validar valores
                int nuevoAncho = validador.validarNumero(campoAncho.getText(), 15, 50, "Ancho");
                int nuevoAlto = validador.validarNumero(campoAlto.getText(), 15, 50, "Alto");
                int nuevaVelocidad = validador.validarNumero(campoVelocidad.getText(), 100, 2000, "Velocidad");
                String nuevasReglasSupervivencia = validador.validarReglas(campoSupervivencia.getText());
                String nuevasReglasNacimiento = validador.validarReglas(campoNacimiento.getText());

                // Crear nueva configuracion
                return new Configuracion(
                        nuevoAncho,
                        nuevoAlto,
                        nuevasReglasSupervivencia,
                        nuevasReglasNacimiento,
                        nuevaVelocidad
                );
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        padre,
                        ex.getMessage(),
                        Constantes.MSG_ERROR_CONFIGURACION,
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }
        }

        return null;
    }
}
