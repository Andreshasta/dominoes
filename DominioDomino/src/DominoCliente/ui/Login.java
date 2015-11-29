/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.ui;

import DominoCliente.juego.Administrador;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import DominoCliente.ui.componente.ImagePanel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblMsgtela;

	private Administrador manager;

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 296, 193);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/*ImagePanel panel = new ImagePanel(
				new ImageIcon("images/bglogin.jpg").getImage());*/
                ImagePanel panel = new ImagePanel(
				new ImageIcon("../../DominoCliente/images/bglogin.jpg").getImage());

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(42, 61, 197, 30);
		panel.add(panel_1);

		JLabel lblUsurio = new JLabel("Usuario:");
		panel_1.add(lblUsurio);

		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 204));
		panel_2.setBounds(42, 11, 197, 27);
		panel.add(panel_2);

		JLabel lblDominGame = new JLabel("Domino en red");
		panel_2.add(lblDominGame);
		lblDominGame.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(this);
		btnEntrar.setActionCommand("entrar");

		btnEntrar.setBounds(97, 102, 89, 23);
		panel.add(btnEntrar);

		contentPane.add(panel);

		//lblMsgtela = new JLabel("msg_tela");
                lblMsgtela = new JLabel("INGRESO");
		lblMsgtela.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsgtela.setForeground(Color.BLUE);
		//lblMsgtela.setBackground(new Color(255, 192, 203));
		lblMsgtela.setOpaque(true);
		lblMsgtela.setVisible(false);
		contentPane.add(lblMsgtela, BorderLayout.SOUTH);

		try {
			manager = new Administrador();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ERROR);
		}

	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equalsIgnoreCase("entrar")) {
			String username = textField.getText();
			if (username.trim().isEmpty()) {
				lblMsgtela.setText("Nombre de usuario");
				lblMsgtela.setVisible(true);
			} else {
				if (manager.login(username)) {
					dispose();
					OnlineUsersUI o = new OnlineUsersUI(manager);
					o.setVisible(true);
					//Wait waitFrame = new Wait(manager);
					//waitFrame.setVisible(true);
				} else {
					String msg = "No hay conexion:\n";
					
					for(String s : manager.getMessages()) {
						msg += s + "\n";
					}
					lblMsgtela.setText(msg);
					lblMsgtela.setVisible(true);
				}
			}
		}
	}

}
