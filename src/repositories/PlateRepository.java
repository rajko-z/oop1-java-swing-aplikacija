package repositories;

import java.time.Month;
import java.time.Year;

import entity.Entity;
import entity.Plata;
import entity.korisnici.Korisnik;
import repositories.korisnici.KorisnikRepository;

public class PlateRepository extends GenericRepository<Plata> {

	private KorisnikRepository korisnikRepo;
	
	public PlateRepository(String path, KorisnikRepository korisnikRepo) {
		super(path);
		this.korisnikRepo = korisnikRepo;
	}
	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		Korisnik k = (Korisnik) korisnikRepo.getEntityByIdList(Integer.parseInt(tokens[3]));
		Plata plata = new Plata(Integer.parseInt(tokens[0]), Year.parse(tokens[1]), Month.valueOf(tokens[2]) , k, Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]));
		this.getEntitetiList().add(plata);
		return plata;
	}
	

}
