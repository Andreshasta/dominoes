package DominoBase.modelo;

import DominoBase.system.Parametros;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the room game. All Actions must be do by a object of this class.
 * The manipulation of object (engine game) be diferents implements in the
 * server and client. Each have yours.
 *
 */
public class Sala implements Serializable {

    private static final long serialVersionUID = -3183120280903804020L;

    private int id;

    // Players in room
    private List<Jugador> jugadores;

    // Dominos available for jugadores
    private List<Domino> dominosAvailable;

    // The central domino on board
    private Tablero dominoCenter;

    // Determines if the game started
    private boolean gameStarted;

    // Determines it the game finished
    private boolean finishedGame;

    public Sala(int id) {
        setPlayers(new ArrayList<Jugador>());
        setDominosAvailable(new ArrayList<Domino>());
        setGameStarted(false);
        generateDominos();
        this.id = id;
    }

    /**
     * Start the game
     */
    public void start() {
        giveDominos();
        setGameStarted(true);
    }

    /**
     * Add a player in room. However the room does not exceed the limit of
     * jugadores.
     *
     * @param player
     */
    public void addPlayers(Jugador player) {
        if (getPlayers().size() < Parametros.MAX_NUMBER_OF_PLAYERS) {
            getPlayers().add(player);
        }
    }

    /**
     * Defines the next player, according to the sequence.
     */
    public void next() {

        Jugador currentPlayer = whoIsPlaying();
        Jugador nextPlayer = getNextPlayerById(currentPlayer.getId());

        currentPlayer.setToken(false);
        nextPlayer.setToken(true);

    }

    /**
     * Get next player in the sequence by current id.
     *
     * @param id
     * @return next player
     */
    public Jugador getNextPlayerById(int id) {
        return getPlayerById(nextId(id));
    }

    /**
     * Get a player by username
     *
     * @param username
     * @return player
     */
    public Jugador getPlayer(String username) {

        Jugador result = null;

        for (Jugador p : getPlayers()) {
            if (p.getUsername().equals(username)) {
                result = p;
                break;
            }
        }

        return result;
    }

    public Jugador getPlayerById(int id) {
        Jugador result = null;

        for (Jugador p : getPlayers()) {
            if (p.getId() == id) {
                result = p;
            }
        }

        return result;
    }

    private boolean putOnBoard(Domino domino, String side) {

        boolean result = false;
        Tablero newBoardNode = new Tablero(domino);

        if (getDominoCenter() != null) {

            Tablero lastBoardNode = getLastDominoBoard(side);

            if (newBoardNode.defineSides(lastBoardNode, side)) {

                if (side.equals(Parametros.LEFT)
                        && lastBoardNode.putOnLeft(newBoardNode)) {
                    result = true;
                } else if (side.equals(Parametros.RIGHT)
                        && lastBoardNode.putOnRight(newBoardNode)) {
                    result = true;
                }

            }

        } else {

            newBoardNode.setLeftSide(Parametros.SIDE1);
            newBoardNode.setRightSide(Parametros.SIDE2);
            setDominoCenter(newBoardNode);
            result = true;
        }

        return result;
    }

    private Tablero getLastDominoBoard(String side) {

        Tablero lastBoard = getDominoCenter();

        switch (side) {
            case Parametros.LEFT:
                while (lastBoard.getLeft() != null) {
                    lastBoard = lastBoard.getLeft();
                }
                break;

            case Parametros.RIGHT:
                while (lastBoard.getRight() != null) {
                    lastBoard = lastBoard.getRight();
                }
                break;
        }

        return lastBoard;
    }

