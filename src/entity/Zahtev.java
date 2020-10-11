package entity;

import java.time.LocalDate;
import java.util.List;

import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;

public class Zahtev extends Entity {

	private Pacijent pacijent;
	private List<PosebnaAnaliza> analize;
	private StanjeZahteva stanjeZahteva;
	private Dostava dostava;
	private MedicinskiTehničar medicinskiTehničar;
	private LocalDate datumObrade;
	private double cena;

	
	public Zahtev() {
	}
	
	public Zahtev(int id) {
		super(id);
	}
	
	public Zahtev(Integer id, Pacijent pacijent, List<PosebnaAnaliza> analize, StanjeZahteva stanjeZahteva,
			Dostava dostava, MedicinskiTehničar medicinskiTehničar, LocalDate datumObrade, double cena) {
		super(id);
		this.pacijent = pacijent;
		this.analize = analize;
		this.stanjeZahteva = stanjeZahteva;
		this.dostava = dostava;
		this.medicinskiTehničar = medicinskiTehničar;
		this.datumObrade = datumObrade;
		this.cena = cena;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setAnalize(List<PosebnaAnaliza> analize) {
		this.analize = analize;
	}

	public void setStanjeZahteva(StanjeZahteva stanjeZahteva) {
		this.stanjeZahteva = stanjeZahteva;
	}

	public void setDostava(Dostava dostava) {
		this.dostava = dostava;
	}

	public void setMedicinskiTehničar(MedicinskiTehničar medicinskiTehničar) {
		this.medicinskiTehničar = medicinskiTehničar;
	}

	public void setDatumObrade(LocalDate datumObrade) {
		this.datumObrade = datumObrade;
	}
	
	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public List<PosebnaAnaliza> getAnalize() {
		return analize;
	}

	public StanjeZahteva getStanjeZahteva() {
		return stanjeZahteva;
	}

	public Dostava getDostava() {
		return dostava;
	}

	public MedicinskiTehničar getMedicinskiTehničar() {
		return medicinskiTehničar;
	}

	public LocalDate getDatumObrade() {
		return datumObrade;
	}
	
	public double getCena() {
		return cena;
	}

	@Override
	public String toFileEntity() {
		String analizeIDs = getAnalizeIDs();
		return String.format("%-2s| %-4s| %-26s| %-3s| %-3s| %-5s| %-11s| %s", this.getId(), this.pacijent.getId(), analizeIDs,
				this.stanjeZahteva.getId(), this.dostava.getId(), this.medicinskiTehničar.getId(), this.datumObrade, this.cena);
	}

	@Override
	public String toString() {
		return "Zahtev [pacijent=" + pacijent + ", analize=" + analize + ", stanjeZahteva=" + stanjeZahteva
				+ ", dostava=" + dostava + ", medicinskiTehničar=" + medicinskiTehničar + ", datumObrade=" + datumObrade
				+ "]" + "cena:" + cena;
	}

	private String getAnalizeIDs() {
		String retVal = "";
		for (PosebnaAnaliza a : this.analize) {
			if (this.analize.indexOf(a) != (this.analize.size() - 1)) {
				retVal += String.valueOf(a.getId()) + ",";
			} else {
				retVal += String.valueOf(a.getId());
			}
		}
		return retVal;
	}

}
