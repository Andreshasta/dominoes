package DominoServidor;

import DominoBase.modelo.Ejecutable;
import DominoServidor.servidor.Servidor;

public class InicioServidor extends Thread implements Ejecutable {

    public static void main(String[] args) {
        (new Servidor()).execute();
    }

    @Override
    public void run() {
        (new Servidor()).execute();
    }
}
