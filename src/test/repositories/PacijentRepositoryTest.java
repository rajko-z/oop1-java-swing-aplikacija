package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.korisnici.Pacijent;
import repositories.RepositoryFactory;
import test.mocking.FakePacijentRepository;

public class PacijentRepositoryTest {
	
	static FakePacijentRepository fakePacijentRepo;
	static LocalDate datum;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		datum = LocalDate.parse("2020-08-02");
		fakePacijentRepo = new FakePacijentRepository("...");
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Registrovani() {
		Pacijent expected = new Pacijent(1, "ime", "prezime", "username", "lozinka", "lbo", "pol", datum, "1234", "adresa");
		
		String tokeni[] = {"1", "P", "ime", "prezime", "username", "lozinka", "lbo", "pol", "2020-08-02", "1234", "adresa"};
		
		Pacijent actual = (Pacijent) fakePacijentRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Neregistrovani() {
		Pacijent expected = new Pacijent(1, "ime", "prezime", " "," ", "lbo", "pol", datum, "1234", "adresa");
		
		String tokeni[] = {"1", "P", "ime", "prezime", " ", " ", "lbo", "pol", "2020-08-02", "1234", "adresa"};
		
		Pacijent actual = (Pacijent) fakePacijentRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void pacijentNijeRegistrovanTest_Pozitivan_slucaj() {
		assertTrue(fakePacijentRepo.pacijentNijeRegistrovan(null));
	}
	
	@Test
	public void pacijentNijeRegistrovanTest_Negativan_slucaj() {
		assertFalse(fakePacijentRepo.pacijentNijeRegistrovan("username"));
	}
	
	
}
