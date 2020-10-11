package repositories;

import entity.Entity;
import entity.GrupaAnaliza;
import entity.GrupaPopust;

public class GrupaPopustRepository extends GenericRepository<GrupaPopust>{

	private GrupeAnalizaRepository grupeAnalizaRepo;
	
	public GrupaPopustRepository(String path, GrupeAnalizaRepository grupeAnalizaRepo) {
		super(path);
		this.grupeAnalizaRepo = grupeAnalizaRepo;
	}
	
	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		int idGrupe = Integer.parseInt(tokens[0]);
		GrupaAnaliza grupa = (GrupaAnaliza) this.grupeAnalizaRepo.getEntityByIdMap(idGrupe);
		GrupaPopust grupaPopust = new GrupaPopust(grupa, Integer.parseInt(tokens[1]));
		this.getEntitetiMap().put(idGrupe, grupaPopust);
		return grupaPopust;
	}
	
	
	

}
