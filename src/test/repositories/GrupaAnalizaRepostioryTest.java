package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import entity.AnalizaPopust;
import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import repositories.RepositoryFactory;
import test.mocking.FakeAnalizaPopustRepository;
import test.mocking.FakeGrupeAnalizaRepo;

public class GrupaAnalizaRepostioryTest {
	static FakeGrupeAnalizaRepo fakeGrupaAnalizaRepo;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		fakeGrupaAnalizaRepo = new FakeGrupeAnalizaRepo("...");
	}
	@Test
	public void createEntityAndAddToCollectionTest() {
		GrupaAnaliza expected = new GrupaAnaliza(2, "Hematologija", true);
		
		String tokeni[] = {"2", "Hematologija", "true"};
		
		GrupaAnaliza actual = (GrupaAnaliza) fakeGrupaAnalizaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}

}
