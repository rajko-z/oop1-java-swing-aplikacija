package repositories.korisnici;
import entity.Entity;
import entity.StručnaSprema;
import entity.korisnici.MedicinskiTehničar;
import repositories.GenericRepository;
import repositories.StručnaSpremaRepository;

public class MedicinskiTehničarRepository extends GenericRepository<MedicinskiTehničar> {
	
	private StručnaSpremaRepository spremaRepo;
	
	public MedicinskiTehničarRepository(String path, StručnaSpremaRepository spremaRepo) {
		super(path);
		this.spremaRepo = spremaRepo;
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		StručnaSprema stručnaSprema = (StručnaSprema) spremaRepo.getEntityByIdList(Integer.parseInt(tokens[6]));
		MedicinskiTehničar m = new MedicinskiTehničar(Integer.parseInt(tokens[0]), tokens[2], tokens[3], tokens[4], tokens[5], stručnaSprema, Boolean.parseBoolean(tokens[7]));
		this.getEntitetiList().add(m);
		return m;
	}
}
