package vista.componentes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import modelo.Configuracion;
import modelo.Grid;
import util.Constantes;

// Panel que visualiza y permite interactuar con el tablero del juego
public class PanelTablero extends JPanel {
    // Referencia al modelo y configuracion
    private Grid gridModelo;
    private Configuracion config;

    // Interfaz para comunicar eventos al controlador
    public interface TableroListener {
        void celdaCambiada();
    }

    private TableroListener listener;

    // Constructor
    public PanelTablero(Grid gridModelo, Configuracion config, TableroListener listener) {
        this.gridModelo = gridModelo;
        this.config = config;
        this.listener = listener;

        // Establecer tamaño preferido
        int tamañoCelda = Constantes.TAMAÑO_CELDA;
        setPreferredSize(new Dimension(
                config.getAncho() * tamañoCelda,
                config.getAlto() * tamañoCelda
        ));

        // Configurar fondo
        setBackground(Constantes.COLOR_FONDO);

        // Permitir dibujar celulas con click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int anchoPanel = getWidth();
                int altoPanel = getHeight();

                // Calcular tamaño de celda
                int tamañoCeldaActual = Math.min(
                        anchoPanel / config.getAncho(),
                        altoPanel / config.getAlto()
                );

                // Calcular offsets para centrar
                int offsetX = (anchoPanel - (tamañoCeldaActual * config.getAncho())) / 2;
                int offsetY = (altoPanel - (tamañoCeldaActual * config.getAlto())) / 2;

                // Calcular coordenadas de la celula
                int x = (e.getX() - offsetX) / tamañoCeldaActual;
                int y = (e.getY() - offsetY) / tamañoCeldaActual;

                // Validar que el clic sea dentro de la cuadricula
                if (x >= 0 && x < config.getAncho() && y >= 0 && y < config.getAlto()) {
                    gridModelo.setCelda(x, y, !gridModelo.getCelda(x, y));
                    listener.celdaCambiada();
                    repaint();
                }
            }
        });
    }

    // Actualiza las referencias al modelo y configuracion
    public void actualizarModelo(Grid gridModelo, Configuracion config) {
        this.gridModelo = gridModelo;
        this.config = config;
        repaint();
    }

    // Dibuja el tablero
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int anchoPanel = getWidth();
        int altoPanel = getHeight();

        // Calcular tamaño de celda
        int tamañoCeldaActual = Math.min(
                anchoPanel / config.getAncho(),
                altoPanel / config.getAlto()
        );

        // Centrar la cuadricula
        int offsetX = (anchoPanel - (tamañoCeldaActual * config.getAncho())) / 2;
        int offsetY = (altoPanel - (tamañoCeldaActual * config.getAlto())) / 2;

        // Dibujar fondo
        g.setColor(Constantes.COLOR_FONDO);
        g.fillRect(0, 0, anchoPanel, altoPanel);

        // Dibujar todas las celdas
        for (int x = 0; x < config.getAncho(); x++) {
            for (int y = 0; y < config.getAlto(); y++) {
                // Dibujar celda segun estado
                if (gridModelo.getCelda(x, y)) {
                    g.setColor(Constantes.COLOR_CELDA_VIVA);
                } else {
                    g.setColor(Constantes.COLOR_CELDA_MUERTA);
                }

                // Tamaño  un poco menor para efecto de rejilla
                g.fillRect(
                        offsetX + x * tamañoCeldaActual + 1,
                        offsetY + y * tamañoCeldaActual + 1,
                        tamañoCeldaActual - 1,
                        tamañoCeldaActual - 1
                );

                // Borde para mejor visualizacion
                g.setColor(Constantes.COLOR_BORDE_CELDA);
                g.drawRect(
                        offsetX + x * tamañoCeldaActual,
                        offsetY + y * tamañoCeldaActual,
                        tamañoCeldaActual,
                        tamañoCeldaActual
                );
            }
        }
    }
}