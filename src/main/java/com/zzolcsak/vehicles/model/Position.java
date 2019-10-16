package com.zzolcsak.vehicles.model;

public class Position {
	public static Position CENTER = Position.of(0, 0);
	
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Position of(int x, int y) {
		return new Position(x, y);
	}
}
