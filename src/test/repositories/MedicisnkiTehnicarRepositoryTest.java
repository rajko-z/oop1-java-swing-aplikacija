package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.AnalizaPopust;
import entity.PosebnaAnaliza;
import entity.StručnaSprema;
import entity.korisnici.MedicinskiTehničar;
import repositories.RepositoryFactory;
import test.mocking.FakeAnalizaPopustRepository;
import test.mocking.FakeMedicinskiTehnicarRepository;

public class MedicisnkiTehnicarRepositoryTest {
	
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static StručnaSprema sprema;
	static FakeMedicinskiTehnicarRepository fakeMedicinskiTehnicarRepo;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		
		fakeMedicinskiTehnicarRepo = new FakeMedicinskiTehnicarRepository("...", rp.getStručnaSpremaRepo());
		sprema = (StručnaSprema) rp.getStručnaSpremaRepo().getEntityByIdList(1);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		MedicinskiTehničar expected = new MedicinskiTehničar(1, "ime", "prezime", "username", "password", sprema, true);		
		String tokeni[] = {"1", "M", "ime", "prezime", "username", "password", "1", "true"};
		
		MedicinskiTehničar actual = (MedicinskiTehničar) fakeMedicinskiTehnicarRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
}
