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

		// czy wybrano pionka, którym trzeba wykonać następny ruch
		if (nextMove.isPresent()){
			if (!nextMove.get().equals(source)){
				throw new InvalidMoveException("next move must be made by pawn: " + source);
			}
		}

		// TODO czy zwykły pionek może bić do tyłu ?
		// "Król ma analogiczną możliwość wykonywania posunięć jak pion, z tym, że może poruszać się i bić także do tyłu"
		// zwykły pionek, podczas zwykłego ruchu nie może się cofać
		if (BoardLogic.isPawn(sourceField) && dx == 1 && BoardLogic.isMovingBackwards(sourceField, source, target))
			throw new InvalidMoveException("pawn cannot move backwards");

		if (dx == 1) { // zwykły ruch o jedno pole
			// ruch potencjalnie poprawny
			
		} else if (dx == 2) {
			// bicie pionka przeciwnika
			Point between = BoardLogic.pointBetween(source, target);
			char betweenField = board.getCell(between);
			// pionek pomiędzy musi być przeciwnego koloru
			if (!BoardLogic.isOppositeColor(sourceField, betweenField))
				throw new InvalidMoveException("pawn can only jump over opponent's pawns");
			// ruch potencjalnie poprawny
		}

		Optional<Point> anotherMove = Optional.empty();
		// wyjątek: "Promocja piona do króla powoduje zakończenie posunięcia"
		if (BoardLogic.isOnBoardEnd(playerColor, target) && BoardLogic.isPawn(sourceField)) {
			return Optional.empty();
		}
		// jeśli obecny ruch jest biciem przeciwnika
		if (dx == 2) {
			// kopia planszy
			Board nextBoard = new Board(board);
			// symulacja ustawienia planszy w kolejnym ruchu
			BoardLogic.executeMove(nextBoard, playerColor, source, target, false);
			// sprawdzenie, czy można wykonać bicie w kolejnym ruchu tym samym pionkiem
			if(isBeatingPossible(playerColor, nextBoard, target, Optional.of(target))){
				// jeśli tak, to następny ruch wykonany ma być tym samym pionkiem
				anotherMove = Optional.of(target);
			}
		}
		
		return anotherMove;
	}

	public Optional<Point> advancedValidateMove(char playerColor, Board board, Point source, Point target, Optional<Point> nextMove) throws InvalidMoveException {
		Optional<Point> anotherMove = validateMove(playerColor, board, source, target, nextMove);
		// sprawdzanie, czy jest możliwe bicie przeciwnika jakimkolwiek pionkiem
		List<PossibleMove> allPossibleMoves = generateAllPossibleMoves(playerColor, board, Optional.empty())
		boolean beatingPossible = isBeatingPossible(allPossibleMoves);
		if (beatingPossible) { //jeśli jest możliwe bicie
			List<PossibleMove> beatingMoves = filterBeatingMoves(allPossibleMoves);
			// czy ruch wykonywany jest pionkiem, który może wykonać bicie
			if (!(beatingMoves.contains(new PossibleMove(source, target)))){
				throw new InvalidMoveException("Beating move is possible.");
			}
		}

		return anotherMove;
	}

	private boolean isMoveValid(char playerColor, Board board, Point source, Point target, Optional<Point> nextMove) {
		try{
			validateMove(playerColor, board, source, target, nextMove);
			return true;
		}catch (InvalidMoveException e){
			return false;
		}
	}
	
	private List<PossibleMove> generatePossibleMoves(char playerColor, Board board, Point source, Optional<Point> nextMove){
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
	
	private List<PossibleMove> generateAllPossibleMoves(char playerColor, Board board, Optional<Point> nextMove){
		List<PossibleMove> allPossibleMoves = new ArrayList<>();

		List<Point> playerPawns = BoardLogic.listAllPlayerPawns(playerColor, board);
		for (Point playerPawn : playerPawns){
			List<PossibleMove> possibleMoves = generatePossibleMoves(playerColor, board, playerPawn, nextMove);
			allPossibleMoves.addAll(possibleMoves);
		}

		return allPossibleMoves;
	}

	private boolean isBeatingPossible(List<PossibleMove> possibleMoves){
		return possibleMoves.stream()
				.anyMatch(move -> move.isBeating());
	}
	
	private boolean isBeatingPossible(char playerColor, Board board, Optional<Point> nextMove){
		return isBeatingPossible(generateAllPossibleMoves(playerColor, board, nextMove));
	}
	
	private boolean isBeatingPossible(char playerColor, Board board, Point source, Optional<Point> nextMove){
		return isBeatingPossible(generatePossibleMoves(playerColor, board, source, nextMove));
	}

	private List<PossibleMove> filterBeatingMoves(List<PossibleMove> moves){
		return possibleMoves.stream()
				.filter(move -> move.isBeating())
				.collect(collect(Collectors.toList());
	}

	private boolean isAnyMovePossible(char playerColor, Board board){
		List<Point> playerPawns = BoardLogic.listAllPlayerPawns(playerColor, board);
		for (Point source : playerPawns){
			char sourceField = board.getCell(source);
			// check if potential targets are valid
			List<Point> potentialTargets = BoardLogic.potentialTargets(source, sourceField);
			for(Point potentialTarget : potentialTargets){
				if (isMoveValid(playerColor, board, source, potentialTarget, Optional.empty())){
					return true;
				}
			}
		}
		return false;
	}
}