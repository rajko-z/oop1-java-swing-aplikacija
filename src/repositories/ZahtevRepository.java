package repositories;
import java.time.LocalDate;
import java.util.ArrayList;
import entity.Dostava;
import entity.Entity;
import entity.PosebnaAnaliza;
import entity.StanjeZahteva;
import entity.Zahtev;
import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;
import repositories.korisnici.KorisnikRepository;

public class ZahtevRepository extends GenericRepository<Zahtev> {

	private PosebnaAnalizaRepository posebnaAnalizaRepo;
	private KorisnikRepository korisnikRepo;
	private StanjeZahtevaRepository stanjeZahtevaRepo;
	private DostavaRepository dostavaRepo;
	
	public ZahtevRepository(String path, PosebnaAnalizaRepository posebnaAnalizaRepo,
			KorisnikRepository korisnikRepo, StanjeZahtevaRepository stanjeZahtevaRepo, DostavaRepository dostavaRepo) {
		super(path);
		this.posebnaAnalizaRepo = posebnaAnalizaRepo;
		this.korisnikRepo = korisnikRepo;
		this.stanjeZahtevaRepo = stanjeZahtevaRepo;
		this.dostavaRepo = dostavaRepo;
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		
		int zahtevId = Integer.parseInt(tokens[0]);
		ArrayList<PosebnaAnaliza> analize = parseAnalizeIdsIntoList(tokens[2]);
		Pacijent pacijent = (Pacijent) korisnikRepo.getEntityByIdList(Integer.parseInt(tokens[1]));
		StanjeZahteva stanjeZahteva = (StanjeZahteva) stanjeZahtevaRepo.getEntityByIdMap(Integer.parseInt(tokens[3]));
		MedicinskiTehničar medicinskiTehničar = getMedicinskiTehničarFromToken(tokens[5]);
		Dostava dostava = getDostavaFromToken(tokens[4]);
		
		Zahtev zahtev = new Zahtev(zahtevId, pacijent, analize, stanjeZahteva, dostava, medicinskiTehničar, LocalDate.parse(tokens[6]), Double.parseDouble(tokens[7]));
		this.getEntitetiList().add(zahtev);
		return zahtev;
	}


	private ArrayList<PosebnaAnaliza> parseAnalizeIdsIntoList(String string) {
		ArrayList<PosebnaAnaliza> analize = new ArrayList<PosebnaAnaliza>();
		String tokeni[] = string.split(",");
		for(String s: tokeni) {
			PosebnaAnaliza pa = (PosebnaAnaliza) this.posebnaAnalizaRepo.getEntityByIdMap(Integer.parseInt(s));
			analize.add(pa);
		}
		return analize;
	}
	
	private MedicinskiTehničar getMedicinskiTehničarFromToken(String token) {
		MedicinskiTehničar medicinskiTehničar = null;
		if(token.equals("0")) {
			medicinskiTehničar = new MedicinskiTehničar();
		}
		else {
			medicinskiTehničar = (MedicinskiTehničar) korisnikRepo.getEntityByIdList(Integer.parseInt(token));
		}
		return medicinskiTehničar;
	}
	
	private Dostava getDostavaFromToken(String token) {
		Dostava dostava = null;
		if(token.equals("0")) {
			dostava = new Dostava();
		}
		else {
			dostava = (Dostava) dostavaRepo.getEntityByIdList(Integer.parseInt(token));
		}
		return dostava;
	}
	

}
