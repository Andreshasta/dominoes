/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoInicio.ui;

import DominoBase.modelo.AdministrarArchivo;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
 * @author AndresMauricio
 */
public class Tutorial extends JFrame implements ActionListener {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    private JPanel jpContentPane;
    private JPanel jpPrincipal;
    private JLabel lbTitulo;
    private JTextArea jtaJugadores;
    private JButton jbVolver;
    private JScrollPane jsDeslice;
    
    public Tutorial() {
        setTitle("Tutorial");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMaximumSize(new Dimension(200, 200));
        jpContentPane = new JPanel();
        jpContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        jpContentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(jpContentPane);
        
        jpPrincipal = new JPanel();
        jpPrincipal.setLayout(new GridLayout(2,1));
        lbTitulo = new JLabel("¿COMO JUGAR?", JLabel.CENTER);
        jtaJugadores = new JTextArea();
        
        jsDeslice=new JScrollPane();
        jtaJugadores.add(jsDeslice);
        asignarDatos();
//        jbVolver = new JButton("Volver");
//        jbVolver.setActionCommand("VOLVER");
//        jbVolver.addActionListener(this);
        
        jpPrincipal.add(lbTitulo);
        jpPrincipal.add(jtaJugadores);
        //jpPrincipal.add(jbVolver);
        
        jpContentPane.add(jpPrincipal);
        this.pack();
        this.setVisible(true);
    }

    public void asignarDatos(){
        for (String linea : leerDatos()){
            jtaJugadores.setText( jtaJugadores.getText() + linea +"\n" );
            //jtaJugadores.setText( jtaJugadores.getText() + linea );
        }
    }
    
    public ArrayList<String> leerDatos(){
        AdministrarArchivo aa = new AdministrarArchivo();
        ArrayList<String> resultados = new ArrayList<>();
        aa.setArchivoHistJugadores("tutorial.txt");
        String resultado="";
    
        for (String j : aa.getListaTexto()){
            resultado = j;
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

    

