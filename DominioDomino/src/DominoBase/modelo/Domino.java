package DominoBase.modelo;

import DominoBase.system.Parametros;
import java.io.Serializable;

/**
 * Part of the Domino that is manipulated by the player.
 */
public class Domino implements Serializable {

	private static final long serialVersionUID = -7534721277825768082L;

	private int ladoA;

	private int ladoB;

	public Domino(int ladoA, int ladoB) {
		setSide1(ladoA);
		setSide2(ladoB);
	}

	public int getSide1() {
		return ladoA;
	}

	public void setSide1(int side1) {
		this.ladoA = side1;
	}

	public int getSide2() {
		return ladoB;
	}

	public void setSide2(int side2) {
		this.ladoB = side2;
	}

	/**
	 * Method must used by client. Define client path with image of the domino
	 * to be used by GUI.
	 * 
	 * @return complete path of the image file.
	 */
	public String getImagePath() {
		//String rootPath = "/images/";
		String rootPath = Parametros.PATH_IMAGES;
		return rootPath + ladoA + "x" + ladoB + ".png";
	}
        public int getPuntos(){
            return this.ladoA + this.ladoB;
        }

	/**
	 * Method must used by client. When the object is placed in a string,
	 * formats the display to the user.
	 * Can be modified if necessary.
	 */
	@Override
	public String toString() {
		return getSide1() + "x" + getSide2();
	}
	
}
