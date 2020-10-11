package gui.models;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.AnalizaZaObradu;

public class AnalizeZaObraduTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1745678517763471529L;
	private String[] columnNames = { "Id", "Grupa", "Analiza", "Analiza je izmerena", "Donja ref.vrednost", "Gornja ref.vrednost", "Izmerena vrednost", "Ime laboranta", "Prezime laboratna"};
	private List<AnalizaZaObradu>data;
	
	public AnalizeZaObraduTableModel(List<AnalizaZaObradu>data) {
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
		AnalizaZaObradu a = data.get(row);
		switch (col) {
		case 0:
			return a.getId();
		case 1:
			return a.getAnaliza().getGrupaAnaliza().getNaziv();
		case 2:
			return a.getAnaliza().getNaziv();
		case 3:
			if (a.isJesteObradjena()) {
				return "DA";
			}
			return "NE";
		case 4:
			return a.getAnaliza().getDonjaRefVrednost();
		case 5:
			return a.getAnaliza().getGornjaRefVrednost();
		case 6:
			if (a.isJesteObradjena()) {
				return String.valueOf(a.getIzmerenaVrednost());
			}
			return "nije obradjena";
		case 7:
			if (a.isJesteObradjena()) {
				return a.getLaborant().getIme();
			}
			return "nije obradjeno";
		case 8:
			if (a.isJesteObradjena()) {
				return a.getLaborant().getPrezime();
			}
			return "nije obradjeno";
		default:
			return null;
		}
	}
}
