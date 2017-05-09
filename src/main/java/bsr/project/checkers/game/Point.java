package bsr.project.checkers.game;

public class Point {
	
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point p2) {
		return this.x == p2.x && this.y == p2.y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}