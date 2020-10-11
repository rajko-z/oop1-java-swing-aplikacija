package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.korisnici.Korisnik;

public class KorisniciTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -735426345470092656L;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Korisničko ime", "Lozinka"};
	private List<Korisnik>data;
	
	
	public KorisniciTableModel(List<Korisnik>data) {
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
		Korisnik k = data.get(row);
		switch (col) {
		case 0:
			return k.getId();
		case 1:
			return k.getIme();
		case 2:
			return k.getPrezime();
		case 3:
			return k.getUsername();
		case 4:
			return k.getPassword();
		default:
			return null;
		}
	}
}
