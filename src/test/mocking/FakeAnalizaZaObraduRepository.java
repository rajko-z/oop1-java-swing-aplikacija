package test.mocking;

import entity.AnalizaZaObradu;
import entity.Entity;
import entity.PosebnaAnaliza;
import entity.korisnici.Laborant;
import repositories.GenericRepository;
import repositories.PosebnaAnalizaRepository;
import repositories.korisnici.LaborantRepository;

public class FakeAnalizaZaObraduRepository extends GenericRepository<AnalizaZaObradu> {
	private PosebnaAnalizaRepository analizaRepo;
	private LaborantRepository laborantRepo;
	
	public FakeAnalizaZaObraduRepository(String path, PosebnaAnalizaRepository analizaRepo, LaborantRepository laborantRepo) {
		super(path);
		this.analizaRepo = analizaRepo;
		this.laborantRepo = laborantRepo;
	}

	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		AnalizaZaObradu analizaZaObradu = null;
		PosebnaAnaliza analiza = (PosebnaAnaliza) this.analizaRepo.getEntityByIdMap(Integer.parseInt(tokens[1]));
		Laborant laborant = getLaborantFromToken(tokens[4]);
		int id = Integer.parseInt(tokens[0]);
		if (tokens[3].equals("true")) {
			analizaZaObradu = new AnalizaZaObradu(id, analiza, Double.parseDouble(tokens[2]), true, laborant );
		}
		else {
			analizaZaObradu = new AnalizaZaObradu(id, analiza, false, laborant );
		}
		this.getEntitetiList().add(analizaZaObradu);
		this.getEntitetiMap().put(id, analizaZaObradu);
		return analizaZaObradu;
	}
	
	public Laborant getLaborantFromToken(String token) {
		Laborant laborant = null;
		if(token.equals("0")) {
			laborant = new Laborant();
		}
		else {
			laborant = (Laborant) laborantRepo.getEntityByIdList(Integer.parseInt(token));
		}
		return laborant;
	}
}
