package test.mocking;

import java.time.LocalDate;
import java.time.LocalTime;

import entity.Dostava;
import entity.Entity;
import repositories.GenericRepository;

public class FakeDostavaRepository extends GenericRepository<Dostava> {
	
	public FakeDostavaRepository(String path) {
		super(path);
	}

	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		Dostava dostava = null;
		int id = Integer.parseInt(tokens[0]);
		if (tokens[1].equals("false")) {
			dostava = new Dostava(id, false, LocalDate.parse(tokens[2]));
		}
		else if(tokens[1].equals("true")) {
			if (tokens[3].equals("null")) {
				dostava = new Dostava(id, true, LocalDate.parse(tokens[2]));
			}
			else {
				dostava = new Dostava(id, true, LocalDate.parse(tokens[2]), LocalTime.parse(tokens[3]));
			}
		}
		this.getEntitetiList().add(dostava);
		return dostava;
	}
}
