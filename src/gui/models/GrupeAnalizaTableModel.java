package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.GrupaAnaliza;

public class GrupeAnalizaTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 2883401321128763632L;

	private String[] columnNames = { "Id", "Naziv grupe analiza"};

	private List<GrupaAnaliza> data;

	public GrupeAnalizaTableModel(List<GrupaAnaliza>data) {
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
		default:
			return null;
		}
	}
}
