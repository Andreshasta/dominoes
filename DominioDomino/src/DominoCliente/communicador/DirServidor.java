/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.communicador;

/**
 *
 * @author Edwin
 */
public final class DirServidor {
    private static String direccion;

    public static String getDireccion() {
        return direccion;
    }

    public static void setDireccion(String direccion) {
        DirServidor.direccion = direccion;
    }
    
}
