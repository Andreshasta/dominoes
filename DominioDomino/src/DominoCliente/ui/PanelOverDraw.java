package DominoCliente.ui;

import DominoBase.system.Parametros;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import DominoBase.modelo.Domino;

public class PanelOverDraw extends JPanel {

    private BufferedImage fondo = null;
    private Graphics2D pinta = null;
    private int posX;
    private int posY;
    private int ancho;
    private int alto;
    private ArrayList<Domino> listaIzq;
    private ArrayList<Domino> listaDer;

    public PanelOverDraw() {
        posX = 1;
        posY = 1;
        ancho = 779;
        alto = 336;
        File imageFondo = new File(Parametros.PATH_IMAGES + "border.png"); // guarda la imagen en un archivo
        try {
            fondo = ImageIO.read(getClass().getResourceAsStream(imageFondo.toString()));
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        pinta = fondo.createGraphics();
        pinta.drawImage(fondo, posX, posY, ancho, alto, this);
    }

    /*public void pintar(){
     pinta.drawImage(fondo, posX, posY, ancho, alto, this);
     }*/
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 1, 1, 779, 336, this); // dibuja la imagen al iniciar la aplicacion
    }

    public void addListIzq(Domino obj) {
        this.listaIzq.add(obj);
        for (Domino gt : listaIzq){
            System.out.println(gt.getImagePath());
        }
    }

    public void addListDer(Domino obj) {
        this.listaDer.add(obj);
    }
}
