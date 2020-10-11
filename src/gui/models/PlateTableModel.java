package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Plata;

public class PlateTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -1841652908903077120L;
	private String[] columnNames = { "Id","Godina", "Mesec", "Ime", "Prezime", "Redovna plata", "Bonus", "Ukupna plata"};
	private List<Plata>data;
	

	public PlateTableModel(List<Plata>data) {
		this.data = data;
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		return this.getValueAt(0, column).getClass();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Plata p = data.get(row);
		switch (col) {
		case 0:
			return p.getId();
		case 1:
			return p.getGodina();
		case 2:
			return p.getMesec();
		case 3:
			return p.getZaposleni().getIme();
		case 4:
			return p.getZaposleni().getPrezime();
		case 5:
			return p.getRedovnaPlata();
		case 6:
			return p.getBonus();
		case 7:
			return p.getUkupnaPlata();
		default:
			return null;
		}
	}

}
