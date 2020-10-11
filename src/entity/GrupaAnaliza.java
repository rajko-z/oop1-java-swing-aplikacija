package entity;

public class GrupaAnaliza extends Entity{
	private String naziv;
	private boolean validna;
	
	public GrupaAnaliza() {
	}

	public GrupaAnaliza(int id, String naziv, boolean validna) {
		super(id);
		this.naziv = naziv;
		this.validna = validna;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isValidna() {
		return validna;
	}

	public void setValidna(boolean validna) {
		this.validna = validna;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-2s| %-30s| %s", this.getId(), this.naziv, this.validna);
	}

	@Override
	public String toString() {
		return String.format("Naziv grupe:%s",this.naziv);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupaAnaliza other = (GrupaAnaliza) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}
	
}
