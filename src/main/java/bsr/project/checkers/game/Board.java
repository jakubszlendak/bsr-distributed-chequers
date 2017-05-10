package bsr.project.checkers.game;

import bsr.project.checkers.protocol.BoardSymbols;
import bsr.project.checkers.logger.Logs;
import java.util.List;

public class Board {
	/*
	board[x][y]
	(0,0) (1,0) ... (BOARD_SIZE-1,0)
	(0,1) (1,1) ... (BOARD_SIZE-1,1)
	...   ...   ... ...
	(0,BOARD_SIZE-1) (1,BOARD_SIZE-1) ... (BOARD_SIZE-1,BOARD_SIZE-1)

	initial board:
	OCOCOCOC
	COCOCOCO
	OCOCOCOC
	OOOOOOOO
	OOOOOOOO
	BOBOBOBO
	OBOBOBOB
	BOBOBOBO
	*/
	private char[][] map;
	
	public static final int BOARD_SIZE = 8;
	public static final int PAWN_INITIAL_ROWS = 3;
	
	public Board() {
		initBoard();
	}

	public Board(Board sourceBoard){
		map = new char[BOARD_SIZE][BOARD_SIZE];
		// copy board
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				map[x][y] = sourceBoard.map[x][y];
			}
		}
	}
	
	private void initBoard() {
		map = new char[BOARD_SIZE][BOARD_SIZE];
		// clear board
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				map[x][y] = BoardSymbols.EMPTY;
			}
		}
		// set initial pawn positions: black
		for (int y = 0; y < PAWN_INITIAL_ROWS; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				if ((x + y) % 2 == 1) {
					map[x][y] = BoardSymbols.BLACK_PAWN;
				}
			}
		}
		// set initial pawn positions: white
		for (int y = BOARD_SIZE - PAWN_INITIAL_ROWS; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				if ((x + y) % 2 == 1) {
					map[x][y] = BoardSymbols.WHITE_PAWN;
				}
			}
		}
	}
	
	public int countSymbols(char... symbols) {
		int count = 0;
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				for (char symbol : symbols) {
					if (map[x][y] == symbol)
						count++;
				}
			}
		}
		return count;
	}
	
	public char getCell(int x, int y) {
		if (x < 0 || y < 0 || x >= BOARD_SIZE || y >= BOARD_SIZE)
			throw new IllegalArgumentException("x or y out of bound");
		return map[x][y];
	}
	
	public char getCell(Point p) {
		return getCell(p.x, p.y);
	}
	
	public void setCell(Point p, char value) {
		map[p.x][p.y] = value;
	}
	
	public char[][] getMap() {
		return map;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				sb.append(map[x][y]);
			}
		}
		return sb.toString();
	}

	public void executeMove(char playerColor, Point source, Point target, boolean verbose){
		// move source to target
		char moving = getCell(source);
		setCell(target, moving);
		setCell(source, BoardSymbols.EMPTY); // replace by empty field
		// remove (beat) all the pawns between source and target
		List<Point> points = BoardLogic.pointsBetween(source, target);
		for (Point p : points) {
			char cell = getCell(p);
			if (cell != BoardSymbols.EMPTY) {
				setCell(p, BoardSymbols.EMPTY);
				if (verbose)
					Logs.debug("Pawn " + cell + " on field " + p.toString() + " has been beaten");
			}
		}
		// if pawn reached end of board
		if (BoardLogic.isOnBoardEnd(playerColor, target) && BoardLogic.isPawn(moving)) {
			// transform pawn to King
			setCell(target, BoardLogic.pawnToKing(moving));
			if (verbose)
				Logs.debug("pawn on " + target.toString() + " field has been transformed to king");
		}
	}
}
