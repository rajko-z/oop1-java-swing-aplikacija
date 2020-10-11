package repositories;

import entity.CenePodesavanja;
import entity.Entity;

public class CenePodesavanjaRepo extends GenericRepository<CenePodesavanja>{
	public CenePodesavanjaRepo(String path) {
		super(path);
	}
	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		CenePodesavanja cenePodesavanja = CenePodesavanja.getInstance();
		cenePodesavanja.setData(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
		this.getEntitetiList().add(cenePodesavanja);
		return cenePodesavanja;
	}
}
