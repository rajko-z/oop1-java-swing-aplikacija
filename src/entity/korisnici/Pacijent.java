package entity.korisnici;

import java.time.LocalDate;

public class Pacijent extends Korisnik {
	private String LBO;
	private String pol;
	private LocalDate datumRodjena;
	private String telefon;
	private String adresa;

	public Pacijent() {
	}

	public void setLBO(String lBO) {
		LBO = lBO;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public void setDatumRodjena(LocalDate datumRodjena) {
		this.datumRodjena = datumRodjena;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getLBO() {
		return LBO;
	}

	public String getPol() {
		return pol;
	}

	public LocalDate getDatumRodjena() {
		return datumRodjena;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getAdresa() {
		return adresa;
	}

	public boolean jeMuško() {
		if (this.pol.equals("muški")) {
			return true;
		}return false;
	}
	
	public Pacijent(int id, String ime, String prezime, String username, String password, String LBO, String pol,
			LocalDate datumRodjena, String telefon, String adresa) {
		super(id, ime, prezime, username, password);
		this.LBO = LBO;
		this.pol = pol;
		this.datumRodjena = datumRodjena;
		this.telefon = telefon;
		this.adresa = adresa;
	}

	public Pacijent(int id, String ime, String prezime, String LBO, String pol, LocalDate datumRodjena, String telefon,
			String adresa) {
		super(id, ime, prezime);
		this.LBO = LBO;
		this.pol = pol;
		this.datumRodjena = datumRodjena;
		this.telefon = telefon;
		this.adresa = adresa;
	}



	@Override
	public String toString() {
		return "Pacijent [LBO=" + LBO + ", pol=" + pol + ", datumRodjena=" + datumRodjena + ", telefon=" + telefon
				+ ", adresa=" + adresa + ", getIme()=" + getIme() + ", getPrezime()=" + getPrezime()
				+ ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getId()=" + getId()
				+ "]";
	}

	@Override
	public String toFileEntity() {
		if(this.getUsername() == null) {
			this.setUsername("");
			this.setPassword("");
		}
		return String.format("%-2d| %-2s| %-12s| %-14s| %-12s| %-15s| %-5s| %-7s| %-12s| %-11s| %s", this.getId(), "P",
				this.getIme(), this.getPrezime(), this.getUsername(), this.getPassword(), this.LBO, this.pol,
				this.datumRodjena, this.telefon, this.adresa);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pacijent other = (Pacijent) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}
	
}
