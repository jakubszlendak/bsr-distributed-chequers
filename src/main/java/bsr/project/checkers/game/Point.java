package bsr.project.checkers.game;

public class Point {
	
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object p2) {
		if (!(p2 instanceof Point))
			return false;
		return this.x == ((Point) p2).x && this.y == ((Point) p2).y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public Point move(int xOffset, int yOffset){
		return new Point(x + xOffset, y + yOffset);
	}
}