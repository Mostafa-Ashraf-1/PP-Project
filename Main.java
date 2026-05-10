import javax.swing.SwingUtilities;

public class Main {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            GUI frame = new GUI();

            frame.playBackgroundMusic("PrettyLittleBaby.wav");
            frame.setVisible(true);
        });
    }
}
