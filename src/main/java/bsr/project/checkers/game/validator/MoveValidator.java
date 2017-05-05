package bsr.project.checkers.game.validator;

import java.util.List;
import java.util.ArrayList;

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
			// zwykły pionek
			if (dx > 2)
				throw new InvalidMoveException("pawn cannot move by more than 2 fields");
			if (dx == 1){ // zwykły ruch do przodu
				// pionek nie może się cofać
				if (isMovingBackwards(sourceField, source, target))
					throw new InvalidMoveException("pawn cannot move backwards");
				// ruch poprawny - bez możliwości kolejnego ruchu
				return false;

			} else if (dx == 2){
				// bicie pionka przeciwnika
				Point between = pointBetween(source, target);
				char betweenField = board.getCell(between);
				// pionek pomiędzy musi być przeciwnego koloru
				if(!isOppositeColor(sourceField, betweenField))
					throw new InvalidMoveException("pawn can only jump over opponent's pawns");
				// ruch poprawny

				// TODO sprawdzenie, czy można bić w kolejnym ruchu

			}
		} else if (isKing(sourceField)){ 
			// TODO damka

		}

		// TODO czy było bicie

			// TODO czy gracz może wykonać kolejne bicie (kolejny ruch)

		// TODO lista legalnych ruchów, kopia mapy i symulacja

		return false;
		
	}

	// używane metody logiki planszy przenieść do utilsa: BoardLogic

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

	private boolean isSameColor(char field1, char field2){
		if (isWhite(field1) && isWhite(field2))
			return true;
		if (isBlack(field1) && isBlack(field2))
			return true;
		return false;
	}

	private boolean isOppositeColor(char field1, char field2){
		if (isWhite(field1) && isBlack(field2))
			return true;
		if (isBlack(field1) && isWhite(field2))
			return true;
		return false;
	}

	private static int abs(int number){
		return number >= 0 ? number : -number;
	}

	private Point pointBetween(Point p1, Point p2){
		int x = (p1.x + p2.x) / 2;
		int y = (p1.y + p2.y) / 2;
		return new Point(x, y);
	}

	public static List<Point> pointsBetween(Point p1, Point p2){
		List<Point> points = new ArrayList<>();

		int dx = abs(p1.x - p2.x);
		int dy = abs(p1.y - p2.y);

		for (int i = 1; i < dx; i++) {
			int x = p1.x + (p2.x - p1.x) * i / dx;
			int y = p1.y + (p2.y - p1.y) * i / dy;
			points.add(new Point(x, y));
		}

		return points;
	}

	private boolean isMovingBackwards(char field, Point source, Point target){
		int dy = target.y - source.y; // dodatni kierunek - ruch w dół planszy (w kierunku białych)
		if (isWhite(field)){
			return dy > 0;
		} else if(isBlack(field)){
			return dy < 0;
		}
		return false;
	}
	
}