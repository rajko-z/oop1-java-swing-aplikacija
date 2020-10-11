package gui.models;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.StručnaSprema;

public class StručnaSpremaTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -2047301301427832666L;
	private String[] columnNames = { "Id", "Stručna sprema", "Koeficijent"};
	private List<StručnaSprema>data;
	
	public StručnaSpremaTableModel(List<StručnaSprema>data) {
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
		StručnaSprema s = data.get(row);
		switch (col) {
		case 0:
			return s.getId();
		case 1:
			return s.getOpis();
		case 2:
			return s.getKoeficijent();
		default:
			return null;
		}
	}

}
