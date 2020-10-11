package test.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.Zahtev;
import repositories.RepositoryFactory;
import test.mocking.FakeNalazRepository;

public class NalazRepositoryTest {
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static List<AnalizaZaObradu>analizeZaObradu = new ArrayList<AnalizaZaObradu>();
	static Zahtev zahtev;
	static FakeNalazRepository fakeNalazRepository;
	static LocalDate datumObrade;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		fakeNalazRepository = new FakeNalazRepository("...", rp.getZahtevRepo(), rp.getAnalizaZaObraduRepo());
		
		datumObrade = LocalDate.parse("2020-08-16");
		zahtev = (Zahtev) rp.getZahtevRepo().getEntityByIdList(5);
		
		AnalizaZaObradu analizaZaObradu1 = (AnalizaZaObradu) rp.getAnalizaZaObraduRepo().getEntityByIdMap(1);
		AnalizaZaObradu analizaZaObradu2 = (AnalizaZaObradu) rp.getAnalizaZaObraduRepo().getEntityByIdMap(2);
		AnalizaZaObradu analizaZaObradu3 = (AnalizaZaObradu) rp.getAnalizaZaObraduRepo().getEntityByIdMap(3);
		
		analizeZaObradu.add(analizaZaObradu1);
		analizeZaObradu.add(analizaZaObradu2);
		analizeZaObradu.add(analizaZaObradu3);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Neobradjen() {
		Nalaz expected = new Nalaz(1, zahtev, analizeZaObradu);
		
		String tokeni[] = {"1", "5", "1,2,3", "null"};
		
		Nalaz actual = (Nalaz) fakeNalazRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void createEntityAndAddToCollectionTest_Obradjen() {
		Nalaz expected = new Nalaz(1, zahtev, analizeZaObradu, datumObrade);
		
		String tokeni[] = {"1", "5", "1,2,3", "2020-08-16"};
		
		Nalaz actual = (Nalaz) fakeNalazRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void parseAnalizeZaObraduIntoListTest() {
		String parseString = "1,2,3";
		ArrayList<AnalizaZaObradu>dobijeVrednosti = fakeNalazRepository.parseAnalizeZaObraduIntoList(parseString);
		
		assertArrayEquals(analizeZaObradu.toArray(), dobijeVrednosti.toArray());
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
	

}
