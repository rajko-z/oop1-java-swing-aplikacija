package entity;

import java.time.LocalDate;
import java.util.List;

import org.junit.validator.PublicClassValidator;

public class Nalaz extends Entity implements Comparable<Nalaz> {

	private Zahtev zahtev;
	private List<AnalizaZaObradu> analizeZaObradu;
	private LocalDate datumObrade;
	private int brojAnaliza;

	public Nalaz() {
	}
	
	public Nalaz(int id) {
		super(id);
	}
	
	public Nalaz(int id, Zahtev zahtev, List<AnalizaZaObradu> analizeZaObradu, LocalDate datumObrade) {
		super(id);
		this.zahtev = zahtev;
		this.analizeZaObradu = analizeZaObradu;
		this.datumObrade = datumObrade;
		this.brojAnaliza = this.zahtev.getAnalize().size();
	}
	
	public Nalaz(int id, Zahtev zahtev, List<AnalizaZaObradu> analizeZaObradu) {
		super(id);
		this.zahtev = zahtev;
		this.analizeZaObradu = analizeZaObradu;
		this.brojAnaliza = this.zahtev.getAnalize().size();
	}
	public Zahtev getZahtev() {
		return zahtev;
	}

	public void setZahtev(Zahtev zahtev) {
		this.zahtev = zahtev;
	}
	
	public void setBrojAnaliza(int brojAnaliza) {
		this.brojAnaliza = brojAnaliza;
	}

	public List<AnalizaZaObradu> getAnalizeZaObradu() {
		return analizeZaObradu;
	}

	public void setAnalizeZaObradu(List<AnalizaZaObradu> analizeZaObradu) {
		this.analizeZaObradu = analizeZaObradu;
	}

	public LocalDate getDatumObrade() {
		return datumObrade;
	}

	public int getBrojAnaliza() {
		return brojAnaliza;
	}
	
	public void setDatumObrade(LocalDate datumObrade) {
		this.datumObrade = datumObrade;
	}
	

	@Override
	public String toString() {
		return "Nalaz [zahtev=" + zahtev + ", analizeZaObradu=" + analizeZaObradu + ", datumObrade=" + datumObrade
				+ "]";
	}

	@Override
	public String toFileEntity() {
		String analizeZaObraduIds =  getAnalizeZaObraduIds();
		return String.format("%-3s| %-3s | %-30s| %s", this.getId(), this.zahtev.getId(), analizeZaObraduIds, this.datumObrade); 
	}

	private String getAnalizeZaObraduIds() {
		String retVal = "";
		for (AnalizaZaObradu a: this.analizeZaObradu) {
			if (this.analizeZaObradu.indexOf(a) != (this.analizeZaObradu.size() - 1)) {
				retVal += a.getId() + ",";
			}
			else {
				retVal += a.getId();
			}
		}
		return retVal;
	}

	@Override
	public int compareTo(Nalaz o) {
		return this.getDatumObrade().compareTo(o.getDatumObrade());
	}

	

}
