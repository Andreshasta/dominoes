/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoCliente.ui;

import DominoCliente.juego.Administrador;

//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import java.util.List;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;

import DominoBase.modelo.Domino;
import DominoBase.modelo.Tablero;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import DominoBase.system.Parametros;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JuegoUI extends JFrame {

    private static final long serialVersionUID = -3028719584988240249L;

    private JPanel contentPane;

    private JList lista;
    private JList listaIzquierda;
    private JList listaDerecha;

    private JButton btnPonerDerecha;
    private JButton btnPonerIzquierda;
    private JButton btnPonerEnTablero;
    private JButton btnPasar;

    private JLabel lblDominosAvaliables;
    private JLabel QtdDominoPlayer1;
    private JLabel QtdDominoPlayer2;
    private JLabel QtdDominoPlayer3;
    private JLabel lblpieceN1, lblpieceN2, lblpieceN3, lblpieceN4, lblpieceN5, lblpieceN6, lblpieceN7;
    private JLabel lblpieceE1, lblpieceE2, lblpieceE3, lblpieceE4, lblpieceE5, lblpieceE6, lblpieceE7;
    private JLabel lblpieceW1, lblpieceW2, lblpieceW3, lblpieceW4, lblpieceW5, lblpieceW6, lblpieceW7;
    private JLabel lblFichaS1, lblFichaS2, lblFichaS3, lblFichaS4, lblFichaS5, lblFichaS6, lblFichaS7;

    private String me;
    private String player1;
    private String player2;
    private String player3;
    //PanelOverDraw lblmesa;
    JLabel lblmesa;

    private Administrador administra;
    private final JTextArea textArea = new JTextArea();

    public JuegoUI(Administrador administra) {

        this.administra = administra;

        /*
         * obtener secuencia de jugadores
         */
        me = administra.getMyPlayer().getUsername();
        player1 = administra.getRoom().getNextPlayerById(administra.getMyPlayer().getId()).getUsername();
        player2 = administra.getRoom().getNextPlayerById(administra.getRoom().getPlayer(player1).getId()).getUsername();
        player3 = administra.getRoom().getNextPlayerById(administra.getRoom().getPlayer(player2).getId()).getUsername();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 645);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Color color = new Color(255, 255, 255);

        lblpieceW1 = new JLabel();
        lblpieceW2 = new JLabel();
        lblpieceW3 = new JLabel();
        lblpieceW4 = new JLabel();
        lblpieceW5 = new JLabel();
        lblpieceW6 = new JLabel();
        lblpieceW7 = new JLabel();

        lblpieceW1.setBounds(15, 97, 62, 27);
        lblpieceW2.setBounds(15, 127, 62, 27);
        lblpieceW3.setBounds(15, 157, 62, 27);
        lblpieceW4.setBounds(15, 187, 62, 27);
        lblpieceW5.setBounds(15, 217, 62, 27);
        lblpieceW6.setBounds(15, 247, 62, 27);
        lblpieceW7.setBounds(15, 277, 62, 27);

        lblpieceW1.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceW2.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceW3.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceW4.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceW5.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceW6.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceW7.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        //Panel Izquierdo
        JPanel panelWest = new JPanel();
        panelWest.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        //panel (color);
        panelWest.setBounds(0, 0, 100, 537);

        //etiquetas con imagenes panel izq
        panelWest.add(lblpieceW1);
        panelWest.add(lblpieceW2);
        panelWest.add(lblpieceW3);
        panelWest.add(lblpieceW4);
        panelWest.add(lblpieceW5);
        panelWest.add(lblpieceW6);
        panelWest.add(lblpieceW7);

        contentPane.add(panelWest);
        panelWest.setLayout(null);

        JLabel lblJugador_1 = new JLabel(player1);
        lblJugador_1.setBounds(10, 11, 80, 14);
        panelWest.add(lblJugador_1);

        String s = "Dominos: " + administra.getRoom().getPlayer(player1).getDominos().size();
        QtdDominoPlayer1 = new JLabel(s);
        QtdDominoPlayer1.setBounds(10, 39, 80, 14);
        panelWest.add(QtdDominoPlayer1);

        //ETIQUETAS Panel norte
        lblpieceN1 = new JLabel();
        lblpieceN2 = new JLabel();
        lblpieceN3 = new JLabel();
        lblpieceN4 = new JLabel();
        lblpieceN5 = new JLabel();
        lblpieceN6 = new JLabel();
        lblpieceN7 = new JLabel();

        lblpieceN1.setBounds(100, 15, 27, 62);
        lblpieceN2.setBounds(130, 15, 27, 62);
        lblpieceN3.setBounds(160, 15, 27, 62);
        lblpieceN4.setBounds(190, 15, 27, 62);
        lblpieceN5.setBounds(220, 15, 27, 62);
        lblpieceN6.setBounds(250, 15, 27, 62);
        lblpieceN7.setBounds(280, 15, 27, 62);

        lblpieceN1.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));
        lblpieceN2.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));
        lblpieceN3.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));
        lblpieceN4.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));
        lblpieceN5.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));
        lblpieceN6.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));
        lblpieceN7.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "piece.png")));

        //Panel norte
        JPanel panelNorth = new JPanel();

        panelNorth.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panelNorth.setBounds(100, 0, 785, 100);

        //etiquetas panel norte
        panelNorth.add(lblpieceN1);
        panelNorth.add(lblpieceN2);
        panelNorth.add(lblpieceN3);
        panelNorth.add(lblpieceN4);
        panelNorth.add(lblpieceN5);
        panelNorth.add(lblpieceN6);
        panelNorth.add(lblpieceN7);

        contentPane.add(panelNorth);
        panelNorth.setLayout(null);

        JLabel lblJugador_2 = new JLabel(administra.getRoom().getPlayer(player2).getUsername());
        lblJugador_2.setBounds(10, 11, 134, 14);
        panelNorth.add(lblJugador_2);

        s = "Dominos: " + administra.getRoom().getPlayer(player2).getDominos().size();
        QtdDominoPlayer2 = new JLabel(s);
        QtdDominoPlayer2.setBounds(10, 36, 134, 14);
        panelNorth.add(QtdDominoPlayer2);

        //etiquetas panel der
        lblpieceE1 = new JLabel();
        lblpieceE2 = new JLabel();
        lblpieceE3 = new JLabel();
        lblpieceE4 = new JLabel();
        lblpieceE5 = new JLabel();
        lblpieceE6 = new JLabel();
        lblpieceE7 = new JLabel();

        lblpieceE1.setBounds(15, 97, 62, 27);
        lblpieceE2.setBounds(15, 127, 62, 27);
        lblpieceE3.setBounds(15, 157, 62, 27);
        lblpieceE4.setBounds(15, 187, 62, 27);
        lblpieceE5.setBounds(15, 217, 62, 27);
        lblpieceE6.setBounds(15, 247, 62, 27);
        lblpieceE7.setBounds(15, 277, 62, 27);

        lblpieceE1.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceE2.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceE3.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceE4.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceE5.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceE6.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));
        lblpieceE7.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "pieceh.png")));

        //Panel derecho
        JPanel panelEast = new JPanel();
        //panel (color);
        panelEast.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panelEast.setBounds(884, 0, 100, 537);

        //eetiquetas 2 panel der
        panelEast.add(lblpieceE1);
        panelEast.add(lblpieceE2);
        panelEast.add(lblpieceE3);
        panelEast.add(lblpieceE4);
        panelEast.add(lblpieceE5);
        panelEast.add(lblpieceE6);
        panelEast.add(lblpieceE7);

        contentPane.add(panelEast);
        panelEast.setLayout(null);

        JLabel lblJugador_3 = new JLabel(administra.getRoom().getPlayer(player3).getUsername());
        lblJugador_3.setBounds(10, 11, 134, 14);
        panelEast.add(lblJugador_3);

        s = "Domi " + administra.getRoom().getPlayer(player3).getDominos().size();
        QtdDominoPlayer3 = new JLabel(s);
        QtdDominoPlayer3.setBounds(10, 35, 134, 14);
        panelEast.add(QtdDominoPlayer3);

        //Etiquetas con imagenes
        lblFichaS1 = new JLabel();
        lblFichaS2 = new JLabel();
        lblFichaS3 = new JLabel();
        lblFichaS4 = new JLabel();
        lblFichaS5 = new JLabel();
        lblFichaS6 = new JLabel();
        lblFichaS7 = new JLabel();

        lblFichaS1.setBounds(2, 28, 27, 62);
        lblFichaS2.setBounds(32, 28, 27, 62);
        lblFichaS3.setBounds(62, 28, 27, 62);
        lblFichaS4.setBounds(92, 28, 27, 62);
        lblFichaS5.setBounds(122, 28, 27, 62);
        lblFichaS6.setBounds(152, 28, 27, 62);
        lblFichaS7.setBounds(182, 28, 27, 62);

        lblFichaS1.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(0).toString() + ".png")));
        lblFichaS2.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(1).toString() + ".png")));
        lblFichaS3.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(2).toString() + ".png")));
        lblFichaS4.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(3).toString() + ".png")));
        lblFichaS5.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(4).toString() + ".png")));
        lblFichaS6.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(5).toString() + ".png")));
        lblFichaS7.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + administra.getRoom().getPlayer(me).getDominos().get(6).toString() + ".png")));

        //Panel inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        //panel (color);
        panelInferior.setBounds(100, 437, 785, 100);

        panelInferior.add(lblFichaS1);
        panelInferior.add(lblFichaS2);
        panelInferior.add(lblFichaS3);
        panelInferior.add(lblFichaS4);
        panelInferior.add(lblFichaS5);
        panelInferior.add(lblFichaS6);
        panelInferior.add(lblFichaS7);

        contentPane.add(panelInferior);
        panelInferior.setLayout(null);

        JLabel lblJugador = new JLabel(administra.getRoom().getPlayer(me).getUsername());
        lblJugador.setBounds(10, 11, 146, 14);
        panelInferior.add(lblJugador);

        s = "Dominos: " + DominoForLabel(administra.getRoom().getPlayer(me).getDominos());

        JScrollPane recorre = new JScrollPane();
        recorre.setBounds(212, 11, 193, 78);
        panelInferior.add(recorre);

        lista = new JList(DominoForListModel(administra.getRoom().getPlayer(me).getDominos()));
        recorre.setViewportView(lista);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        lista.setAutoscrolls(true);

        btnPonerIzquierda = new JButton("Poner izquierda");
        btnPonerIzquierda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ponerIzquierda();
                actualizar();
            }
        });
        btnPonerIzquierda.setBounds(420, 11, 103, 23);
        panelInferior.add(btnPonerIzquierda);

        btnPonerDerecha = new JButton("Poner derecha");
        btnPonerDerecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ponerderecha();
                actualizar();
            }
        });
        btnPonerDerecha.setBounds(420, 45, 103, 23);
        panelInferior.add(btnPonerDerecha);

        JButton btnActualiza = new JButton("Refrescar");
        btnActualiza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                actualizar();
            }
        });
        btnActualiza.setBounds(544, 11, 125, 23);
        panelInferior.add(btnActualiza);

        btnPonerEnTablero = new JButton("Poner ficha en tablero");
        btnPonerEnTablero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ponerEnTablero();
                actualizar();
            }
        });
        btnPonerEnTablero.setBounds(544, 45, 125, 23);
        panelInferior.add(btnPonerEnTablero);

        btnPasar = new JButton("Pasar");
        btnPasar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                pasar();
            }
        });
        btnPasar.setBounds(675, 11, 89, 57);
        panelInferior.add(btnPasar);

        //Etiqueta con imagen de mesa
        lblmesa = new JLabel();
        lblmesa.setBounds(1, 1, 779, 336);
        //lblmesa.setIcon(new ImageIcon(getClass().getResource("/DominoCliente/images/border.png")));
        lblmesa.setIcon(new ImageIcon(getClass().getResource(Parametros.PATH_IMAGES + "border.png")));
        //lblmesa = new PanelOverDraw();
        //lblmesa.setBounds(1, 1, 779, 336);

        //Panel central con  imagen de mesa
        JPanel panelCenter = new JPanel();
        panelCenter.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelCenter.setBounds(100, 100, 782, 339);

        panelCenter.add(lblmesa);

        contentPane.add(panelCenter);
        panelCenter.setLayout(null);

        JScrollPane recorre_1 = new JScrollPane();
        recorre_1.setBounds(90, 69, 170, 183);
        lblmesa.add(recorre_1);

        listaIzquierda = new JList(BoardLeftList());
        recorre_1.setViewportView(listaIzquierda);

        JScrollPane recorre_2 = new JScrollPane();
        recorre_2.setBounds(324, 67, 154, 189);
        lblmesa.add(recorre_2);

        listaDerecha = new JList(BoardRightList());
        recorre_2.setViewportView(listaDerecha);

        JLabel lblLeft = new JLabel("Izquierda");
        lblLeft.setBounds(92, 50, 46, 14);
        lblmesa.add(lblLeft);

        JLabel lblRight = new JLabel("Derecha");
        lblRight.setBounds(324, 50, 46, 14);
        lblmesa.add(lblRight);

        lblDominosAvaliables = new JLabel("Fichas disponibles:");
        lblDominosAvaliables.setBounds(606, 11, 154, 14);
        lblmesa.add(lblDominosAvaliables);

        textArea.setBounds(0, 537, 984, 69);
        textArea.setEnabled(false);
        textArea.setText("Mensajes...");
        contentPane.add(textArea);

        actualizar();

    }

    private DefaultListModel<Domino> BoardLeftList() {

        boolean sigue = false;
        DefaultListModel<Domino> model = new DefaultListModel<Domino>();

        Tablero dob = administra.getRoom().getDominoCenter();

        if (dob != null) {
            model.add(0, dob.getDomino());
            int i = 1;
            while (!sigue) {
                dob = dob.getLeft();
                if (dob != null) {
                    model.add(i, dob.getDomino());
                    i++;
                } else {
                    sigue = true;
                }
            }
        }
        /*Tablero obj = dob;
        while (obj != null) {
            lblmesa.addListIzq(obj.getDomino());
            obj = obj.getLeft();
        }*/
        
        return model;
    }

    private DefaultListModel<Domino> BoardRightList() {

        boolean sigue = false;
        DefaultListModel<Domino> modelo = new DefaultListModel<Domino>();

        Tablero dob = administra.getRoom().getDominoCenter();
        if (dob != null) {
            modelo.add(0, dob.getDomino());
            int i = 1;

            while (!sigue) {
                dob = dob.getRight();
                if (dob != null) {
                    modelo.add(i, dob.getDomino());
                    i++;
                } else {
                    sigue = true;
                }
            }
        }

        return modelo;
    }

    private void ponerIzquierda() {
        Domino x = (Domino) lista.getSelectedValue();
        administra.putOnBoard(x, Parametros.LEFT, me);
        actualizar();
    }

    private void ponerderecha() {
        Domino x = (Domino) lista.getSelectedValue();
        administra.putOnBoard(x, Parametros.RIGHT, me);
        actualizar();
    }

    private DefaultListModel<Domino> DominoForListModel(List<Domino> dominos) {
        DefaultListModel<Domino> model = new DefaultListModel<Domino>();
        int i = 0;

        for (Domino d : dominos) {
            model.add(i, d);
            i++;
        }

        return model;
    }

    private String DominoForLabel(List<Domino> dominos) {
        String result = "";
        for (Domino d : dominos) {
            result += d.getSide1() + "x" + d.getSide2() + "   |   ";
        }
        return result;
    }

    private void habilitado() {
        lista.setEnabled(true);
        btnPonerIzquierda.setEnabled(true);
        btnPonerDerecha.setEnabled(true);
        btnPasar.setEnabled(true);
    }

    private void deshabilitado() {
        lista.setEnabled(false);
        btnPonerIzquierda.setEnabled(false);
        btnPonerDerecha.setEnabled(false);
        btnPasar.setEnabled(false);
    }

    private void actualizar() {

        if (administra.finishedGame()) {
            deshabilitado();
        } else if (administra.isMyTurn()) {
            habilitado();
        } else {
            deshabilitado();
        }

        String msg = "";
        if (administra.getRoom().isGameStarted()) {
            msg += administra.getRoom().whoIsPlaying().getUsername() + " esta jugando" + "\n";
        }

        for (String s : administra.getMessages()) {
            msg += s + "\n";
        }

        textArea.setText(msg);

        lista.setModel(DominoForListModel(administra.getRoom().getPlayer(me).getDominos()));
        listaIzquierda.setModel(BoardLeftList());
        listaDerecha.setModel(BoardRightList());
        btnPonerEnTablero.setEnabled(administra.getRoom().getDominosAvailable().size() > 0);
        QtdDominoPlayer1.setText("Ficha: " + administra.getRoom().getPlayer(administra.getRoom().getPlayer(player1).getUsername()).getDominos().size());
        QtdDominoPlayer2.setText("Ficha: " + administra.getRoom().getPlayer(administra.getRoom().getPlayer(player2).getUsername()).getDominos().size());
        QtdDominoPlayer3.setText("Ficha: " + administra.getRoom().getPlayer(administra.getRoom().getPlayer(player3).getUsername()).getDominos().size());
        lblDominosAvaliables.setText("Fichas disponibles: " + administra.getRoom().getDominosAvailable().size());
    }

    private void pasar() {
        administra.next();
        actualizar();
    }

    private void ponerEnTablero() {
        administra.ponerEnTablero();
    }
}
