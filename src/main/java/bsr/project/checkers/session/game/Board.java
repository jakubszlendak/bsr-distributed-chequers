package bsr.project.checkers.session.game;

import bsr.project.checkers.protocol.BoardSymbols;

public class Board {
	/*
	board[x][y]
	(0,0) (1,0) ... (BOARD_SIZE-1,0)
	(0,1) (1,1) ... (BOARD_SIZE-1,1)
	...   ...   ... ...
	(0,BOARD_SIZE-1) (1,BOARD_SIZE-1) ... (BOARD_SIZE-1,BOARD_SIZE-1)
	*/
	private char[][] map;
	
	public static final int BOARD_SIZE = 10;
	public static final int PAWN_INITIAL_ROWS = 4;
	
	public Board() {
		initBoard();
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
					if (map[x][y] == symbol) count++;
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
}
