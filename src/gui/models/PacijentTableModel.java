package gui.models;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.korisnici.Pacijent;


public class PacijentTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 8304460420433202039L;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Pol", "LBO", "Datum rodjenja", "Broj telefona", "Adresa"};
	private List<Pacijent>pacijenti;
	
	public PacijentTableModel(List<Pacijent>pacijenti) {
		this.pacijenti = pacijenti;
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
		return pacijenti.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Pacijent p = pacijenti.get(row);
		switch (col) {
		case 0:
			return p.getId();
		case 1:
			return p.getIme();
		case 2:
			return p.getPrezime();
		case 3:
			return p.getPol();
		case 4:
			return p.getLBO();
		case 5:
			return p.getDatumRodjena();
		case 6:
			return p.getTelefon();
		case 7:
			return p.getAdresa();
		default:
			return null;
		}
	}

}
