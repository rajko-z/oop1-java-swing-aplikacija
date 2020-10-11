package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import repositories.RepositoryFactory;
import test.mocking.FakePosebnaAnalizaRepo;

public class PosebnaAnalizaRepositoryTest {
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static GrupaAnaliza grupa;
	static FakePosebnaAnalizaRepo fakePosebnaAnalizaRepo;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		grupa = (GrupaAnaliza) rp.getGrupeAnalizaRepo().getEntityByIdMap(1);
		fakePosebnaAnalizaRepo = new FakePosebnaAnalizaRepo("...", rp.getGrupeAnalizaRepo());
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		PosebnaAnaliza expected = new PosebnaAnaliza(1, "leukociti", grupa, 500.0, 1.0, 5.0, "g/L", true);
		
		String tokeni[] = {"1", "leukociti", "1", "500.0", "1.0", "5.0", "g/L", "true"};
		
		PosebnaAnaliza actual = (PosebnaAnaliza)fakePosebnaAnalizaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
}
