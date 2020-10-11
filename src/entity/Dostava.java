package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Dostava extends Entity{
	
	private boolean kućnaDostava;
	private boolean kućnaDostavaSaVremenom;
	private LocalDate datumDostave;
	private LocalTime vremeDostave;
	private boolean bezZahteva;
	

	public Dostava() {
		this.bezZahteva = true;
	}
	
	public Dostava(Integer id, boolean kućnaDostava, LocalDate datumDostave, LocalTime vremeDostave) {
		super(id);
		this.kućnaDostava = kućnaDostava;
		this.datumDostave = datumDostave;
		this.vremeDostave = vremeDostave;
		this.kućnaDostavaSaVremenom = true;
	}
	
	public Dostava(Integer id, boolean kućnaDostava, LocalDate datumDostave) {
		super(id);
		this.kućnaDostava = kućnaDostava;
		this.datumDostave = datumDostave;
		this.kućnaDostavaSaVremenom = false;
	}

	public boolean isKućnaDostava() {
		return kućnaDostava;
	}
	
	public boolean isBezZahteva() {
		return bezZahteva;
	}

	public LocalDate getDatumDostave() {
		return datumDostave;
	}


	public LocalTime getVremeDostave() {
		return vremeDostave;
	}
	

	public void setKućnaDostava(boolean kućnaDostava) {
		this.kućnaDostava = kućnaDostava;
	}

	public void setDatumDostave(LocalDate datumDostave) {
		this.datumDostave = datumDostave;
	}

	public void setVremeDostave(LocalTime vremeDostave) {
		this.vremeDostave = vremeDostave;
	}
	
	public boolean isKućnaDostavaSaVremenom() {
		return kućnaDostavaSaVremenom;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-3s| %-7s| %-11s| %s", this.getId(), this.kućnaDostava, this.datumDostave, this.vremeDostave);
	}

	@Override
	public String toString() {
		return "Dostava [kućnaDostava=" + kućnaDostava + ", datumDostave=" + datumDostave + ", vremeDostave="
				+ vremeDostave + "]";
	}

	
	
	
}
