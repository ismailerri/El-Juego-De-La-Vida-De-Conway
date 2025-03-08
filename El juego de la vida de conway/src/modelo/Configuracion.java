package modelo;

// Clase que almacena la configuracion del juego
public class Configuracion {
    // Atributos
    private int ancho;
    private int alto;
    private String reglasSupervivencia;
    private String reglasNacimiento;
    private int velocidad;

    // Constructor con valores por defecto
    public Configuracion() {
        this.ancho = 50;
        this.alto = 50;
        this.reglasSupervivencia = "23";
        this.reglasNacimiento = "3";
        this.velocidad = 1000;
    }

    // Constructor con parametros para inicializacion personalizada
    public Configuracion(int ancho, int alto, String reglasSupervivencia, String reglasNacimiento, int velocidad) {
        this.ancho = ancho;
        this.alto = alto;
        this.reglasSupervivencia = reglasSupervivencia;
        this.reglasNacimiento = reglasNacimiento;
        this.velocidad = velocidad;
    }

    // Getters y setters
    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public String getReglasSupervivencia() {
        return reglasSupervivencia;
    }

    public void setReglasSupervivencia(String reglasSupervivencia) {
        this.reglasSupervivencia = reglasSupervivencia;
    }

    public String getReglasNacimiento() {
        return reglasNacimiento;
    }

    public void setReglasNacimiento(String reglasNacimiento) {
        this.reglasNacimiento = reglasNacimiento;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    // Devuelve una representación del formato de reglas AA/B
    public String getReglaFormatoConway() {
        return reglasSupervivencia + "/" + reglasNacimiento;
    }
}