package repositories;

import entity.Entity;
import entity.StanjeZahteva;

public class StanjeZahtevaRepository extends GenericRepository<StanjeZahteva>{

	public StanjeZahtevaRepository(String path) {
		super(path);
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		StanjeZahteva s = new StanjeZahteva(id, tokens[1]);
		this.getEntitetiList().add(s);
		this.getEntitetiMap().put(id, s);
		return s;
	}
	
	
}
