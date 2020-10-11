package repositories.korisnici;
import java.time.LocalDate;

import entity.Entity;
import entity.korisnici.Pacijent;
import repositories.GenericRepository;

public class PacijentRepository extends GenericRepository<Pacijent> {
	
	public PacijentRepository(String path) {
		super(path);
	}

	private boolean pacijentNijeRegistrovan(String s) {
		if (s == null) {
			return true; 
		}
		return false;
	}
	
	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		Pacijent p = null;
		if (pacijentNijeRegistrovan(tokens[4])) {
			p = new Pacijent(Integer.parseInt(tokens[0]), tokens[2], tokens[3], tokens[6], tokens[7], LocalDate.parse(tokens[8]), tokens[9], tokens[10]);
		}
		else {
			p = new Pacijent(Integer.parseInt(tokens[0]), tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], LocalDate.parse(tokens[8]), tokens[9], tokens[10]);
		}
		this.getEntitetiList().add(p);
		return p;
	}
	
	
}
