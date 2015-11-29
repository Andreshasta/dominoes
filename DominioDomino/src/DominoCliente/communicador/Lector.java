/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.communicador;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Lector {
	
	private ObjectInputStream ois;
	
	public Lector(InputStream inputStream) throws IOException {
		ois = new ObjectInputStream(inputStream);
	}
	
	public Object read() throws ClassNotFoundException, IOException {
		Object object = ois.readObject();
		System.out.println(">> leer: " + object);
		return object;
	}
	
	public void close() throws IOException {
		ois.close();
	}
	
}
