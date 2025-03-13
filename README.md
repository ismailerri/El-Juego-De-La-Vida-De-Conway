Juego de la Vida de Conway
Un juego visual sin jugadores creado por John Conway en 1970. Las células viven y mueren según reglas matemáticas simples, creando patrones complejos.

Características:
Tablero donde las células viven o mueren
Puedes elegir el tamaño del tablero (15x15 hasta 50x50)
Velocidad ajustable
Modo manual o automático para crear células iniciales
Puedes pausar, continuar o reiniciar el juego
Muestra información como número de generación y células vivas

Reglas basicas:
Célula con 2 o 3 vecinas vivas → sobrevive
Célula con menos de 2 vecinas vivas → muere por soledad
Célula con más de 3 vecinas vivas → muere por sobrepoblación
Célula muerta con exactamente 3 vecinas vivas → nace

Como usar:
Elige entre "Juego Original" o "Juego Personalizado"
Configura el juego según tus preferencias
Coloca células iniciales o genera grupos aleatorios
Usa los botones para controlar la simulación

Estructura del Proyecto
El proyecto sigue el patrón de diseño Modelo-Vista-Controlador (MVC):
Modelo: Contiene la lógica del juego
Grid.java: Implementa el tablero y las reglas del juego
Configuracion.java: Gestiona los parámetros configurables

Vista: Responsable de la interfaz de usuario
componentes/PanelTablero.java: Visualiza el tablero del juego
componentes/PanelControles.java: Contiene los controles y estadísticas
MenuPrincipal.java: Pantalla de inicio de la aplicación
dialogos/: Contiene ventanas de diálogo para configuración e inicialización

Controlador: Coordina el modelo y la vista
Juego.java: Principal controlador de la aplicación

Utilidades: Funciones de apoyo
Constantes.java: Centraliza colores, textos y dimensiones
EstiloUI.java: Proporciona estilos consistentes para la interfaz

Requisitos:
Java 8 o superior
Cualquier sistema operativo (Windows, Mac, Linux)

Solución de problemas:
Si va lento: Reduce el tamaño del tablero o aumenta la pausa entre generaciones
Si no funciona bien: Verifica que tienes Java instalado correctamente
Comportamiento extraño: Reinicia la simulación

Patrones famosos:
Bloques: No cambian nunca
Intermitentes: Cambian de forma periódicamente
Planeadores: Se mueven por el tablero

Desarrollado por Ismail Errifaiy para la asignatura Programación DAM, Escola Pia Nostra Senyora 2025.
