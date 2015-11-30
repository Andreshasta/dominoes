package DominoCliente;

import DominoBase.modelo.Ejecutable;
import DominoCliente.communicador.DirServidor;
import DominoCliente.ui.Login;

public class InicioCliente extends Thread implements Ejecutable {

    public static void main(String[] args) {
        DirServidor.setDireccion("127.0.0.1");
        Login frame = new Login();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        Login frame = new Login();
        frame.setVisible(true);
    }
}
