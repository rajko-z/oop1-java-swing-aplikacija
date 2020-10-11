package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Dostava;
import entity.Zahtev;
import entity.korisnici.MedicinskiTehničar;

public class ZahtevTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -3855334862989541317L;
	private String[] columnNames = { "Id", "Stanje", "LBO", "Ime pacijenta", "Prezime pacijenta", 
			"<html>Broj zahtevanih <br>analiza u zahtevu", "Kućna poseta",
			"<html>Datum željene<br>posete ili dostave", "Vreme posete", "Datum podnošenja",
			"<html>Ime medinskog<br>tehničara", "Prezime"}; 
	
	private List<Zahtev> data;
	
	public ZahtevTableModel(List<Zahtev> data) {
		super();
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
		Zahtev zahtev = data.get(row);
		Dostava dostava = zahtev.getDostava();
		MedicinskiTehničar medicinskiTehničar = zahtev.getMedicinskiTehničar();
		switch (col) {
		case 0:
			return zahtev.getId();
		case 1:
			return zahtev.getStanjeZahteva().getOpis();
		case 2:
			return zahtev.getPacijent().getLBO();
		case 3:
			return zahtev.getPacijent().getIme();
		case 4:
			return zahtev.getPacijent().getPrezime();
		case 5:
			return zahtev.getAnalize().size();
		case 6:
			if (dostava.isKućnaDostava()) return "DA";
			else return "NE";
		case 7:
			if (dostava.isBezZahteva()) {
				return "nema";
			}
			return dostava.getDatumDostave().toString();
		case 8:
			if (dostava.isBezZahteva() | !dostava.isKućnaDostavaSaVremenom()) {
				return "nema";
			}
			return dostava.getVremeDostave().toString();
		case 9:
			return zahtev.getDatumObrade();
		case 10:
			if (medicinskiTehničar.getId() == 0) {
				return "još nije obrađeno";
			}
			return medicinskiTehničar.getIme();
		case 11:
			if (medicinskiTehničar.getId() == 0) {
				return "još nije obrađeno";
			}
			return medicinskiTehničar.getPrezime();
		default:
			return null;
		}
	}
}
