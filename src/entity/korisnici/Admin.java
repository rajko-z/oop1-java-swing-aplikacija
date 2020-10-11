package entity.korisnici;

public class Admin extends Korisnik {
	public Admin() {
	}

	public Admin(int id, String ime, String prezime, String username, String password) {
		super(id, ime, prezime, username, password);
	}


	@Override
	public String toString() {
		return "Admin [getIme()=" + getIme() + ", getPrezime()=" + getPrezime() + ", getUsername()=" + getUsername()
				+ ", getPassword()=" + getPassword() + ", getId()=" + getId() + "]";
	}

	@Override
	public String toFileEntity() {
		return String.format("%-2d| %-2s| %-12s| %-14s| %-12s| %-15s", this.getId(), "A",
				this.getIme(), this.getPrezime(), this.getUsername(), this.getPassword());
	}

	

}
