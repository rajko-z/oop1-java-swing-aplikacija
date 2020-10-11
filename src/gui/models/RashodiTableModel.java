package gui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import services.KorisniciServis;
import services.LaborantServis;
import services.MedicinskiTehničarServis;
import utils.DTOselektovaniDaniIDatumi;

public class RashodiTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 4827978266404127180L;
	private String[] columnNames = { "Id", "Ime", "Prezime", "Broj obradjenih nalaza/zahteva", "Plata"};
	private List<Korisnik>zaposleni;
	private DTOselektovaniDaniIDatumi dto;
	private LaborantServis laborantServis = new LaborantServis();
	private MedicinskiTehničarServis mTehničarServis = new MedicinskiTehničarServis();
	private KorisniciServis korisniciServis = new KorisniciServis();

	
	public RashodiTableModel(List<Korisnik>korisnici, DTOselektovaniDaniIDatumi dtoDatumiDani) {
		this.zaposleni = korisnici;
		this.dto = dtoDatumiDani;
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
		return zaposleni.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Korisnik k = zaposleni.get(row);
		switch (col) {
		case 0:
			return k.getId();
		case 1:
			return k.getIme();
		case 2:
			return k.getPrezime();
		case 3:
			if (k instanceof Laborant) {
				return laborantServis.getBrojObradjenihNalazUZadatomPeriouduSaDanima((Laborant)k, dto.getPocetak() , dto.getKraj(), dto.getDani());
			}else {
				return mTehničarServis.getBrojObradjenihZahtevaUZadatomPerioduSaDanima((MedicinskiTehničar)k, dto.getPocetak(), dto.getKraj(), dto.getDani());
			}
		case 4:
			return korisniciServis.getIznosPlateUZadatomPerioduSaDanima(k, dto.getPocetak(), dto.getKraj(), dto.getDani());
		default:
			return null;
		}
	}
}
