package DominoInicio;

import DominoCliente.InicioCliente;
import DominoServidor.InicioServidor;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuIngreso extends JFrame implements ActionListener, ChangeListener {

    private JPanel contentPane;
    private JCheckBox isServidor;
    private JCheckBox isCliente;
    private JButton iniciar;
    private JLabel txAIniciar;
    JPanel panel;
    boolean iniServidor;
    boolean iniCliente;

    public MenuIngreso() {
        setTitle("Ingreso juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        iniServidor= false;
        iniCliente = false;
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        isServidor = new JCheckBox("Servidor");
        isServidor.addChangeListener(this);
        isCliente = new JCheckBox("Cliente");
        isCliente.addChangeListener(this);
        iniciar = new JButton("Inicar juego");
        iniciar.setActionCommand("INICIAR");
        iniciar.addActionListener(this);
        iniciar.setEnabled(false);
        txAIniciar = new JLabel("");
        panel.add(isServidor);
        panel.add(iniciar);
        panel.add(isCliente);
        panel.add(txAIniciar);
        contentPane.add(panel);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "INICIAR"){
            System.out.println("servidor: "+iniServidor + " cliente: "+iniCliente);
            InicioServidor server1 = new InicioServidor();
            InicioCliente cliente = new InicioCliente();
            if (iniServidor){
                Ejecutor.ejecutar(server1);
            }
            if (iniCliente){
                Ejecutor.ejecutar(cliente);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (isServidor.isSelected()) {
            txAIniciar.setText("Servidor");
            iniServidor= true;
        } else {
            txAIniciar.setText("");
            iniServidor = false;
        }
        if (isCliente.isSelected()) {
            if (txAIniciar.getText().length() != 0){
                txAIniciar.setText(txAIniciar.getText() + " + ");
            }
            txAIniciar.setText(txAIniciar.getText() + " Cliente");
            iniCliente = true;
        }else{
            iniCliente = false;
        }
        //panel.repaint();
        iniciar.setEnabled((iniServidor || iniCliente));
    }
}
