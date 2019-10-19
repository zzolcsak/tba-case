package com.zzolcsak.vehicles.model;

public class Vehicle {
	private Position position;
	
	public Vehicle() {
		position = Position.CENTER;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
}
