package DominoCliente;

import DominoBase.modelo.Ejecutable;
import DominoCliente.ui.Login;

public class InicioCliente extends Thread implements Ejecutable {

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        Login frame = new Login();
        frame.setVisible(true);
    }
}
