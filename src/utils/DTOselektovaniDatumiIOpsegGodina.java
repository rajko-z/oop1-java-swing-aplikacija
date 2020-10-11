package utils;

import java.time.LocalDate;

import entity.PosebnaAnaliza;

public class DTOselektovaniDatumiIOpsegGodina {
	private LocalDate pocetak;
	private LocalDate kraj;
	private int donjaGranicaGodina;
	private int gornjaGranicaGodina;
	private PosebnaAnaliza analiza;
	
	public DTOselektovaniDatumiIOpsegGodina() {
	}
	
	public DTOselektovaniDatumiIOpsegGodina(LocalDate pocetak, LocalDate kraj, int donjaGranicaGodina,
			int gornjaGranicaGodina, PosebnaAnaliza analiza) {
		this();
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.donjaGranicaGodina = donjaGranicaGodina;
		this.gornjaGranicaGodina = gornjaGranicaGodina;
		this.analiza = analiza;
	}

	public PosebnaAnaliza getAnaliza() {
		return analiza;
	}

	public LocalDate getPocetak() {
		return pocetak;
	}

	public LocalDate getKraj() {
		return kraj;
	}

	public int getDonjaGranicaGodina() {
		return donjaGranicaGodina;
	}

	public int getGornjaGranicaGodina() {
		return gornjaGranicaGodina;
	}

}
