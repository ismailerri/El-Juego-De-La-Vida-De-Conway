public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error al establecer Look and Feel: " + e.getMessage());
        }

        // Iniciar aplicación
        javax.swing.SwingUtilities.invokeLater(() -> {
            controlador.Juego juego = new controlador.Juego();
            juego.setVisible(true);
        });
    }
}