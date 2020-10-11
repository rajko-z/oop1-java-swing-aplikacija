package repositories.korisnici;

import entity.Entity;
import entity.korisnici.Admin;
import repositories.GenericRepository;

public class AdminRepository extends GenericRepository<Admin>{
	
	public AdminRepository(String path) {
		super(path);
	}
	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		Admin a = new Admin(Integer.parseInt(tokens[0]), tokens[2], tokens[3], tokens[4], tokens[5]);
		this.getEntitetiList().add(a);
		return a;
	}
	
}
