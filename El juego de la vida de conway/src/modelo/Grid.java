package modelo;

import java.util.Random;

// Clase que representa el tablero del Juego
public class Grid {
    // Atributos
    private boolean[][] grid;
    private Configuracion configuracion;
    private int celulasVivas;

    // Constructor que inicializa el tablero con una configuracion dada
    public Grid(Configuracion configuracion) {
        this.configuracion = configuracion;
        inicializarGrid();
    }

    // Crea un tablero vacio con las dimensiones de la configuracion
    private void inicializarGrid() {
        grid = new boolean[configuracion.getAncho()][configuracion.getAlto()];
        celulasVivas = 0;
    }

    // Establece el estado de una celda especifica
    public void setCelda(int x, int y, boolean estado) {
        if (x >= 0 && x < configuracion.getAncho() &&
                y >= 0 && y < configuracion.getAlto()) {
            // Actualizar contador de celulas vivas
            if (estado && !grid[x][y]) {
                celulasVivas++;
            } else if (!estado && grid[x][y]) {
                celulasVivas--;
            }
            grid[x][y] = estado;
        }
    }

    // Obtiene el estado de una celda especifica
    public boolean getCelda(int x, int y) {
        if (x >= 0 && x < configuracion.getAncho() &&
                y >= 0 && y < configuracion.getAlto()) {
            return grid[x][y];
        }
        return false;
    }

    // Cuenta el numero de vecinos vivos con envolvimiento toroidal
    public int contarVecinos(int x, int y) {
        int contador = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = (x + dx + configuracion.getAncho()) % configuracion.getAncho();
                int ny = (y + dy + configuracion.getAlto()) % configuracion.getAlto();
                if (getCelda(nx, ny)) contador++;
            }
        }
        return contador;
    }

    // Avanza una generacion aplicando las reglas del juego
    public void siguienteGeneracion() {
        boolean[][] nuevoGrid = new boolean[configuracion.getAncho()][configuracion.getAlto()];
        int nuevoContador = 0;

        for (int x = 0; x < configuracion.getAncho(); x++) {
            for (int y = 0; y < configuracion.getAlto(); y++) {
                int vecinos = contarVecinos(x, y);

                // Aplicar reglas de supervivencia y nacimiento
                if (getCelda(x, y)) {
                    nuevoGrid[x][y] = configuracion.getReglasSupervivencia()
                            .contains(String.valueOf(vecinos));
                } else {
                    nuevoGrid[x][y] = configuracion.getReglasNacimiento()
                            .contains(String.valueOf(vecinos));
                }

                if (nuevoGrid[x][y]) nuevoContador++;
            }
        }

        grid = nuevoGrid;
        celulasVivas = nuevoContador;
    }

    // Genera 4 celulas vivas alrededor de una posicion
    public void generarVecinasAlrededor(int x, int y) {
        Random random = new Random();
        int celulasGeneradas = 0;
        int intentos = 0;

        // Generar exactamente 4 celulas alrededor
        while (celulasGeneradas < 4 && intentos < 100) {
            intentos++;
            int offsetX = random.nextInt(3) - 1;
            int offsetY = random.nextInt(3) - 1;

            // Evitar la posicion central
            if (offsetX == 0 && offsetY == 0) continue;

            // Calcular nueva posicion con wrapping toroidal
            int nuevoX = (x + offsetX + configuracion.getAncho()) % configuracion.getAncho();
            int nuevoY = (y + offsetY + configuracion.getAlto()) % configuracion.getAlto();

            // Solo añadir si la celda esta vacia
            if (!getCelda(nuevoX, nuevoY)) {
                setCelda(nuevoX, nuevoY, true);
                celulasGeneradas++;
            }
        }
    }

    // Inicializa el tablero con N grupos aleatorios
    public void inicializarAleatoria(int numGrupos) {
        inicializarGrid();

        Random random = new Random();

        for (int grupo = 0; grupo < numGrupos; grupo++) {
            // Determina una posicion X, Y inicial aleatoria
            int centroX = random.nextInt(configuracion.getAncho());
            int centroY = random.nextInt(configuracion.getAlto());

            // Crea una colonia con la celula central
            setCelda(centroX, centroY, true);

            // Añadir 4 celulas vecinas alrededor
            generarVecinasAlrededor(centroX, centroY);
        }
    }

    // Limpia el tablero
    public void limpiarGrid() {
        inicializarGrid();
    }

    // Obtiene el tablero completo
    public boolean[][] getGrid() {
        return grid;
    }

    // Obtiene el numero de celulas vivas
    public int getCelulasVivas() {
        return celulasVivas;
    }
}
