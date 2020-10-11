package repositories.korisnici;

import java.util.ArrayList;

import entity.Entity;
import entity.GrupaAnaliza;
import entity.StručnaSprema;
import entity.korisnici.Laborant;
import repositories.GenericRepository;
import repositories.GrupeAnalizaRepository;
import repositories.StručnaSpremaRepository;

public class LaborantRepository extends GenericRepository<Laborant>{

	private GrupeAnalizaRepository grupeAnalizaRepo;
	private StručnaSpremaRepository stručnaSpremaRepo;
	
	public LaborantRepository(String path, GrupeAnalizaRepository grupeAnalizaRepo, StručnaSpremaRepository stručnaSpremaRepo) {
		super(path);
		this.grupeAnalizaRepo = grupeAnalizaRepo;
		this.stručnaSpremaRepo = stručnaSpremaRepo;
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		String[] tokeni = tokens[8].split(",");
		ArrayList<GrupaAnaliza> grupeAnaliza = new ArrayList<GrupaAnaliza>();
		StručnaSprema sprema = (StručnaSprema) this.stručnaSpremaRepo.getEntityByIdList(Integer.parseInt(tokens[6]));
		for(String s: tokeni) {
			GrupaAnaliza gp = (GrupaAnaliza) this.grupeAnalizaRepo.getEntityByIdMap(Integer.parseInt(s.trim()));
			grupeAnaliza.add(gp);
		}
		Laborant l = new Laborant(Integer.parseInt(tokens[0]), tokens[2], tokens[3], tokens[4], tokens[5], sprema, Boolean.parseBoolean(tokens[7]), grupeAnaliza);
		this.getEntitetiList().add(l);
		return l;
	}
	
}
