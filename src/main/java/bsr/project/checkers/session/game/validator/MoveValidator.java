package bsr.project.checkers.session.game.validator;

import bsr.project.checkers.session.game.Board;
import bsr.project.checkers.session.game.Point;

public class MoveValidator {
	
	public static void validateMove(char playerColor, Board board, Point source, Point target) throws InvalidMoveException {
		
		// TODO check if source pawn is owned by player
		
		// TODO check if target is not out of bounds
		
		// TODO check if target (x + y) % 2 == 1
		
		// TODO check if target field is empty
		
	}
	
}