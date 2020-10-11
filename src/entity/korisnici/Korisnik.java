package entity.korisnici;

import entity.Entity;

public abstract class Korisnik extends Entity {
	private String ime;
	private String prezime;
	private String username;
	private String password;
	
	
	public Korisnik() {
	}
	
	public Korisnik(int id) {
		super(id);
	}
	
	public Korisnik(int id, String ime, String prezime) {
		super(id);
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public Korisnik(int id, String ime, String prezime, String username, String password) {
		super(id);
		this.ime = ime;
		this.prezime = prezime;
		this.username = username;
		this.password = password;
	}

	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Korisnik other = (Korisnik) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}
	
	
}
