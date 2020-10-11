package entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public abstract class DatumIDaniZaPopuste extends Entity {
	private LocalDate pocetak;
	private LocalDate kraj;
	private List<DayOfWeek> dani;
	
	public void setData(LocalDate pocetak, LocalDate kraj, List<DayOfWeek> dani) {
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.dani = dani;
	}

	public LocalDate getPocetak() {
		return pocetak;
	}

	public void setPocetak(LocalDate pocetak) {
		this.pocetak = pocetak;
	}

	public LocalDate getKraj() {
		return kraj;
	}

	public void setKraj(LocalDate kraj) {
		this.kraj = kraj;
	}

	public List<DayOfWeek> getDani() {
		return dani;
	}

	public void setDani(List<DayOfWeek> dani) {
		this.dani = dani;
	}

	@Override
	public String toString() {
		return "DatumiPopusta [pocetak=" + pocetak + ", kraj=" + kraj + ", dani=" + dani + "]";
	}

	@Override
	public String toFileEntity() {
		String dani = getDaneKaoString();
		return String.format("%-12s| %-12s| ", this.pocetak, this.kraj) + dani;
	}

	private String getDaneKaoString() {
		String retVal = "";
		for (DayOfWeek dan : this.dani) {
			if (this.dani.indexOf(dan) != (this.dani.size() - 1)) {
				retVal += String.valueOf(dan) + ",";
			} else {
				retVal += String.valueOf(dan);
			}
		}
		return retVal;
	}
}
