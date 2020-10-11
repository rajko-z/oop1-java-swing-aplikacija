package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.time.Month;
import java.time.Year;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Plata;
import entity.korisnici.Korisnik;
import repositories.RepositoryFactory;
import test.mocking.FakePlateRepository;

public class PlateRepositoryTest {
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static Korisnik korisnik;
	static FakePlateRepository fakePlateRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		korisnik = (Korisnik) rp.getKorisnikRepo().getEntityByIdList(15);
		fakePlateRepository = new FakePlateRepository("...", rp.getKorisnikRepo());
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		Plata expected = new Plata(3, Year.parse("2020"), Month.MAY, korisnik, 20000.0, 1000.0);

		String tokeni[] = {"3", "2020", "MAY", "15", "20000.0", "1000.0"};
		
		Plata actual = (Plata) fakePlateRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
}
