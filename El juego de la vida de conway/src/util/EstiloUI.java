package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// Clase de utilidad para aplicar estilos consistentes a los componentes UI
public class EstiloUI {

    // Aplica estilo a un boton estandar
    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFont(Constantes.FUENTE_BOTONES);
        boton.setMargin(new Insets(8, 16, 8, 16));
        return boton;
    }

    // Aplica estilo a un boton del menu principal
    public static void styleMenuButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(Constantes.FUENTE_NORMAL);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(350, 50));

        // Efecto al pasar el raton
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
    }


    // Crea una etiqueta de texto con estilo
    public static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.BLACK);
        etiqueta.setFont(Constantes.FUENTE_ETIQUETAS);
        return etiqueta;
    }

    // Crea un area de texto con estilo
    public static JTextArea crearAreaTexto(String texto) {
        JTextArea area = new JTextArea(texto);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setFont(Constantes.FUENTE_NORMAL);
        return area;
    }

    // Crea un panel con fondo blanco
    public static JPanel crearPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        return panel;
    }
}
