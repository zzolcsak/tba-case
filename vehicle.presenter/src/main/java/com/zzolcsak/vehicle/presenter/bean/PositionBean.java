package com.zzolcsak.vehicle.presenter.bean;

public class PositionBean {
	private Long id;
	private int x;
	private int y;

	public PositionBean() {
	}

	public PositionBean(Long id, int x, int y) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public Long getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "PositionBean [x=" + x + ", y=" + y + "]";
	}

}
