package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import entity.PosebnaAnaliza;
import repositories.PosebnaAnalizaRepository;

public class AnalizaTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1145929959478070687L;
	private String[] columnNames = { "Id", "Naziv", "Grupa", "Cena", "Donja ref.vrednost", "Gornja ref.vrednost", "Jedinica mere"};
	
	private List<PosebnaAnaliza>data;
	
	
	public AnalizaTableModel(List<PosebnaAnaliza>data) {
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
		PosebnaAnaliza p = data.get(row);
		switch (col) {
		case 0:
			return p.getId();
		case 1:
			return p.getNaziv();
		case 2:
			return p.getGrupaAnaliza().getNaziv();
		case 3:
			return p.getCena();
		case 4:
			return p.getDonjaRefVrednost();
		case 5:
			return p.getGornjaRefVrednost();
		case 6:
			return p.getJedinicnaVrednost();
		default:
			return null;
		}
	}
	

}
