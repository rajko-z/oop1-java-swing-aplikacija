package entity;

public class PosebnaAnaliza extends Entity {
	private String naziv;
	private GrupaAnaliza grupaAnaliza;
	private double cena;
	private double donjaRefVrednost;
	private double gornjaRefVrednost;
	private String jedinicnaVrednost;
	private boolean validna;

	public PosebnaAnaliza() {
	}

	public PosebnaAnaliza(int id, String naziv, GrupaAnaliza grupaAnaliza, double cena, double donjaRefVrednost,
			double gornjaRefVrednost, String jedinicnaVrednost, boolean validna) {
		super(id);
		this.naziv = naziv;
		this.grupaAnaliza = grupaAnaliza;
		this.cena = cena;
		this.donjaRefVrednost = donjaRefVrednost;
		this.gornjaRefVrednost = gornjaRefVrednost;
		this.jedinicnaVrednost = jedinicnaVrednost;
		this.validna = validna;
	}

	public String getNaziv() {
		return naziv;
	}

	public GrupaAnaliza getGrupaAnaliza() {
		return grupaAnaliza;
	}
	
	public double getCena() {
		return cena;
	}

	public double getDonjaRefVrednost() {
		return donjaRefVrednost;
	}

	public double getGornjaRefVrednost() {
		return gornjaRefVrednost;
	}

	public String getJedinicnaVrednost() {
		return jedinicnaVrednost;
	}
	
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setGrupaAnaliza(GrupaAnaliza grupaAnaliza) {
		this.grupaAnaliza = grupaAnaliza;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public void setDonjaRefVrednost(double donjaRefVrednost) {
		this.donjaRefVrednost = donjaRefVrednost;
	}

	public void setGornjaRefVrednost(double gornjaRefVrednost) {
		this.gornjaRefVrednost = gornjaRefVrednost;
	}

	public void setJedinicnaVrednost(String jedinicnaVrednost) {
		this.jedinicnaVrednost = jedinicnaVrednost;
	}

	public boolean isValidna() {
		return validna;
	}

	public void setValidna(boolean validna) {
		this.validna = validna;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-3s| %-22s| %-2s| %-7s| %-5s| %-6s| %-7s| %s", this.getId(), this.naziv, this.grupaAnaliza.getId(),
				this.cena, this.donjaRefVrednost, this.gornjaRefVrednost, this.jedinicnaVrednost, this.validna);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PosebnaAnaliza other = (PosebnaAnaliza) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Id: %s Naziv: %s Cena: %s", this.getId(), this.getNaziv(), this.getCena());
	}
	
	
	

}
