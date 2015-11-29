package DominoBase.modelo;

import DominoBase.system.Parametros;
import java.io.Serializable;


/**
 * Data structure for assembling the game with dominos placed by players.
 * 
 * When put on the table is necessary to identify which side of the domino is
 * left and what is right. Moreover, one should link the pieces to be able
 * scroll through on the structure.
 */
public class Tablero implements Serializable {

	private static final long serialVersionUID = 5927176236567547194L;

	private Domino domino;

	// Binds to object that is positioned on the left side
	private Tablero left;

	// Determines which side of the domino is left
	private int leftSide;

	// Binds to object that is positioned on the right side
	private Tablero right;

	// Determines which side of the domino is right
	private int rightSide;

	/**
	 * Default constructor. The leftSide and rightSide must be initialized with
	 * value that not exists in the game. Range values of domino: 1-6
	 * 
	 * @param domino
	 */
	public Tablero(Domino domino) {
		setDomino(domino);
		setLeft(null);
		setLeftSide(-1);
		setRight(null);
		setRightSide(-1);
	}

	/**
	 * Put a domino in the left side of domino of the this object.
	 * 
	 * A Domino only can be placed on the left side of the other, if not exist a
	 * domino and your right side equals of the left side of the other.
	 * 
	 * @param boardNode
	 *            node to be placed on the left side.
	 * @return true if the domino was placed on the left side.
	 */
	public boolean putOnLeft(Tablero boardNode) {

		boolean result = false;

		// Verify if left side is available and if the domino is compatible with
		// the side.
		if (getLeft() == null && getLeftSide() == boardNode.getRightSide()) {
			setLeft(boardNode);
			result = true;
		}

		return result;

	}

	/**
	 * Put a domino in the right side of domino of the this object.
	 * 
	 * A Domino only can be placed on the right side of the other, if not exist
	 * a domino and your left side equals of the right side of the other.
	 * 
	 * @param boardNode
	 *            node to be placed on the right side.
	 * @return true if the domino was placed on the right side.
	 */
	public boolean putOnRight(Tablero boardNode) {

		boolean result = false;

		// Verify if left side is available and if the domino is compatible with
		// the side.
		if (getRight() == null && getRightSide() == boardNode.getLeftSide()) {
			setRight(boardNode);
			result = true;
		}

		return result;
	}

	// TODO Mudar metodo para Room... Não cabe ao nó decidir o seu lado e sim
	// caracteristicas da mesa.
	// TODO Comentar metodo.
	/**
	 * 
	 * @param boardNode
	 * @param side
	 * @return
	 */
	public boolean defineSides(Tablero boardNode, String side) {

		boolean result = false;

		// Initialized with value not exits in the domino game.
		int valueSide = -1;

		if (side.equals(Parametros.LEFT)) {
			valueSide = boardNode.getLeftSide();
			if (getDomino().getSide1() == valueSide) {
				setRightSide(Parametros.SIDE1);
				setLeftSide(Parametros.SIDE2);
			} else if (getDomino().getSide2() == valueSide) {
				setRightSide(Parametros.SIDE2);
				setLeftSide(Parametros.SIDE1);
			}

			// se isso ocorrer é por que as pecas se encaixam
			if (getLeftSide() >= 0 && getRightSide() >= 0) {
				// TODO Lembrar desse detalhe ao mudar o metodo para Room
				setRight(boardNode);
				result = true;
			}
		} else if (side.equals(Parametros.RIGHT)) {
			valueSide = boardNode.getRightSide();
			if (getDomino().getSide1() == valueSide) {
				setRightSide(Parametros.SIDE2);
				setLeftSide(Parametros.SIDE1);
			} else if (getDomino().getSide2() == valueSide) {
				setRightSide(Parametros.SIDE1);
				setLeftSide(Parametros.SIDE2);
			}

			if (getLeftSide() >= 0 && getRightSide() >= 0) {
				// TODO Lembrar desse detalhe ao mudar o metodo para Room
				setLeft(boardNode);
				result = true;
			}
		}

		return result;
	}

	/*
	 * Getters and Setters
	 */

	public Domino getDomino() {
		return domino;
	}

	public void setDomino(Domino domino) {
		this.domino = domino;
	}

	public Tablero getLeft() {
		return left;
	}

	public int getLeftSide() {
		return leftSide;
	}

	private void setLeftSide(int leftside) {
		this.leftSide = leftside;
	}

	public void setLeftSide(String side) {

		int leftSide = -1;

		switch (side) {
		case Parametros.SIDE1:
			leftSide = getDomino().getSide1();
			break;

		case Parametros.SIDE2:
			leftSide = getDomino().getSide2();
			break;
		}

		setLeftSide(leftSide);
	}

	public Tablero getRight() {
		return right;
	}

	public int getRightSide() {
		return rightSide;
	}

	private void setRightSide(int rightSide) {
		this.rightSide = rightSide;
	}

	public void setRightSide(String side) {

		int rightSide = -1;

		switch (side) {
		case Parametros.SIDE1:
			rightSide = getDomino().getSide1();
			break;

		case Parametros.SIDE2:
			rightSide = getDomino().getSide2();
			break;
		}

		setRightSide(rightSide);

	}

	private void setLeft(Tablero boardNode) {
		this.left = boardNode;
	}

	private void setRight(Tablero boardNode) {
		this.right = boardNode;
	}

}
