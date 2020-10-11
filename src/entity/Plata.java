package entity;

import java.time.Month;
import java.time.Year;

import entity.korisnici.Korisnik;

public class Plata extends Entity {
	
	private Year godina;
	private Month mesec;
	private Korisnik zaposleni;
	private double redovnaPlata;
	private double bonus;
	private double ukupnaPlata;
	
	public Plata() {
	}
	
	public Plata(int id,Year godina, Month mesec, Korisnik zaposleni, double redovnaPlata, double bonus) {
		super(id);
		this.godina = godina;
		this.mesec = mesec;
		this.zaposleni = zaposleni;
		this.redovnaPlata = redovnaPlata;
		this.bonus = bonus;
		this.ukupnaPlata = redovnaPlata + bonus;
	}

	public Year getGodina() {
		return godina;
	}

	public Month getMesec() {
		return mesec;
	}

	public Korisnik getZaposleni() {
		return zaposleni;
	}

	public double getRedovnaPlata() {
		return redovnaPlata;
	}

	public double getBonus() {
		return bonus;
	}

	public double getUkupnaPlata() {
		return ukupnaPlata;
	}
	public void setGodina(Year godina) {
		this.godina = godina;
	}

	public void setMesec(Month mesec) {
		this.mesec = mesec;
	}

	public void setZaposleni(Korisnik zaposleni) {
		this.zaposleni = zaposleni;
	}

	public void setRedovnaPlata(double redovnaPlata) {
		this.redovnaPlata = redovnaPlata;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public void setUkupnaPlata(double ukupnaPlata) {
		this.ukupnaPlata = ukupnaPlata;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-5s| %-5s| %-11s| %-4s| %-9s | %s", this.getId(), godina, mesec, zaposleni.getId(), redovnaPlata, bonus);
	}

	@Override
	public String toString() {
		return "Plata [godina=" + godina + ", mesec=" + mesec + ", zaposleni=" + zaposleni + ", redovnaPlata="
				+ redovnaPlata + ", bonus=" + bonus + ", ukupnaPlata=" + ukupnaPlata + "]";
	}

	
	
}
