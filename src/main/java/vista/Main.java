
package vista;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Usuario;

/**
 * Clase principal del programa
 * 
 */
public class Main {
    
    // variable que guardará el usuario activo en el programa
    public static Usuario usuarioActual;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  
        Login login = new Login();
        login.setVisible(true);
    }

}
