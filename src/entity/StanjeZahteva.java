package entity;

public class StanjeZahteva extends Entity {
	
	private String opis;
	
	public StanjeZahteva(int id, String opis) {
		super(id);
		this.opis = opis;
	}
	
	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-2s| %s", this.getId(), this.opis);
	}

	@Override
	public String toString() {
		return opis.toUpperCase();
	}
	
	
}
