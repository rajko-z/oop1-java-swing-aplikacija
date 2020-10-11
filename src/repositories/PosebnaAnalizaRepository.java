package repositories;

import entity.Entity;
import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;

public class PosebnaAnalizaRepository extends GenericRepository<PosebnaAnaliza> {
	
	GrupeAnalizaRepository grupeAnalizaRepo;
	
	public PosebnaAnalizaRepository(String path, GrupeAnalizaRepository grupeAnalizaRepo) {
		super(path);
		this.grupeAnalizaRepo = grupeAnalizaRepo;
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		GrupaAnaliza grupaAnaliza = (GrupaAnaliza) this.grupeAnalizaRepo.getEntityByIdMap(Integer.parseInt(tokens[2])); 
		PosebnaAnaliza analiza = new PosebnaAnaliza(Integer.parseInt(tokens[0]),
				                                    tokens[1], grupaAnaliza,
				                                    Double.parseDouble(tokens[3]),
				                                    Double.parseDouble(tokens[4]),
				                                    Double.parseDouble(tokens[5]),
				                                    tokens[6], Boolean.parseBoolean(tokens[7]));
		this.getEntitetiMap().put(Integer.parseInt(tokens[0]), analiza);
		this.getEntitetiList().add(analiza);
		return analiza;
	}
    
	
}
