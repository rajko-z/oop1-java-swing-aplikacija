package test.mocking;

import entity.Entity;
import entity.StanjeZahteva;
import repositories.GenericRepository;

public class FakeStanjeZahtevaRepository extends GenericRepository<StanjeZahteva> {
	public FakeStanjeZahtevaRepository(String path) {
		super(path);
	}

	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		StanjeZahteva s = new StanjeZahteva(id, tokens[1]);
		this.getEntitetiList().add(s);
		this.getEntitetiMap().put(id, s);
		return s;
	}
}
