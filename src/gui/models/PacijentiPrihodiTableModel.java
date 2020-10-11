package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Nalaz;
import entity.korisnici.Pacijent;
import services.PacijentServis;

public class PacijentiPrihodiTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -5273227275569275713L;
	
	private String[] columnNames = {"Ime", "Prezime", "Pol", "LBO", "Datum rodjenja", "Broj telefona", "Broj nalaza", "Ukupna naplata"};
	private List<Pacijent>pacijenti;
	private List<Nalaz>nalaziZaPretragu;
	private PacijentServis pacijentServis = new PacijentServis();
	
	public PacijentiPrihodiTableModel(List<Pacijent>pacijenti, List<Nalaz>nalazi) {
		this.pacijenti = pacijenti;
		this.nalaziZaPretragu = nalazi;
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
			return p.getIme();
		case 1:
			return p.getPrezime();
		case 2:
			return p.getPol();
		case 3:
			return p.getLBO();
		case 4:
			return p.getDatumRodjena();
		case 5:
			return p.getTelefon();
		case 6:
			return pacijentServis.getBrojNalazaZaPacijenta(p, nalaziZaPretragu);
		case 7:
			return pacijentServis.getUkupnaCenaNalaza(p, nalaziZaPretragu);
		default:
			return null;
		}
	}

	
}
