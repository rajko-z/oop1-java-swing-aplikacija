package test.mocking;

import entity.Entity;
import entity.StručnaSprema;
import repositories.GenericRepository;

public class FakeStručnaSpremaRepository extends GenericRepository<StručnaSprema> {
	
	public FakeStručnaSpremaRepository(String path) {
		super(path);
	}
	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		StručnaSprema sprema = new StručnaSprema(id, Double.parseDouble(tokens[1]), tokens[2]);
		this.getEntitetiList().add(sprema);
		return sprema;
	}
}
