package entity;

import entity.korisnici.Laborant;

public class AnalizaZaObradu extends Entity {

	private PosebnaAnaliza analiza;
	private double izmerenaVrednost;
	private boolean jesteObradjena;
	private Laborant laborant;
	
	public AnalizaZaObradu() {
	}
	
	public AnalizaZaObradu(int id) {
		super(id);
	}
	
	public AnalizaZaObradu(int id, PosebnaAnaliza analiza, double izmerenaVrednost, boolean jesteObradjena,
			Laborant laborant) {
		super(id);
		this.analiza = analiza;
		this.izmerenaVrednost = izmerenaVrednost;
		this.jesteObradjena = jesteObradjena;
		this.laborant = laborant;
	}

	public AnalizaZaObradu(int id, PosebnaAnaliza analiza, boolean jesteObradjena, Laborant laborant) {
		super(id);
		this.analiza = analiza;
		this.jesteObradjena = jesteObradjena;
		this.laborant = laborant;
	}

	public PosebnaAnaliza getAnaliza() {
		return analiza;
	}

	public double getIzmerenaVrednost() {
		return izmerenaVrednost;
	}

	public boolean isJesteObradjena() {
		return jesteObradjena;
	}

	public Laborant getLaborant() {
		return laborant;
	}

	public void setIzmerenaVrednost(double izmerenaVrednost) {
		this.izmerenaVrednost = izmerenaVrednost;
	}

	public void setJesteObradjena(boolean jesteObradjena) {
		this.jesteObradjena = jesteObradjena;
	}

	public void setLaborant(Laborant laborant) {
		this.laborant = laborant;
	}

	
	@Override
	public String toString() {
		return "AnalizaZaObradu " + "id:" + this.getId() + "[analiza=" + analiza + ", izmerenaVrednost=" + izmerenaVrednost + ", jesteObradjena="
				+ jesteObradjena + ", laborant=" + laborant + "]";
	}

	@Override
	public String toFileEntity() {
		return String.format("%-3s| %-6s| %-9s| %-7s | %s", this.getId(), this.getAnaliza().getId(),
				      this.izmerenaVrednost, this.jesteObradjena, this.getLaborant().getId());
	}
	
}
