package utils;

import entity.GrupaAnaliza;

public class CheckBoxListItemAnaliza {
	private GrupaAnaliza grupaAnaliza;
	private boolean isSelected = false;
	
	public CheckBoxListItemAnaliza(GrupaAnaliza grupaAnaliza) {
		this.grupaAnaliza = grupaAnaliza;
	}
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public void setGrupaAnaliza(GrupaAnaliza grupaAnaliza) {
		this.grupaAnaliza = grupaAnaliza;
	}
	
	public GrupaAnaliza getGrupaAnaliza() {
		return grupaAnaliza;
	}
	@Override
	public String toString() {
		return grupaAnaliza.getNaziv();
	}
	
}
