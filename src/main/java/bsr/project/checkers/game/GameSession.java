package bsr.project.checkers.game;

import java.util.List;
import java.util.Optional;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.game.validator.InvalidMoveException;
import bsr.project.checkers.game.validator.MoveValidator;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.protocol.BoardSymbols;

public class GameSession {
	
	private ClientData player1; // WHITE
	private ClientData player2; // NIGGA
	
	private Board board;
	private char currentPlayer = BoardSymbols.WHITE_PAWN;
	private Optional<Point> nextMove = Optional.empty();
	
	private MoveValidator validator;
	
	public GameSession(ClientData player1, ClientData player2) {
		this.player1 = player1;
		this.player2 = player2;
		board = new Board();
		validator = new MoveValidator();
	}
	
	
	public void executeMove(ClientData player, Point source, Point target) throws InvalidMoveException {
		
		char playerColor = player == player1 ? BoardSymbols.WHITE_PAWN : BoardSymbols.BLACK_PAWN;
		nextMove = validator.validateMove(playerColor, board, source, target, nextMove);

		// move is valid - execute move
		board.executeMove(playerColor, source, target, true);

		// if current player does not make next move
		if (!nextMove.isPresent()) {
			// switch current player
			currentPlayer = currentPlayer == BoardSymbols.WHITE_PAWN ? BoardSymbols.BLACK_PAWN : BoardSymbols.WHITE_PAWN;
		}
		
		// TODO "Promocja piona do króla powoduje zakończenie posunięcia"

		// TODO jeśli jest kolejny ruch, musi być wykonany tym samym pionkiem !!!
		
		// TODO jeśli nie ma możliwości wykonania ruchu - wygrana drugiego gracza
	}
	
	
	public boolean isGameOver() {
		return hasWhiteWon() || hasBlackWon();
	}
	
	public ClientData getWinner() {
		if (hasWhiteWon())
			return player1;
		if (hasBlackWon())
			return player2;
		return null;
	}
	
	private boolean hasWhiteWon() {
		return board.countSymbols(BoardSymbols.BLACK_PAWN, BoardSymbols.BLACK_KING) == 0;
	}
	
	private boolean hasBlackWon() {
		return board.countSymbols(BoardSymbols.WHITE_PAWN, BoardSymbols.WHITE_KING) == 0;
	}
	
	public ClientData getPlayer1() {
		return player1;
	}
	
	public ClientData getPlayer2() {
		return player2;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ClientData getCurrentPlayer() {
		return currentPlayer == BoardSymbols.WHITE_PAWN ? player1 : player2;
	}
	
	public ClientData getOpponent(ClientData player) {
		return player1 == player ? player2 : player1;
	}
}