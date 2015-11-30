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
 * @author AndresMauricio
 */
public class Creditos extends JFrame implements ActionListener {
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

    public Creditos() {
        setTitle("Creditos");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jpContentPane = new JPanel();
        jpContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        jpContentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(jpContentPane);

        jpPrincipal = new JPanel();
        jpPrincipal.setLayout(new GridLayout(3, 1));
        lbTitulo = new JLabel("ACERCA DE DOMINO", JLabel.CENTER);
        jtaJugadores = new JTextArea();
        asignarDatos();
        jsDeslice = new JScrollPane(jtaJugadores);
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

    public void asignarDatos() {
        for (String linea : leerDatos()) {
            jtaJugadores.setText(jtaJugadores.getText() + linea + "\n");
        }
    }

    public ArrayList<String> leerDatos() {

        ArrayList<String> resultados = new ArrayList<>();
        resultados.add("Creditos.");
        resultados.add("Desarrollado por:");
        resultados.add("Edwin Yesid Hastamorir ");
        resultados.add("Andrés Mauricio Hastamorir");
        resultados.add("PROGRAMACION AVANZADA");
        resultados.add("2015 IV.");
        resultados.add("AGRADECIMIENTOS");
        resultados.add("GitHub");
        resultados.add("www.GitHub.com");
        resultados.add("code.google.com/p/projectdominogame/");

        return resultados;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cm = e.getActionCommand();
        if (cm.equals("VOLVER")) {
            this.dispose();
        }
    }

}
