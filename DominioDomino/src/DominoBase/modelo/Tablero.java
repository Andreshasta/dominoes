package DominoBase.modelo;

import DominoBase.system.Parametros;
import java.io.Serializable;

public class Tablero implements Serializable {

    private static final long serialVersionUID = 5927176236567547194L;

    private Domino domino;

    private Tablero left;

    private int leftSide;

    private Tablero right;

    private int rightSide;

    public Tablero(Domino domino) {
        setDomino(domino);
        setLeft(null);
        setLeftSide(-1);
        setRight(null);
        setRightSide(-1);
    }

    public boolean putOnLeft(Tablero boardNode) {

        boolean result = false;

        if (getLeft() == null && getLeftSide() == boardNode.getRightSide()) {
            setLeft(boardNode);
            result = true;
        }

        return result;

    }

    public boolean putOnRight(Tablero boardNode) {

        boolean result = false;

        if (getRight() == null && getRightSide() == boardNode.getLeftSide()) {
            setRight(boardNode);
            result = true;
        }

        return result;
    }

    public boolean defineSides(Tablero boardNode, String side) {

        boolean result = false;

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

            if (getLeftSide() >= 0 && getRightSide() >= 0) {

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

                setLeft(boardNode);
                result = true;
            }
        }

        return result;
    }

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
