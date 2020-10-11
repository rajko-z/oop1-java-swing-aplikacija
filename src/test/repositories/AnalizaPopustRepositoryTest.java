package test.repositories;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.AnalizaPopust;
import entity.PosebnaAnaliza;
import repositories.RepositoryFactory;
import test.mocking.FakeAnalizaPopustRepository;

public class AnalizaPopustRepositoryTest {
	
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static PosebnaAnaliza analiza;
	static FakeAnalizaPopustRepository fakeAnalizaRepo;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		analiza = (PosebnaAnaliza) rp.getPosebnaAnalizaRepo().getEntityByIdMap(1);
		fakeAnalizaRepo = new FakeAnalizaPopustRepository("string", rp.getPosebnaAnalizaRepo());
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		AnalizaPopust expected = new AnalizaPopust(analiza, 5);
		
		String tokeni[] = {"1", "5"};
		
		AnalizaPopust actual = (AnalizaPopust) fakeAnalizaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
}
