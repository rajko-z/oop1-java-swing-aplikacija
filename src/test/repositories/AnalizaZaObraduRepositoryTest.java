package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.AnalizaZaObradu;
import entity.PosebnaAnaliza;
import entity.korisnici.Laborant;
import repositories.RepositoryFactory;
import test.mocking.FakeAnalizaZaObraduRepository;

public class AnalizaZaObraduRepositoryTest {

	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static PosebnaAnaliza analiza;
	static Laborant laborant;
	static FakeAnalizaZaObraduRepository fakeAnalizaZaObraduRepo;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		analiza = (PosebnaAnaliza) rp.getPosebnaAnalizaRepo().getEntityByIdMap(1);
		laborant = (Laborant) rp.getLaborantRepo().getEntityByIdList(15);
		fakeAnalizaZaObraduRepo = new FakeAnalizaZaObraduRepository("string", rp.getPosebnaAnalizaRepo(), rp.getLaborantRepo()); 
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_NezavrsenaAnalizaZaObradu() {
		AnalizaZaObradu expected = new AnalizaZaObradu(1, analiza, false, new Laborant());
		
		String tokeni[] = {"1" ,"1", "0.0", "false", "0"};
		
		AnalizaZaObradu actual = (AnalizaZaObradu) fakeAnalizaZaObraduRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_ZavrsenaAnalizaZaObradu() {
		AnalizaZaObradu expected = new AnalizaZaObradu(1, analiza, 2.5, true, laborant);
		
		String tokeni[] = {"1" ,"1", "2.5", "true", "15"};
		
		AnalizaZaObradu actual = (AnalizaZaObradu) fakeAnalizaZaObraduRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}


}
