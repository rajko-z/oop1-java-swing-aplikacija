package exceptions;

public class KorisnikNijeNadjen extends RuntimeException {
	private static final long serialVersionUID = -2053891077917551795L;
	
	private String opis;
	public KorisnikNijeNadjen(String opis) {
		this.opis = opis;
	}
	public String getOpis() {
		return opis;
	}
	@Override
	public String toString() {
		return "KorisnikNijeNadjen: " + opis;
	}
	
	
	
}
