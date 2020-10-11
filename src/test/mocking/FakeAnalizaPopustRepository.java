package test.mocking;

import entity.AnalizaPopust;
import entity.Entity;
import entity.PosebnaAnaliza;
import repositories.GenericRepository;
import repositories.PosebnaAnalizaRepository;

public class FakeAnalizaPopustRepository extends GenericRepository<AnalizaPopust>{

	
	private PosebnaAnalizaRepository posebnaAnalizaRepo;
	
	
	public FakeAnalizaPopustRepository(String path, PosebnaAnalizaRepository posebnaAnalizaRepository) {
		super(path);
		this.posebnaAnalizaRepo = posebnaAnalizaRepository;
	}

	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		int idAnalize = Integer.parseInt(tokens[0]);
		PosebnaAnaliza analiza = (PosebnaAnaliza) this.posebnaAnalizaRepo.getEntityByIdMap(idAnalize);
		AnalizaPopust analizaPopust = new AnalizaPopust(analiza, Integer.parseInt(tokens[1]));
		this.getEntitetiMap().put(idAnalize, analizaPopust);
		return analizaPopust;
	}

}
