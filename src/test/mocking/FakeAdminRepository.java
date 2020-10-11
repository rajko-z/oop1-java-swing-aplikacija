package test.mocking;

import entity.Entity;
import entity.korisnici.Admin;
import repositories.GenericRepository;

public class FakeAdminRepository extends GenericRepository<Admin>{
	
	public FakeAdminRepository(String path) {
		super(path);
	}
	@Override
	public Entity createEntityAndAddToCollection(String[] tokens) {
		Admin a = new Admin(Integer.parseInt(tokens[0]), tokens[2], tokens[3], tokens[4], tokens[5]);
		this.getEntitetiList().add(a);
		return a;
	}
}
