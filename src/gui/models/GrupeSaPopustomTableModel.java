package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.GrupaAnaliza;
import services.AnalizeServis;

public class GrupeSaPopustomTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -6027538734948168135L;

	private String[] columnNames = { "Id", "Naziv grupe analiza", "Popust grupe (%)"};

	private List<GrupaAnaliza> data;
	private AnalizeServis analizeServis = new AnalizeServis();
	
	public GrupeSaPopustomTableModel(List<GrupaAnaliza>data) {
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
		GrupaAnaliza grupa = data.get(row);
		switch (col) {
		case 0:
			return grupa.getId();
		case 1:
			return grupa.getNaziv();
		case 2:
			return analizeServis.getPopustZaGrupu(grupa);
		default:
			return null;
		}
	}
}
