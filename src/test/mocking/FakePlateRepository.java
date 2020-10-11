package test.mocking;

import java.time.Month;
import java.time.Year;

import entity.Entity;
import entity.Plata;
import entity.korisnici.Korisnik;
import repositories.GenericRepository;
import repositories.korisnici.KorisnikRepository;

public class FakePlateRepository extends GenericRepository<Plata>{
private KorisnikRepository korisnikRepo;
	
	public FakePlateRepository(String path, KorisnikRepository korisnikRepo) {
		super(path);
		this.korisnikRepo = korisnikRepo;
	}
	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		Korisnik k = (Korisnik) korisnikRepo.getEntityByIdList(Integer.parseInt(tokens[3]));
		Plata plata = new Plata(Integer.parseInt(tokens[0]), Year.parse(tokens[1]), Month.valueOf(tokens[2]) , k, Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]));
		this.getEntitetiList().add(plata);
		return plata;
	}
}
