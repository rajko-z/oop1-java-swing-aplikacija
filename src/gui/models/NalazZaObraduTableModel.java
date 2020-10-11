package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Nalaz;
import services.NalaziServis;

public class NalazZaObraduTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1336632723036256803L;
	private String[] columnNames = { "Id", "Ime pacijenta", "Prezime pacijenta", "Broj analiza u nalazu", "Trenutno obradjeno", "Preostalo"};
	private List<Nalaz>data;
	private NalaziServis nalaziServis = new NalaziServis();
	
	
	public NalazZaObraduTableModel(List<Nalaz>data) {
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
		Nalaz n = data.get(row);
		switch (col) {
		case 0:
			return n.getId();
		case 1:
			return n.getZahtev().getPacijent().getIme();
		case 2:
			return n.getZahtev().getPacijent().getPrezime();
		case 3:
			return n.getBrojAnaliza();
		case 4:
			return nalaziServis.getBrojObradjenihAnaliza(n);
		case 5:
			return nalaziServis.getBrojPreostalihAnaliza(n);
		default:
			return null;
		}
	}

}
