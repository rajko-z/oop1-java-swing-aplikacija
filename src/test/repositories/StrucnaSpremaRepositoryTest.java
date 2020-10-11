package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import entity.StručnaSprema;
import test.mocking.FakeStručnaSpremaRepository;

public class StrucnaSpremaRepositoryTest {
	
	static FakeStručnaSpremaRepository fakeStručnaSpremaRepo;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		fakeStručnaSpremaRepo = new FakeStručnaSpremaRepository("...");
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		StručnaSprema expected = new StručnaSprema(1, 3.3, "opis spreme");
		
		String tokeni[] = {"1", "3.3", "opis spreme"};
		
		StručnaSprema actual = (StručnaSprema) fakeStručnaSpremaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
}
