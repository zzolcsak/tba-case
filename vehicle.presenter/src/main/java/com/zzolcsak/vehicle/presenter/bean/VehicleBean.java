package com.zzolcsak.vehicle.presenter.bean;

public class VehicleBean implements Comparable<VehicleBean> {
	private Long id;
	private PositionBean position;

	public VehicleBean() {
	}

	public VehicleBean(Long id, PositionBean position) {
		super();
		this.id = id;
		this.position = position;
	}

	public Long getId() {
		return id;
	}

	public PositionBean getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "VehicleBean [id=" + id + ", position=" + position + "]";
	}

	@Override
	public int compareTo(VehicleBean o) {
		return id.compareTo(o.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		VehicleBean other = (VehicleBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