    private void giveDominos() {

        int numberDominosByPlayer = Parametros.MAX_NUMBER_FICHAS_PLAYER;

        int biggestDomino = 0;
        int biggestBushwacker = 0;

        Jugador last = null;

        for (Jugador p : getPlayers()) {
            Random random = new Random();
            for (int i = 0; i < numberDominosByPlayer; i++) {

                int index = random.nextInt(getDominosAvailable().size());
                Domino domino = getDominosAvailable().get(index);

                p.addDomino(domino);
                getDominosAvailable().remove(index);

                if (domino.getSide1() == domino.getSide2()
                        && domino.getSide1() + domino.getSide2() > biggestBushwacker) {
                    biggestBushwacker = domino.getSide1() + domino.getSide2();
                    last = p;
                }

                /*
                 * If no there Bushwacker raffled, start that has the domino
                 * with the largest sum of sides.
                 */
                if (biggestBushwacker == 0
                        && domino.getSide1() + domino.getSide2() > biggestDomino) {
                    biggestDomino = domino.getSide1() + domino.getSide2();
                    last = p;
                }

            }
        }

        last.setToken(true);

    }

    private void generateDominos() {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                getDominosAvailable().add(new Domino(i, j));
            }
        }
    }

    private int nextId(int id) {

        int result = ++id;

        if (result < 1 || result > getPlayers().size()) {
            result = 1;
        }

        return result;
    }

    public boolean putOnBoard(Domino domino, String side, String username) {

        boolean result = false;

        if (putOnBoard(domino, side)) {
            getPlayer(username).removeDomino(domino);

            if (getPlayer(username).getDominos().size() == 0
                    || !hasPossibleMoves()) {
                setFinishedGame(true);
            }

            result = true;
        }

        return result;
    }

    public Jugador whoIsPlaying() {

        Jugador result = null;

        for (Jugador p : getPlayers()) {
            if (p.isToken()) {
                result = p;
                break;
            }
        }

        return result;
    }

    public boolean pushAvaliableDominos(Jugador player) {

        boolean result = false;

        if (getDominosAvailable().size() > 0) {
            Domino domino = getDominosAvailable().get(0);
            player.addDomino(domino);
            getDominosAvailable().remove(0);
            result = true;
        }

        return result;
    }

    private boolean hasPossibleMoves() {

        boolean result = false;
        int leftNumberAvailable = -1;
        int rightNumberAvailable = -1;

        if (getDominoCenter() != null) {
            leftNumberAvailable = getLastDominoBoard(Parametros.LEFT)
                    .getLeftSide();
            rightNumberAvailable = getLastDominoBoard(Parametros.RIGHT)
                    .getRightSide();
        }

        for (Jugador player : getPlayers()) {
            if (!player.getDominos().isEmpty()) {
                for (Domino domino : player.getDominos()) {
                    if (domino.getSide1() == leftNumberAvailable
                            || domino.getSide1() == rightNumberAvailable
                            || domino.getSide2() == leftNumberAvailable
                            || domino.getSide2() == rightNumberAvailable) {

                        result = true;
                        break;
                    }
                }
            } else {
                result = false;
                break;
            }
        }

        return result;

    }

    public Jugador whoWon() {

        Jugador result = null;
        int puntaje = 0;
        if (isFinishedGame()) {
            for (Jugador player : getPlayers()) {
                if (player.getDominos().size() == 0) {
                    result = player;

                } else {
                    for (Domino d : player.getDominos()) {
                        puntaje = puntaje + d.getPuntos();
                    }
                }
            }
            result.setPuntaje(puntaje);
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public Tablero getDominoCenter() {
        return dominoCenter;
    }

    public void setDominoCenter(Tablero boardNode) {
        this.dominoCenter = boardNode;
    }

    public List<Domino> getDominosAvailable() {
        return dominosAvailable;
    }

    public List<Jugador> getPlayers() {
        return jugadores;
    }

    private void setPlayers(List<Jugador> players) {
        this.jugadores = players;
    }

    private void setDominosAvailable(List<Domino> dominosAvailable) {
        this.dominosAvailable = dominosAvailable;
    }

    public boolean isFinishedGame() {
        return finishedGame;
    }

    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }

}
