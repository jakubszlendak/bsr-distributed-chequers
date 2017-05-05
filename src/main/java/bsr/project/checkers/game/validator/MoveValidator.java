package bsr.project.checkers.game.validator;

import java.util.List;
import java.util.ArrayList;

import bsr.project.checkers.game.Board;
import bsr.project.checkers.game.BoardLogic;
import bsr.project.checkers.game.Point;
import bsr.project.checkers.protocol.BoardSymbols;

public class MoveValidator {
	
	/**
	 * check if move is valid
	 * @param playerColor moving player color (@see BoardSymbols.WHITE_PAWN, @see BoardSymbols.BLACK_PAWN)
	 * @param board current board (before move)
	 * @param source source pawn coordinates
	 * @param target target field coordinates
	 * @throws InvalidMoveException if move is invalid
	 * @return anotherMove if moving player has another move after current move
	 */
	public boolean validateMove(char playerColor, Board board, Point source, Point target) throws InvalidMoveException {
		// współrzędne w granicach planszy
		if (BoardLogic.isOutOfBounds(source))
			throw new InvalidMoveException("source field coordinates out of board bounds");
		if (BoardLogic.isOutOfBounds(target))
			throw new InvalidMoveException("target field coordinates out of board bounds");

		char sourceField = board.getCell(source);
		char targetField = board.getCell(target);
		// czy wybrany pionek należy do obecnego gracza
		if (!BoardLogic.isSameColor(sourceField, playerColor))
			throw new InvalidMoveException("source pawn is not owned by moving player");

		// czy docelowe pole jest puste
		if (!BoardLogic.isEmpty(targetField))
			throw new InvalidMoveException("target field is not empty");

		// czy wybrano dozwolone pola (dozwolonego koloru pola planszy) - suma współrzędnych ma być nieparzysta
		if ((source.x + source.y) % 2 == 0)
			throw new InvalidMoveException("source field has invalid coordinates");
		if ((target.x + target.y) % 2 == 0)
			throw new InvalidMoveException("target field has invalid coordinates");
		
		// czy punkty są różne
		if (source.equals(target))
			throw new InvalidMoveException("target field is the same as source");

		// ruch tylko na ukos
		int dx = BoardLogic.abs(source.x - target.x);
		int dy = BoardLogic.abs(source.y - target.y);
		if (dx != dy)
			throw new InvalidMoveException("only diagonal moves are allowed");

		if (BoardLogic.isPawn(sourceField)){
			// zwykły pionek
			if (dx > 2)
				throw new InvalidMoveException("pawn cannot move by more than 2 fields");
			if (dx == 1){ // zwykły ruch do przodu
				// pionek nie może się cofać
				if (BoardLogic.isMovingBackwards(sourceField, source, target))
					throw new InvalidMoveException("pawn cannot move backwards");
				// ruch poprawny - bez możliwości kolejnego ruchu
				return false;

			} else if (dx == 2){
				// bicie pionka przeciwnika
				Point between = BoardLogic.pointBetween(source, target);
				char betweenField = board.getCell(between);
				// pionek pomiędzy musi być przeciwnego koloru
				if(!BoardLogic.isOppositeColor(sourceField, betweenField))
					throw new InvalidMoveException("pawn can only jump over opponent's pawns");
				// ruch poprawny

				// TODO sprawdzenie, czy można bić w kolejnym ruchu

			}
		} else if (BoardLogic.isKing(sourceField)){ 
			// TODO damka

			// TODO jeśli pomiędzy były własne pionki
			
			// TODO jeśli pomiędzy były pionki przeciwnika - było bicie


		}

		// TODO czy było bicie

			// TODO czy gracz może wykonać kolejne bicie (kolejny ruch)

		// TODO lista legalnych ruchów, kopia mapy i symulacja

		return false;
	}
	
}