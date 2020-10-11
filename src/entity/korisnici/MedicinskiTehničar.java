package entity.korisnici;

import entity.StručnaSprema;

public class MedicinskiTehničar extends Korisnik {
	private StručnaSprema sprema;
	private boolean validan;  // kada dobije otkaz vise nije
	
	public MedicinskiTehničar() {
	}

	public MedicinskiTehničar(int id, String ime, String prezime, String username, String password, StručnaSprema sprema, boolean validan) {
		super(id, ime,  prezime, username, password);
		this.sprema = sprema;
		this.validan = validan;
	}
	

	public StručnaSprema getSprema() {
		return sprema;
	}

	public void setSprema(StručnaSprema sprema) {
		this.sprema = sprema;
	}
	
	
	public boolean isValidan() {
		return validan;
	}

	public void setValidan(boolean validan) {
		this.validan = validan;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-2d| %-2s| %-12s| %-14s| %-12s| %-15s| %-2d| %s", this.getId(), "M",
				this.getIme(), this.getPrezime(), this.getUsername(), this.getPassword(),
				this.sprema.getId(), this.validan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicinskiTehničar other = (MedicinskiTehničar) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedicinskiTehničar [sprema=" + sprema + ", getIme()=" + getIme() + ", getPrezime()=" + getPrezime()
				+ ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getId()=" + getId()
				+ "]" + "validan" + validan;
	}
	
}
