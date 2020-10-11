package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.GrupaAnaliza;
import entity.StručnaSprema;
import entity.korisnici.Laborant;
import repositories.RepositoryFactory;
import test.mocking.FakeLaborantRepository;

public class LaborantRepositoryTest {
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static FakeLaborantRepository fakeLaborantRepository;
	static StručnaSprema sprema;
	static List<GrupaAnaliza>grupeAnaliza = new ArrayList<GrupaAnaliza>();
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		
		fakeLaborantRepository = new FakeLaborantRepository("...", rp.getGrupeAnalizaRepo(), rp.getStručnaSpremaRepo());
		
		grupeAnaliza.add((GrupaAnaliza) rp.getGrupeAnalizaRepo().getEntityByIdMap(1));
		grupeAnaliza.add((GrupaAnaliza) rp.getGrupeAnalizaRepo().getEntityByIdMap(2));
		grupeAnaliza.add((GrupaAnaliza) rp.getGrupeAnalizaRepo().getEntityByIdMap(3));
		
		sprema = (StručnaSprema) rp.getStručnaSpremaRepo().getEntityByIdList(1);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		Laborant expected = new Laborant(1, "ime", "prezime", "username", "lozinka", sprema, true, grupeAnaliza);
		
		String tokeni[] = {"1", "L", "ime", "prezime", "username", "lozinka", "1", "true", "1,2,3"};
		
		Laborant actual = (Laborant) fakeLaborantRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
}
