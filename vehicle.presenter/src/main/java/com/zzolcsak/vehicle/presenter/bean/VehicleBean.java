package com.zzolcsak.vehicle.presenter.bean;

public class VehicleBean {
	private Long id;
	private PositionBean position;

	public VehicleBean() {
	}

	public VehicleBean(Long id, PositionBean position) {
		super();
		this.id = id;
		this.position = position;
	}

}
