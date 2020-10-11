package test.mocking;

import entity.Entity;
import entity.GrupaAnaliza;
import repositories.GenericRepository;

public class FakeGrupeAnalizaRepo extends GenericRepository<GrupaAnaliza>{
	
	public FakeGrupeAnalizaRepo(String path) {
		super(path);
	}

	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		GrupaAnaliza grupa = new GrupaAnaliza(id, tokens[1], Boolean.parseBoolean(tokens[2]));
		this.getEntitetiMap().put(id, grupa);
		this.getEntitetiList().add(grupa);
		return grupa;
	}

}
