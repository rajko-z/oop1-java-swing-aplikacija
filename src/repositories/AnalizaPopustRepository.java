package repositories;

import entity.AnalizaPopust;
import entity.Entity;
import entity.PosebnaAnaliza;

public class AnalizaPopustRepository extends GenericRepository<AnalizaPopust> {


	private PosebnaAnalizaRepository posebnaAnalizaRepo;
	
	public AnalizaPopustRepository(String path, PosebnaAnalizaRepository posebnaAnalizaRepo) {
		super(path);
		this.posebnaAnalizaRepo = posebnaAnalizaRepo;
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		int idAnalize = Integer.parseInt(tokens[0]);
		PosebnaAnaliza analiza = (PosebnaAnaliza) this.posebnaAnalizaRepo.getEntityByIdMap(idAnalize);
		AnalizaPopust analizaPopust = new AnalizaPopust(analiza, Integer.parseInt(tokens[1]));
		this.getEntitetiMap().put(idAnalize, analizaPopust);
		return analizaPopust;
	}

}
