package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.StanjeZahteva;
import repositories.RepositoryFactory;
import test.mocking.FakeStanjeZahtevaRepository;

public class StanjeZahtevaRepositoryTest {
	static FakeStanjeZahtevaRepository fakeStanjeZahtevaRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		fakeStanjeZahtevaRepository = new FakeStanjeZahtevaRepository("...");
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		StanjeZahteva expected = new StanjeZahteva(1, "opis");
		
		String tokeni[] = {"1", "opis"};
		
		StanjeZahteva actual = (StanjeZahteva) fakeStanjeZahtevaRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
}
