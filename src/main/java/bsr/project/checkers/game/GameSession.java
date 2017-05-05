package bsr.project.checkers.game;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.game.validator.InvalidMoveException;
import bsr.project.checkers.game.validator.MoveValidator;
import bsr.project.checkers.protocol.BoardSymbols;

public class GameSession {
	
	private ClientData player1; // WHITE
	private ClientData player2; // NIGGA
	
	private char currentPlayer = BoardSymbols.WHITE_PAWN;
	
	private Board board;
	
	public GameSession(ClientData player1, ClientData player2) {
		this.player1 = player1;
		this.player2 = player2;
		board = new Board();
	}
	
	
	public void executeMove(ClientData player, Point source, Point target) throws InvalidMoveException {
		char playerColor = player == player1 ? BoardSymbols.WHITE_PAWN : BoardSymbols.BLACK_PAWN;
		MoveValidator.validateMove(playerColor, board, source, target);
		// TODO execute move
	}
	
	
	private boolean isGameOver() {
		return hasWhiteWon() || hasBlackWon();
	}
	
	private boolean hasWhiteWon() {
		return board.countSymbols(BoardSymbols.BLACK_PAWN, BoardSymbols.BLACK_KING) == 0;
	}
	
	private boolean hasBlackWon() {
		return board.countSymbols(BoardSymbols.WHITE_PAWN, BoardSymbols.WHITE_KING) == 0;
	}

	public ClientData getPlayer1(){
		return player1;
	}
	
	public ClientData getPlayer2(){
		return player2;
	}

	public Board getBoard(){
		return board;
	}

	public ClientData getCurrentPlayer(){
		return currentPlayer == BoardSymbols.WHITE_PAWN ? player1 : player2;
	}

	public ClientData getOpponent(ClientData player){
		return player1 == player ? player2 : player1;
	}
}