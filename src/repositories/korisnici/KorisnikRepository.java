package repositories.korisnici;

import entity.Entity;
import entity.korisnici.Korisnik;
import repositories.GenericRepository;

public class KorisnikRepository extends GenericRepository<Korisnik>{

	PacijentRepository pacijentRepo;
	AdminRepository adminRepo;
	MedicinskiTehničarRepository medicinskiTehničarRepo;
	LaborantRepository laborantRepo;
	
	
	public KorisnikRepository(String filePath, AdminRepository adminRepo, PacijentRepository pacijentRepo, LaborantRepository laborantRepo, MedicinskiTehničarRepository medicinskiTehničarRepo) {
		super(filePath);
		this.pacijentRepo = pacijentRepo;
		this.adminRepo = adminRepo;
		this.laborantRepo = laborantRepo;
		this.medicinskiTehničarRepo = medicinskiTehničarRepo;
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		Entity e = null;
		switch(tokens[1]) {
		case "A":
			e = this.adminRepo.createEntityAndAddToCollection(tokens);
			break;
		case "M":
			e = this.medicinskiTehničarRepo.createEntityAndAddToCollection(tokens);
			break;
		case "L":
			e = this.laborantRepo.createEntityAndAddToCollection(tokens);
			break;
		case "P":
			e = this.pacijentRepo.createEntityAndAddToCollection(tokens);
			break;
		}
		this.getEntitetiList().add((Korisnik) e);
		return e;
	}
	
	
}
