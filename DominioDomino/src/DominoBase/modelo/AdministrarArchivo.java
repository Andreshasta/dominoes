/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoBase.modelo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;

public class AdministrarArchivo {

    private ArrayList<Jugador> listaJugadores;
    private ArrayList<String> listaTexto;
    private String archivoHistJugadores;
    

    public AdministrarArchivo() {
        listaJugadores = new ArrayList<>();
        listaTexto = new ArrayList<>();
        archivoHistJugadores = "jugadores.dat";
    }

    public void setArchivoHistJugadores(String nombre) {
        this.archivoHistJugadores = nombre;
        cargarArchivoTutorial(nombre);
    }

    public void cargarArchivoTutorial(String nombre) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
         // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(nombre);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
               // System.out.println(linea);
                listaTexto.add(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
         // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void cargarArchivo() {
        listaJugadores = new ArrayList<>();
        int i = 0;
        try {
            ArrayList<Object> listaO;
            System.out.println("ruta archivo: " + archivoHistJugadores);
            listaO = abrir(archivoHistJugadores);
            for (Object obj : listaO) {
                i++;
                listaJugadores.add((Jugador) (obj));
                Jugador ta = (Jugador) (obj);
                System.out.println(i + "Tipo " + ta.getUsername());
            }
        } catch (NullPointerException npe) {
            System.out.println("No encuentra el archivo.");
            npe.printStackTrace();
        } catch (Exception ex) {
            System.err.println("cargarArchivo2");
            ex.printStackTrace();
        }
    }

    public void guardarDatos() {
        try {
            System.out.println("guardarDatos");
            System.out.println("tamaño lista " + listaJugadores.size());
            for (Jugador ta : listaJugadores) {
                System.out.println("Tipo " + ta.getUsername());
            }
            guardar(archivoHistJugadores, listaJugadores);
        } catch (Exception ex) {
            System.err.println("guardarDatos()");
            ex.printStackTrace();
        }
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public ArrayList<String> getListaTexto() {
        return listaTexto;
    }

    public void adicionarJugador(Jugador player) {
        if (player != null) {
            listaJugadores.add(player);
        }
    }

    /**
     * Método que permite abrir un archivo y pasar los datos a una lista de
     * objetos genérico. Como resultado proporciona la lista de los objetos
     * leídos.
     *
     * @param arch Nombre del archivo a leer.
     * @return Lista de objetos genéricos.
     * @throws Exception
     */
    public ArrayList abrir(String arch) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(arch));
        ObjectInputStream ois = new ObjectInputStream(bis);
        ArrayList<Object> listaInt = new ArrayList<>();
        File archivo = new File(arch);
        try {
            if (archivo.exists()) {
                // Objeto de la clase que se quiere obtener.
                Object p = ois.readObject();
                while (true) {
                    listaInt.add(p);
                    // Objeto de la clase que se quiere obtener.
                    p = ois.readObject();
                }
            } else {
                archivo.createNewFile();
                return null;
            }
        } catch (EOFException ex) {
            System.out.println("-- EOF --");
            ois.close();
            bis.close();
            //archivo.delete();
            return listaInt;
        } finally {
            ois.close();
            bis.close();
            return listaInt;
        }
    }

    /**
     * Método que permite guardar una lista de objetos en una archivo. Como
     * parametro tiene el nombre del archivo y la lista de objetos a guardar.
     *
     * @param arch Nombre del archivo incluyendo la extensión.
     * @param listaObj Lista de objetos de cualquier tipo, que se va a guardar.
     * @throws Exception
     */
    public void guardar(String arch, ArrayList listaObj) { //throws Exception {
        System.out.println("guardar");
        System.out.println("tamaño lista " + listaObj.size());
        BufferedOutputStream bos = null;
        ObjectOutputStream oos = null;
        File archivo = new File(arch);
        try {
            if (archivo.exists()) {
                bos = new BufferedOutputStream(new FileOutputStream(arch));
                oos = new ObjectOutputStream(bos);
                if (listaObj != null) {
                    if (!listaObj.isEmpty()) {
                        for (Object q : listaObj) {
                            oos.writeObject(q);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("guardar");
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
                bos.close();
                //archivo.delete();
            } catch (Exception ex2) {
                System.err.println("guardar2");
                ex2.printStackTrace();
            }
        }
    }
}
