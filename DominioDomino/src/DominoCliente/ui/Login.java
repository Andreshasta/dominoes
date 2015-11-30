package DominoCliente.ui;

//import DominoBase.system.Parametros;
import DominoCliente.juego.Administrador;
import java.awt.BorderLayout;

//import java.awt.BorderLayout;

//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

//import DominoCliente.ui.componente.ImagePanel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import javax.swing.SwingConstants;
public class Login extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JTextField txUsuario;
    private JLabel jlMensaje;
    private JLabel lbUsuario;
    JLabel jlDominoGame;
    JButton jbEntrar;

    private Administrador manager;

    /**
     * Create the frame.
     */
    public Login() {
        setTitle("Ingreso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = this.getContentPane();
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new GridLayout(1,1));
        //setContentPane(contentPane);
        this.setMinimumSize(new Dimension(200,200));

        //ImagePanel panel = new ImagePanel(new ImageIcon(Parametros.PATH_IMAGES + "bglogin.jpg").getImage());
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        jlDominoGame = new JLabel("Domino en red", JLabel.CENTER);
        jlDominoGame.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbUsuario = new JLabel("Nombre de usuario:", JLabel.CENTER);
        txUsuario = new JTextField();
        txUsuario.setColumns(10);
        jbEntrar = new JButton("Entrar");
        jbEntrar.addActionListener(this);
        jbEntrar.setActionCommand("ENTRAR");

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(1,1));
        
        panel_1.setBackground(new Color(255, 255, 204));
        panel_1.add(jlDominoGame);
        
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(2,1));
        panel_2.add(lbUsuario);
        panel_2.add(txUsuario);


        panel.add(panel_1, BorderLayout.NORTH);
        panel.add(panel_2, BorderLayout.CENTER);

        panel.add(jbEntrar, BorderLayout.SOUTH);

        contentPane.add(panel);
        this.pack();
        
        try {
            manager = new Administrador();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(ERROR);
        }

    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equalsIgnoreCase("ENTRAR")) {
            String username = txUsuario.getText();
            if (username.trim().isEmpty()) {
                jlMensaje.setText("Nombre de usuario");
                jlMensaje.setVisible(true);
            } else {
                if (manager.login(username)) {
                    dispose();
                    OnlineUsersUI o = new OnlineUsersUI(manager);
                    o.setVisible(true);
                    //Wait waitFrame = new Wait(manager);
                    //waitFrame.setVisible(true);
                } else {
                    String msg = "No hay conexion:\n";

                    for (String s : manager.getMessages()) {
                        msg += s + "\n";
                    }
                    jlMensaje.setText(msg);
                    jlMensaje.setVisible(true);
                }
            }
        }
    }

}
