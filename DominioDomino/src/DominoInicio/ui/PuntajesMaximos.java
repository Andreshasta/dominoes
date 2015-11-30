/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoInicio.ui;

import DominoBase.modelo.AdministrarArchivo;
import DominoBase.modelo.Jugador;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Edwin
 */
public class PuntajesMaximos extends JFrame implements ActionListener{
    private JPanel jpContentPane;
    private JPanel jpPrincipal;
    private JLabel lbTitulo;
    private JTextArea jtaJugadores;
    private JButton jbVolver;
    private JScrollPane jsDeslice;
    
    public PuntajesMaximos() {
        setTitle("Ingreso juego");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jpContentPane = new JPanel();
        jpContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        jpContentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(jpContentPane);
        
        jpPrincipal = new JPanel();
        jpPrincipal.setLayout(new GridLayout(3,1));
        lbTitulo = new JLabel("Historico de puntajes", JLabel.CENTER);
        jtaJugadores = new JTextArea();
        asignarDatos();
        jsDeslice=new JScrollPane(jtaJugadores);
        jbVolver = new JButton("Volver");
        jbVolver.setActionCommand("VOLVER");
        jbVolver.addActionListener(this);
        
        jpPrincipal.add(lbTitulo);
        jpPrincipal.add(jtaJugadores);
        jpPrincipal.add(jbVolver);
        
        jpContentPane.add(jpPrincipal);
        this.pack();
        this.setVisible(true);
    }

    public void asignarDatos(){
        for (String linea : leerDatos()){
            jtaJugadores.setText( jtaJugadores.getText() + linea +"\n" );
        }
    }
    
    public ArrayList<String> leerDatos(){
        AdministrarArchivo aa = new AdministrarArchivo();
        ArrayList<String> resultados = new ArrayList<>();
        String resultado="";
        aa.cargarArchivo();
        for (Jugador j : aa.getListaJugadores()){
            resultado = j.getUsername() + j.getPuntaje();
            resultados.add(resultado);
        }
        return resultados;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cm = e.getActionCommand();
        if (cm.equals("VOLVER")){
            this.dispose();
        }
    }
    
}
