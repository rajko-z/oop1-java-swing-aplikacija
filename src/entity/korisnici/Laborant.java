package entity.korisnici;

import java.util.List;

import entity.GrupaAnaliza;
import entity.StručnaSprema;

public class Laborant extends Korisnik {
	private StručnaSprema sprema;
	private List<GrupaAnaliza> grupeAnaliza;
	private boolean validan;
	
	
	public StručnaSprema getSprema() {
		return sprema;
	}
	public void setSprema(StručnaSprema sprema) {
		this.sprema = sprema;
	}

	public List<GrupaAnaliza> getGrupeAnaliza() {
		return grupeAnaliza;
	}
	
	public void setGrupeAnaliza(List<GrupaAnaliza> grupeAnaliza) {
		this.grupeAnaliza = grupeAnaliza;
	}
	public boolean isValidan() {
		return validan;
	}

	public void setValidan(boolean validan) {
		this.validan = validan;
	}
	
	
	public Laborant() {
	}
	
	public Laborant(int id) {
		super(id);
	}
	
	public Laborant(int id, String ime, String prezime, String username, String password, StručnaSprema sprema, boolean validan, List<GrupaAnaliza> grupeAnaliza) {
		super(id, ime, prezime, username, password);
		this.sprema = sprema;
		this.validan = validan;
		this.grupeAnaliza = grupeAnaliza;
	}

	
	@Override
	public String toString() {
		return "Laborant [sprema=" + sprema + ", grupeAnaliza=" + grupeAnaliza + ", getIme()=" + getIme()
				+ ", getPrezime()=" + getPrezime() + ", getUsername()=" + getUsername() + ", getPassword()="
				+ getPassword() + ", getId()=" + getId() + "validan:" + validan + "]";
	}

	@Override
	public String toFileEntity() {
		String grupeAnaliza = getGrupeAnalizaKaoString();
		return String.format("%-2d| %-2s| %-12s| %-14s| %-12s| %-15s| %-2d| %-6s| ", this.getId(), "L",
				this.getIme(), this.getPrezime(), this.getUsername(), this.getPassword(),
				this.sprema.getId(), this.validan) + grupeAnaliza;
	}
	
	private String getGrupeAnalizaKaoString() {
		String retVal = "";
		for (GrupaAnaliza g: this.grupeAnaliza) {
			if (this.grupeAnaliza.indexOf(g) != (this.grupeAnaliza.size() - 1)) {
				retVal += String.valueOf(g.getId()) + ",";
			} else {
				retVal += String.valueOf(g.getId());
			}
		}
		return retVal;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Laborant other = (Laborant) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}
	
	
}
