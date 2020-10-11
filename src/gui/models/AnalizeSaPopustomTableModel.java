package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.PosebnaAnaliza;
import services.AnalizeServis;

public class AnalizeSaPopustomTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4734124781313088560L;

	private String[] columnNames = { "Id", "Naziv", "Grupa", "Donja ref.vrednost", "Gornja ref.vrednost",
			"Jedinica mere", "Redovna cena", "Popust(%)", "Cena sa popustom" };

	private List<PosebnaAnaliza> data;
	private AnalizeServis analizeServis = new AnalizeServis();

	public AnalizeSaPopustomTableModel(List<PosebnaAnaliza>data) {
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
			return p.getDonjaRefVrednost();
		case 4:
			return p.getGornjaRefVrednost();
		case 5:
			return p.getJedinicnaVrednost();
		case 6:
			return p.getCena();
		case 7:
			return analizeServis.getPopustZaAnalizu(p);
		case 8:
			return analizeServis.getCenaSaPopustom(p);
		default:
			return null;
		}
	}
}
