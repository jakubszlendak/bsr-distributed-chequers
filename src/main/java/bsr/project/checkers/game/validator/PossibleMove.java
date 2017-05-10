package bsr.project.checkers.game.validator;

import java.util.ArrayList;
import java.util.List;

import bsr.project.checkers.game.Point;
import bsr.project.checkers.game.BoardLogic;

public class PossibleMove {
	
	private Point source;
	private Point target;
	
	public PossibleMove(Point source, Point target) {
		this.source = source;
		this.target = target;
	}

	public Point getSource(){
		return source;
	}

	public Point getTaret(){
		return target;
	}

	public boolean isBeating(){
		// bicie jest wtedy, gdy ruch o więcej niż jedno pole
		int dx = BoardLogic.absDX(source, target);
		int dy = BoardLogic.absDY(source, target);
		return dx > 1 || dy > 1;
	}

	@Override
	public boolean equals(Object m2) {
		if (!(m2 instanceof PossibleMove))
			return false;
		return source.equals(((PossibleMove) m2).source) && target.equals(((PossibleMove) m2).target);
	}

}