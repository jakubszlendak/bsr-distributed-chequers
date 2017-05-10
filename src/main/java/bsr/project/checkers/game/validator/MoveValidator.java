package bsr.project.checkers.game.validator;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import bsr.project.checkers.game.Board;
import bsr.project.checkers.game.BoardLogic;
import bsr.project.checkers.game.Point;

/**
 * walidator do sprawdzania poprawności wykonywanych ruchów
 * zasady gry: warcaby angielskie
 */
public class MoveValidator {
	
	/**
	 * check if move is valid
	 * @param playerColor moving player color (@see BoardSymbols.WHITE_PAWN, @see BoardSymbols.BLACK_PAWN)
	 * @param board       current board (before move)
	 * @param source      source pawn coordinates
	 * @param target      target field coordinates
	 * @return anotherMove if moving player has another move after current move
	 * @throws InvalidMoveException if move is invalid
	 */
	public Optional<Point> validateMove(char playerColor, Board board, Point source, Point target, Optional<Point> nextMove) throws InvalidMoveException {
		// współrzędne w granicach planszy
		if (!BoardLogic.isOnBoard(source))
			throw new InvalidMoveException("source field coordinates out of board bounds");
		if (!BoardLogic.isOnBoard(target))
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
		int dx = BoardLogic.absDX(source, target);
		int dy = BoardLogic.absDY(source, target);
		if (dx != dy)
			throw new InvalidMoveException("only diagonal moves are allowed");

		// ruch tylko o 1 lub 2 pola
		if (dx > 2)
			throw new InvalidMoveException("cannot move by more than 2 fields");

		// TODO czy zwykły pionek może bić do tyłu ?

		// zwykły pionek, podczas zwykłego ruchu nie może się cofać
		if (BoardLogic.isPawn(sourceField) && dx == 1 && BoardLogic.isMovingBackwards(sourceField, source, target))
			throw new InvalidMoveException("pawn cannot move backwards");
		
		// TODO jeśli ma możliwość bicia - nie można wykonać zwykłego ruchu
		
		// "Król ma analogiczną możliwość wykonywania posunięć jak pion, z tym, że może poruszać się i bić także do tyłu"

		if (dx == 1) { // zwykły ruch o jedno pole
			// pionek nie może się cofać
			if (BoardLogic.isMovingBackwards(sourceField, source, target))
				throw new InvalidMoveException("pawn cannot move backwards");
			// ruch poprawny - bez możliwości kolejnego ruchu
			
		} else if (dx == 2) {
			// bicie pionka przeciwnika
			Point between = BoardLogic.pointBetween(source, target);
			char betweenField = board.getCell(between);
			// pionek pomiędzy musi być przeciwnego koloru
			if (!BoardLogic.isOppositeColor(sourceField, betweenField))
				throw new InvalidMoveException("pawn can only jump over opponent's pawns");
			
			// TODO sprawdzenie, czy można bić w kolejnym ruchu
			
			// ruch poprawny
		}
		
		// TODO czy było bicie
		
		// TODO czy gracz może wykonać kolejne bicie (kolejny ruch)
		
		// TODO lista legalnych ruchów, kopia mapy i symulacja
		
		return Optional.empty();
	}

	public boolean isMoveValid(char playerColor, Board board, Point source, Point target, Optional<Point> nextMove) {
		try{
			validateMove(playerColor, board, source, target, nextMove);
			return true;
		}catch (InvalidMoveException e){
			return false;
		}
	}
	
	public List<PossibleMove> generatePossibleMoves(char playerColor, Board board, Point source, Optional<Point> nextMove){
		List<PossibleMove> possibleMoves = new ArrayList<>();
		char sourceField = board.getCell(source);

		// check if potential targets are valid
		List<Point> potentialTargets = BoardLogic.potentialTargets(source, sourceField);
		for(Point potentialTarget : potentialTargets){
			if (isMoveValid(playerColor, board, source, potentialTarget, nextMove)){
				possibleMoves.add(new PossibleMove(source, potentialTarget));
			}
		}

		return possibleMoves;
	}
	
	public List<PossibleMove> generateAllPossibleMoves(char playerColor, Board board, Optional<Point> nextMove){
		List<PossibleMove> allPossibleMoves = new ArrayList<>();

		List<Point> playerPawns = BoardLogic.listAllPlayerPawns(playerColor, board);
		for (Point playerPawn : playerPawns){
			List<PossibleMove> possibleMoves = generatePossibleMoves(playerColor, board, playerPawn, nextMove);
			allPossibleMoves.addAll(possibleMoves);
		}

		return allPossibleMoves;
	}

	public boolean isBeatingPossible(List<PossibleMove> possibleMoves){
		return possibleMoves.stream()
				.anyMatch(move -> move.isBeating());
	}
	
	public boolean isBeatingPossible(char playerColor, Board board, Optional<Point> nextMove){
		return isBeatingPossible(generateAllPossibleMoves(playerColor, board, nextMove));
	}
	
	public boolean isBeatingPossible(char playerColor, Board board, Point source, Optional<Point> nextMove){
		return isBeatingPossible(generatePossibleMoves(playerColor, board, source, nextMove));
	}
}