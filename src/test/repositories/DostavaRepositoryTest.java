package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.BeforeClass;
import org.junit.Test;

import entity.Dostava;
import test.mocking.FakeDostavaRepository;

public class DostavaRepositoryTest {

	static FakeDostavaRepository fakeDostavaRepo;
	static LocalDate datumDostave;
	static LocalTime vremeDostave;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		fakeDostavaRepo = new FakeDostavaRepository("...");
		datumDostave = LocalDate.parse("2020-09-28");
		vremeDostave = LocalTime.parse("21:00");
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Bez_kucne_dostave() {
		Dostava expected = new Dostava(1, false, datumDostave);
		
		String tokeni[] = {"1", "false", "2020-09-28", "null"};
		
		Dostava actual = (Dostava) fakeDostavaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Sa_kucnom_dostavom() {
		Dostava expected = new Dostava(1, true, datumDostave);
		
		String tokeni[] = {"1", "true", "2020-09-28" ,"null"};
		
		Dostava actual = (Dostava) fakeDostavaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Sa_kucnom_dostavom_i_vremenom() {
		Dostava expected = new Dostava(1, true, datumDostave, vremeDostave);
		
		String tokeni[] = {"1", "true", "2020-09-28", "21:00"};
		
		Dostava actual = (Dostava) fakeDostavaRepo.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}


}
