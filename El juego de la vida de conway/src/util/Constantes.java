package util;

import java.awt.Color;
import java.awt.Font;

// Clase que centraliza las constantes utilizadas en el juego
public class Constantes {
    // Constantes de texto
    public static final String TITULO_APLICACION = "Juego de la Vida de Conway Ismaail Errifaiy - Juan Martinez";
    public static final String DESCRIPCION_JUEGO =
            "El Juego de la Vida de Conway es un autómata celular ideado por el matemático " +
                    "británico John Horton Conway en 1970. Es un juego de cero jugadores, " +
                    "en el que su evolución es determinada por un estado inicial.";

    // Colores para el tema
    public static final Color COLOR_FONDO = Color.WHITE;
    public static final Color COLOR_CELDA_VIVA = Color.BLACK;
    public static final Color COLOR_CELDA_MUERTA = new Color(230, 230, 230);
    public static final Color COLOR_TEXTO = Color.BLACK;
    public static final Color COLOR_BORDE_CELDA = new Color(200, 200, 200);
    public static final Color COLOR_BARRA_SUPERIOR = new Color(240, 240, 240);
    public static final Color COLOR_BOTON = Color.WHITE;

    // Fuentes
    public static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 28);
    public static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 16);
    public static final Font FUENTE_BOTONES = new Font("SansSerif", Font.BOLD, 12);
    public static final Font FUENTE_ETIQUETAS = new Font("SansSerif", Font.BOLD, 14);

    // Dimensiones
    public static final int TAMAÑO_CELDA = 12;

    // Mensajes
    public static final String MSG_ERROR_CONFIGURACION = "Error de configuración";
    public static final String MSG_INICIALIZACION_COMPLETA = "Inicialización Completada";
}
