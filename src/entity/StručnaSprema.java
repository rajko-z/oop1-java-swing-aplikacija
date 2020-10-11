package entity;

public class StručnaSprema extends Entity{
	
	private double koeficijent;
	private String opis;
	
	public StručnaSprema() {
	}
	
	public StručnaSprema(int id, double koeficijent, String opis) {
		super(id);
		this.koeficijent = koeficijent;
		this.opis = opis;
	}

	public double getKoeficijent() {
		return koeficijent;
	}

	public void setKoeficijent(double koeficijent) {
		this.koeficijent = koeficijent;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-2s| %-8s| %s", this.getId(), this.getKoeficijent(),  this.opis);
	}

	@Override
	public String toString() {
		return opis;
	}

	
	

}
