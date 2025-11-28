import javax.swing.SwingUtilities;
import view.FormTelaPrincipal;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormTelaPrincipal();
            }
        });
    }
}
