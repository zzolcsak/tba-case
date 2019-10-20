package com.zzolcsak.vehicles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a position on a discrete two-dimensional net. The directions are
 * negative x(left), positive x(right), negative y(down), positive y(up).
 */
@Entity
@Table(name = "position")
public class Position {
	public static enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	public Position() {
		x = 0;
		y = 0;
	}

	public static Position CENTER = Position.of(0, 0);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "x")
	private final int x;

	@Column(name = "y")
	private final int y;

	private Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position of(int x, int y) {
		return new Position(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Position transform(Direction direction) {
		if (direction == null) {
			return null;
		}
		switch (direction) {
		case LEFT:
			return Position.of(x - 1, y);
		case RIGHT:
			return Position.of(x + 1, y);
		case UP:
			return Position.of(x, y + 1);
		case DOWN:
			return Position.of(x, y - 1);
		default:
			throw new UnsupportedOperationException("Can't move in direction " + direction);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
