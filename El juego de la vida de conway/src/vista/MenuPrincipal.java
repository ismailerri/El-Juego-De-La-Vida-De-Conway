package vista;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import util.Constantes;
import util.EstiloUI;

// Panel que muestra el menu principal del juego
public class MenuPrincipal extends JPanel {

    // Interfaz para comunicar eventos al controlador
    public interface MenuListener {
        void iniciarJuegoOriginal();
        void iniciarJuegoPersonalizado();
        void salir();
    }

    private MenuListener listener;

    // Constructor
    public MenuPrincipal(MenuListener listener) {
        this.listener = listener;

        // Configurar panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Constantes.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Titulo
        JLabel titulo = new JLabel("Juego de la Vida de Conway");
        titulo.setForeground(Constantes.COLOR_TEXTO);
        titulo.setFont(Constantes.FUENTE_TITULO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Descripcion
        JTextArea descripcion = EstiloUI.crearAreaTexto(Constantes.DESCRIPCION_JUEGO);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcion.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Botones con estilo
        JButton btnJuegoOriginal = new JButton("Juego Original (23/3)");
        EstiloUI.styleMenuButton(btnJuegoOriginal);

        JButton btnJuegoPersonalizado = new JButton("Juego Personalizado");
        EstiloUI.styleMenuButton(btnJuegoPersonalizado);

        JButton btnSalir = new JButton("Salir");
        EstiloUI.styleMenuButton(btnSalir);

        // Añadir listeners
        btnJuegoOriginal.addActionListener(e -> listener.iniciarJuegoOriginal());
        btnJuegoPersonalizado.addActionListener(e -> listener.iniciarJuegoPersonalizado());
        btnSalir.addActionListener(e -> listener.salir());

        // Agregar componentes
        add(Box.createVerticalGlue());
        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(descripcion);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(btnJuegoOriginal);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btnJuegoPersonalizado);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btnSalir);
        add(Box.createVerticalGlue());
    }
}
