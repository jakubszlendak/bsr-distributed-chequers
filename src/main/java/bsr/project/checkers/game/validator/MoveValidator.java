package bsr.project.checkers.game.validator;

import bsr.project.checkers.game.Board;
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
		if (source.x < 0 || source.y < 0 || source.x >= Board.BOARD_SIZE || source.y >= Board.BOARD_SIZE)
			throw new InvalidMoveException("source field coordinates out of board bounds");
		if (target.x < 0 || target.y < 0 || target.x >= Board.BOARD_SIZE || target.y >= Board.BOARD_SIZE)
			throw new InvalidMoveException("target field coordinates out of board bounds");

		char sourceField = board.getCell(source);
		char targetField = board.getCell(target);
		// czy wybrany pionek należy do obecnego gracza
		if (!isSameColor(sourceField, playerColor))
			throw new InvalidMoveException("source pawn is not owned by moving player");

		// czy docelowe pole jest puste
		if (!isEmpty(targetField))
			throw new InvalidMoveException("target field is not empty");

		// czy wybrano dozwolone pola (dozwolonego koloru pola planszy) - suma współrzędnych ma być nieparzysta
		if ((source.x + source.y) % 2 == 0)
			throw new InvalidMoveException("source field has invalid coordinates");
		if ((target.x + target.y) % 2 == 0)
			throw new InvalidMoveException("target field has invalid coordinates");
		
		// czy punkty są inne
		if (source.equals(target))
			throw new InvalidMoveException("target field is the same as source");

		// ruch tylko na ukos
		int dx = abs(source.x - target.x);
		int dy = abs(source.y - target.y);
		if (dx != dy)
			throw new InvalidMoveException("only diagonal moves are allowed");

		if (isPawn(sourceField)){
			// TODO zwykły pionek
			if (dx > 2)
				throw new InvalidMoveException("pawn cannot move by more than 2 fields");
			if (dx == 1){
				// TODO zwykły ruch

			} else if (dx == 2){
				// TODO bicie

			}


		} else if (isKing(sourceField)){ 
			// TODO damka

		}

		// TODO czy było bicie

		// TODO czy gracz może wykonać kolejne bicie (kolejny ruch)

		return false;
		
	}

	private boolean isWhite(char field){
		return field == BoardSymbols.WHITE_PAWN || field == BoardSymbols.WHITE_KING;
	}

	private boolean isBlack(char field){
		return field == BoardSymbols.BLACK_PAWN || field == BoardSymbols.BLACK_KING;
	}

	private boolean isPawn(char field){
		return field == BoardSymbols.WHITE_PAWN || field == BoardSymbols.BLACK_PAWN;
	}

	private boolean isKing(char field){
		return field == BoardSymbols.WHITE_KING || field == BoardSymbols.BLACK_KING;
	}

	private boolean isEmpty(char field){
		return field == BoardSymbols.EMPTY;
	}

	private Point pointBetween(Point p1, Point p2){
		int x = (p1.x + p2.x) / 2;
		int y = (p1.y + p2.y) / 2;
		return new Point(x, y);
	}

	private boolean isSameColor(char field1, char field2){
		if (isWhite(field1) && isWhite(field2))
			return true;
		if (isBlack(field1) && isBlack(field2))
			return true;
		return false;
	}

	private int abs(int number){
		return number >= 0 ? number : -number;
	}
	
}