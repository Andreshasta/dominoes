/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.communicador;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Mensajero {
	
	private ObjectOutputStream oos;
	
	public Mensajero(OutputStream outputStream) throws IOException {
		oos = new ObjectOutputStream(outputStream);
		flush();
	}
	
	public void flush() throws IOException {
		oos.flush();
	}
	
	public void write(Object object) throws IOException {
		System.out.println(">> Escribir: " + object);
		oos.reset();
		oos.writeObject(object);
		flush();
	}
	
	public void close() throws IOException {
		oos.close();
	}
}
