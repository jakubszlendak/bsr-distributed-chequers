public class MoveValidator {

	public void validateMove(char playerColor, char[][] board, Point source, Point target) throws InvalidMoveException {
		
		// TODO check if source pawn is owned by player

		// TODO check if target is not out of bounds

		// TODO check if target (x + y) % 2 == 1

		// TODO check if target field is empty
		
	}

	private char getCell(int x, int y) {
		if (x < 0 || y < 0 || x >= BOARD_SIZE || y >= BOARD_SIZE){
			throw new IllegalArgumentException("x or y out of bound");
		return board[x][y];
	}

	private char getCell(Point p){
		return getCell(p.x, p.y);
	}
}