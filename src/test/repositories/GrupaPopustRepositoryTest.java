package test.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.GrupaAnaliza;
import entity.GrupaPopust;
import repositories.RepositoryFactory;
import test.mocking.FakeGrupaPopustRepository;

public class GrupaPopustRepositoryTest {

	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static GrupaAnaliza grupa;
	static FakeGrupaPopustRepository fakeGrupaPopustRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		grupa  = (GrupaAnaliza) rp.getGrupeAnalizaRepo().getEntityByIdMap(1);
		fakeGrupaPopustRepository = new FakeGrupaPopustRepository("...", rp.getGrupeAnalizaRepo());
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		GrupaPopust expected = new GrupaPopust(grupa, 10);
		
		String tokeni[] = {"1", "10"};
		
		GrupaPopust actual = (GrupaPopust)fakeGrupaPopustRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}

	
}
