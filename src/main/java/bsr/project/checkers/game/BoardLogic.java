package bsr.project.checkers.game;

import java.util.ArrayList;
import java.util.List;

import bsr.project.checkers.protocol.BoardSymbols;

public class BoardLogic {
	
	public static boolean isOutOfBounds(Point p) {
		return p.x < 0 || p.y < 0 || p.x >= Board.BOARD_SIZE || p.y >= Board.BOARD_SIZE;
	}
	
	public static boolean isWhite(char field) {
		return field == BoardSymbols.WHITE_PAWN || field == BoardSymbols.WHITE_KING;
	}
	
	public static boolean isBlack(char field) {
		return field == BoardSymbols.BLACK_PAWN || field == BoardSymbols.BLACK_KING;
	}
	
	public static boolean isPawn(char field) {
		return field == BoardSymbols.WHITE_PAWN || field == BoardSymbols.BLACK_PAWN;
	}
	
	public static boolean isKing(char field) {
		return field == BoardSymbols.WHITE_KING || field == BoardSymbols.BLACK_KING;
	}
	
	public static boolean isEmpty(char field) {
		return field == BoardSymbols.EMPTY;
	}
	
	public static char pawnToKing(char field) {
		if (field == BoardSymbols.WHITE_PAWN)
			return BoardSymbols.WHITE_KING;
		if (field == BoardSymbols.BLACK_PAWN)
			return BoardSymbols.BLACK_KING;
		return field;
	}
	
	public static boolean isSameColor(char field1, char field2) {
		if (isWhite(field1) && isWhite(field2))
			return true;
		if (isBlack(field1) && isBlack(field2))
			return true;
		return false;
	}
	
	public static boolean isOppositeColor(char field1, char field2) {
		if (isWhite(field1) && isBlack(field2))
			return true;
		if (isBlack(field1) && isWhite(field2))
			return true;
		return false;
	}
	
	public static int abs(int number) {
		return number >= 0 ? number : -number;
	}
	
	public static Point pointBetween(Point p1, Point p2) {
		int x = (p1.x + p2.x) / 2;
		int y = (p1.y + p2.y) / 2;
		return new Point(x, y);
	}
	
	public static List<Point> pointsBetween(Point p1, Point p2) {
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
	
	public static boolean isMovingBackwards(char field, Point source, Point target) {
		int dy = target.y - source.y; // dodatni kierunek - ruch w dół planszy (w kierunku białych)
		if (isWhite(field)) {
			return dy > 0;
		} else if (isBlack(field)) {
			return dy < 0;
		}
		return false;
	}
	
	public static boolean isOnBoardEnd(char movingColor, Point p) {
		if (isWhite(movingColor)) {
			// dla białych - koniec planszy w y = 0
			return p.y == 0;
		} else if (isBlack(movingColor)) {
			// dla czarnych - koniec planszy na samym dole
			return p.y == Board.BOARD_SIZE - 1;
		}
		return false;
	}
	
}
