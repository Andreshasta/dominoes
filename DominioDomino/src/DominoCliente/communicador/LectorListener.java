/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.communicador;

import java.io.IOException;

public class LectorListener extends Thread {

    private Traductor translator;

    public LectorListener(Traductor translator) {
        this.translator = translator;
    }

    @Override
    public void run() {

        while (true) {
            try {
                translator.processMensage(translator.remoteRead());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
