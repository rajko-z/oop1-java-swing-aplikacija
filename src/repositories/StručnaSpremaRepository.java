package repositories;

import entity.Entity;
import entity.StručnaSprema;

public class StručnaSpremaRepository extends GenericRepository<StručnaSprema> {

	public StručnaSpremaRepository(String path) {
		super(path);
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		StručnaSprema sprema = new StručnaSprema(id, Double.parseDouble(tokens[1]), tokens[2]);
		this.getEntitetiList().add(sprema);
		return sprema;
	}
	
}
