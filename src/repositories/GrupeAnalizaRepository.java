package repositories;

import entity.Entity;
import entity.GrupaAnaliza;

public class GrupeAnalizaRepository extends GenericRepository<GrupaAnaliza> {

	public GrupeAnalizaRepository(String path) {
		super(path);
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		GrupaAnaliza grupa = new GrupaAnaliza(id, tokens[1], Boolean.parseBoolean(tokens[2]));
		this.getEntitetiMap().put(id, grupa);
		this.getEntitetiList().add(grupa);
		return grupa;
	}

	
}
