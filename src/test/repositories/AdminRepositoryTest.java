package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.korisnici.Admin;
import repositories.RepositoryFactory;
import test.mocking.FakeAdminRepository;

public class AdminRepositoryTest {
	
	static FakeAdminRepository fakeAdminRepo;
	static Admin admin;
	static int id;
	@BeforeClass
	public static void setUpBeforeClass() {
		fakeAdminRepo = new FakeAdminRepository("...");
		
		id = RepositoryFactory.getInstance().getAdminRepo().generateIdList();
		admin = new Admin(id, "Admin", "Admin", "username", "password");
		RepositoryFactory.getInstance().getAdminRepo().getEntitetiList().add(admin);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		
		String tokeni[] = {String.valueOf(id),"A", "Admin", "Admin", "username", "password"};
		Admin actual = (Admin) fakeAdminRepo.createEntityAndAddToCollection(tokeni);
		assertThat(admin).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
}
