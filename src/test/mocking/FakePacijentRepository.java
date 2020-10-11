package test.mocking;

import java.time.LocalDate;

import entity.Entity;
import entity.korisnici.Pacijent;
import repositories.GenericRepository;

public class FakePacijentRepository extends GenericRepository<Pacijent> {
	public FakePacijentRepository(String path) {
		super(path);
	}

	public boolean pacijentNijeRegistrovan(String s) {
		if (s == null) {
			return true; 
		}
		return false;
	}
	
	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
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
