/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.communicador;

import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteSocket {

	private Socket socket;
	private Lector reader;
	private Mensajero writer;

	public ClienteSocket() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", 7000);
		writer = new Mensajero(socket.getOutputStream());
		reader = new Lector(socket.getInputStream());
		writer.flush();
	}

	public Lector getReader() {
		return reader;
	}

	public Mensajero getWriter() {
		return writer;
	}

	public void disconnect() throws IOException {
		reader.close();
		writer.close();
		socket.close();
	}

}
