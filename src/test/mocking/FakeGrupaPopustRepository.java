package test.mocking;

import entity.Entity;
import entity.GrupaAnaliza;
import entity.GrupaPopust;
import repositories.GenericRepository;
import repositories.GrupeAnalizaRepository;

public class FakeGrupaPopustRepository extends GenericRepository<GrupaPopust> {
	private GrupeAnalizaRepository grupeAnalizaRepo;

	public FakeGrupaPopustRepository(String path, GrupeAnalizaRepository grupeAnalizaRepo) {
		super(path);
		this.grupeAnalizaRepo = grupeAnalizaRepo;
	}

	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		int idGrupe = Integer.parseInt(tokens[0]);
		GrupaAnaliza grupa = (GrupaAnaliza) this.grupeAnalizaRepo.getEntityByIdMap(idGrupe);
		GrupaPopust grupaPopust = new GrupaPopust(grupa, Integer.parseInt(tokens[1]));
		this.getEntitetiMap().put(idGrupe, grupaPopust);
		return grupaPopust;
	}
}
