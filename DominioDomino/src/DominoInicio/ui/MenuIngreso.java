package DominoInicio.ui;

import DominoBase.modelo.AdministrarArchivo;
import DominoCliente.InicioCliente;
import DominoCliente.communicador.DirServidor;
import DominoInicio.cargador.DatosServidor;
import DominoInicio.cargador.Ejecutor;
import DominoServidor.InicioServidor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuIngreso extends JFrame implements ActionListener, ChangeListener {

    private JPanel jpContentPane;
    private JCheckBox isServidor;
    private JCheckBox isCliente;
    private JButton jbIniciar;
    private JButton jbMaxPuntajes;
    private JButton jbTutorial;
    private JButton jbCreditos;
    private JButton jbSalir;
    private JLabel lbAIniciar;
    private JPanel jpPanel1;
    private JPanel jpPanel2;
    private JTextField txDireccion;
    private boolean iniServidor;
    private boolean iniCliente;
    private String IP;
    private PuntajesMaximos pm;
    private Tutorial tu;

    public MenuIngreso() {
        setTitle("Ingreso juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        
        jpContentPane = new JPanel();
        jpContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        jpContentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(jpContentPane);

        iniServidor = false;
        iniCliente = false;

        jpPanel1 = new JPanel();
        jpPanel1.setLayout(new GridLayout(8, 1));
        jpPanel2 = new JPanel();
        jpPanel2.setLayout(new GridLayout(1, 2));
        isServidor = new JCheckBox("Servidor");
        isServidor.addChangeListener(this);
        isCliente = new JCheckBox("Cliente");
        isCliente.addChangeListener(this);
        jbIniciar = new JButton("INICIAR JUEGO");
        jbIniciar.setActionCommand("INICIAR");
        jbIniciar.addActionListener(this);
        jbIniciar.setEnabled(false);

        jbMaxPuntajes = new JButton("Max. Puntajes");
        jbMaxPuntajes.setActionCommand("MAXIMOS");
        jbMaxPuntajes.addActionListener(this);
        jbMaxPuntajes.setEnabled(true);

        jbTutorial = new JButton("Tutorial");
        jbTutorial.setActionCommand("TUTORIAL");
        jbTutorial.addActionListener(this);
        jbTutorial.setEnabled(true);

        jbCreditos = new JButton("Creditos");
        jbCreditos.setActionCommand("CREDITOS");
        jbCreditos.addActionListener(this);
        jbCreditos.setEnabled(true);

        jbSalir = new JButton("Salir");
        jbSalir.setActionCommand("SALIR");
        jbSalir.addActionListener(this);
        jbSalir.setEnabled(true);

        lbAIniciar = new JLabel("");
        txDireccion = new JTextField("");
        txDireccion.setEnabled(false);

        jpPanel2.add(isServidor);
        jpPanel2.add(isCliente);
        jpPanel1.add(jpPanel2);
        jpPanel1.add(txDireccion);
        jpPanel1.add(jbIniciar);
        jpPanel1.add(lbAIniciar);
        jpPanel1.add(jbMaxPuntajes);
        jpPanel1.add(jbTutorial);
        jpPanel1.add(jbCreditos);
        jpPanel1.add(jbSalir);
        jpContentPane.add(jpPanel1);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "SALIR") {
            System.exit(0);
        }
        if (e.getActionCommand() == "INICIAR") {
            System.out.println("servidor: " + iniServidor + " cliente: " + iniCliente);
            InicioServidor server1 = new InicioServidor();
            InicioCliente cliente = new InicioCliente();
            if (iniServidor) {
                Ejecutor.ejecutar(server1);
            }
            if (iniCliente) {
                if (!iniServidor) {
                    if (txDireccion.getText().length() != 0) {
                        DirServidor.setDireccion(txDireccion.getText());
                        Ejecutor.ejecutar(cliente);
                    }else{
                        JOptionPane.showMessageDialog(this, "Es necesario una direccion IP", "Direccion IP", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    DirServidor.setDireccion("127.0.0.1");
                    Ejecutor.ejecutar(cliente);
                }
            }
        }
        if (e.getActionCommand() == "MAXIMOS") {
            pm = new PuntajesMaximos();
        }
        if (e.getActionCommand() == "TUTORIAL") {
       tu = new Tutorial();
        }
        if (e.getActionCommand() == "CREDITOS") {
            
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (isServidor.isSelected()) {
            lbAIniciar.setText("Servidor");
            iniServidor = true;
        } else {
            lbAIniciar.setText("");
            iniServidor = false;
        }
        if (isCliente.isSelected()) {
            if (lbAIniciar.getText().length() != 0) {
                lbAIniciar.setText(lbAIniciar.getText() + " + ");
            }
            lbAIniciar.setText(lbAIniciar.getText() + " Cliente");
            iniCliente = true;
        }else {
            iniCliente = false;
        }
        //panel.repaint();
        jbIniciar.setEnabled((iniServidor || iniCliente));
        if (((iniServidor || iniCliente) && iniCliente) && (!iniServidor)) {
            txDireccion.setEnabled(true);
        } else {
            txDireccion.setEnabled(false);
            if (iniServidor) {
                IP = DatosServidor.consultaIPServidor();
                txDireccion.setText(IP);
            }
        }
    }
}
