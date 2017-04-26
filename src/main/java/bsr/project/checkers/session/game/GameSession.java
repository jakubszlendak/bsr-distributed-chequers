package bsr.project.checkers.session.game;

import bsr.project.checkers.protocol.BoardSymbols;
import bsr.project.checkers.session.client.ClientInfo;
import bsr.project.checkers.session.game.validator.InvalidMoveException;
import bsr.project.checkers.session.game.validator.MoveValidator;

public class GameSession {
	
	private ClientInfo player1; // WHITE
	private ClientInfo player2; // NIGGA
	
	private Board board;
	
	public GameSession(ClientInfo player1, ClientInfo player2) {
		this.player1 = player1;
		this.player2 = player2;
		board = new Board();
	}
	
	
	public void executeMove(ClientInfo player, Point source, Point target) throws InvalidMoveException {
		char playerColor = player == player1 ? BoardSymbols.WHITE_PAWN : BoardSymbols.BLACK_PAWN;
		MoveValidator.validateMove(playerColor, board, source, target);
		// TODO execute move
	}
	
	
	private boolean isGameOver() {
		return hasWhiteWon() || hasBlackWon();
	}
	
	private boolean hasWhiteWon() {
		return board.countSymbols(BoardSymbols.BLACK_PAWN, BoardSymbols.BLACK_SUPER) == 0;
	}
	
	private boolean hasBlackWon() {
		return board.countSymbols(BoardSymbols.WHITE_PAWN, BoardSymbols.WHITE_SUPER) == 0;
	}
}