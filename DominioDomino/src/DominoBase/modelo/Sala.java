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
	private List<Jugador> players;

	// Dominos available for players
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
	 * players.
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

	/**
	 * Get a player by id
	 * 
	 * @param id
	 * @return player
	 */
	public Jugador getPlayerById(int id) {
		Jugador result = null;

		for (Jugador p : getPlayers()) {
			if (p.getId() == id) {
				result = p;
			}
		}

		return result;
	}

	/**
	 * Put a domino in the board of room.
	 * 
	 * @param domino
	 * @param side
	 * @return true if the domino is on the board.
	 */
	private boolean putOnBoard(Domino domino, String side) {

		boolean result = false;
		Tablero newBoardNode = new Tablero(domino);

		// Checks whether there are any domino on the board.
		if (getDominoCenter() != null) {

			Tablero lastBoardNode = getLastDominoBoard(side);

			// Checks if the moves is possible between two nodes
			if (newBoardNode.defineSides(lastBoardNode, side)) {

				// Execute moviment
				if (side.equals(Parametros.LEFT)
						&& lastBoardNode.putOnLeft(newBoardNode)) {
					result = true;
				} else if (side.equals(Parametros.RIGHT)
						&& lastBoardNode.putOnRight(newBoardNode)) {
					result = true;
				}

			}

		} else {

			/*
			 * If not exists put the central Domino on the board. In this case,
			 * the sides of domino is not important.
			 */
			newBoardNode.setLeftSide(Parametros.SIDE1);
			newBoardNode.setRightSide(Parametros.SIDE2);
			setDominoCenter(newBoardNode);
			result = true;
		}

		return result;
	}

	/**
	 * From the central node gets the last node in the specified side.
	 * 
	 * @param side
	 * @return the last node of the side
	 */
	private Tablero getLastDominoBoard(String side) {

		// Get central node
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

	/**
	 * Makes the initial distribution of dominos for players. Each player must
	 * have the same number of domino. The remainings should be available at the
	 * table.
	 */
	private void giveDominos() {

		/*
		 * Calculates the quantity of dominos by player. The value must be
		 * truncated and integer to not miss dominos
		 */
		int numberDominosByPlayer = (int) (getDominosAvailable().size() / players
				.size());

		/*
		 * Control variables
		 */
		int biggestDomino = 0;
		int biggestBushwacker = 0;

		/*
		 * The player that will start game
		 */
		Jugador last = null;

		/*
		 * Random sort dominos for each player.
		 */
		for (Jugador p : getPlayers()) {
			Random random = new Random();
			for (int i = 0; i < numberDominosByPlayer; i++) {

				// Sort a available domino
				int index = random.nextInt(getDominosAvailable().size());
				Domino domino = getDominosAvailable().get(index);

				// move available domino to palyer
				p.addDomino(domino);
				getDominosAvailable().remove(index);

				/*
				 * Determines who will start the game. Starts who has the
				 * largest Bushwacker.
				 */
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

	/**
	 * Generate all dominos.
	 */
	private void generateDominos() {
		for (int i = 0; i <= 6; i++) {
			for (int j = i; j <= 6; j++) {
				getDominosAvailable().add(new Domino(i, j));
			}
		}
	}

	/**
	 * Get the next id of the sequence room based on an id
	 * 
	 * @param id
	 * @return next id of the sequence room
	 */
	private int nextId(int id) {

		// Next id
		int result = ++id;

		// Check quantity players in room.
		if (result < 1 || result > getPlayers().size()) {
			result = 1;
		}

		return result;
	}

	/**
	 * Jugador action of put a domino on board. This method moves the domino from
	 * the player's hands to the table.
	 * 
	 * @param domino
	 * @param side
	 * @param username
	 * @return true if the move carreid.
	 */
	public boolean putOnBoard(Domino domino, String side, String username) {

		boolean result = false;
		
		//Execute moviment
		if (putOnBoard(domino, side)) {
			getPlayer(username).removeDomino(domino);

			/*
			 * Check if game is finished. There are two possibilities: a) The
			 * player has no domino. b) No more moves.
			 */
			if (getPlayer(username).getDominos().size() == 0
					|| !hasPossibleMoves()) {
				setFinishedGame(true);
			}

			result = true;
		}

		return result;
	}
	
	/**
	 * 
	 * @return The player who is current playing.
	 */
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
	
	/**
	 * Get to player the first domino in the stack available
	 *  
	 * @param player
	 * @return domino
	 */
	public boolean pushAvaliableDominos(Jugador player) {

		boolean result = false;
		
		//Check if there domino in the stacks
		if (getDominosAvailable().size() > 0) {
			Domino domino = getDominosAvailable().get(0);
			player.addDomino(domino);
			getDominosAvailable().remove(0);
			result = true;
		}

		return result;
	}
	
	/**
	 * Check there are still moves.
	 * 
	 * @return true if exists.
	 */
	private boolean hasPossibleMoves() {

		boolean result = false;
		int leftNumberAvailable = -1;
		int rightNumberAvailable = -1;

		// Get number available each side of the board.
		if (getDominoCenter() != null) {
			leftNumberAvailable = getLastDominoBoard(Parametros.LEFT)
					.getLeftSide();
			rightNumberAvailable = getLastDominoBoard(Parametros.RIGHT)
					.getRightSide();
		}

		// Checks if any player has a available number
		for (Jugador player : getPlayers()) {
			for (Domino domino : player.getDominos()) {
				if (domino.getSide1() == leftNumberAvailable
						|| domino.getSide1() == rightNumberAvailable
						|| domino.getSide2() == leftNumberAvailable
						|| domino.getSide2() == rightNumberAvailable) {

					result = true;
					break;
				}
			}

		}

		return result;

	}

	public Jugador whoWon() {

		Jugador result = null;

		if (isFinishedGame()) {
			for (Jugador player : getPlayers()) {
				if (player.getDominos().size() == 0) {
					result = player;
					break;
				}
			}
		}

		return result;
	}

	// Getters and Setters

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
		return players;
	}

	private void setPlayers(List<Jugador> players) {
		this.players = players;
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
