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

import entity.Dostava;
import entity.PosebnaAnaliza;
import entity.StanjeZahteva;
import entity.Zahtev;
import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;
import repositories.RepositoryFactory;
import test.mocking.FakeZahtevRepository;

public class ZahtevRepositoryTest {
	static RepositoryFactory rp = RepositoryFactory.getInstance();
	static Pacijent pacijent;
	static Dostava dostava;
	static List<PosebnaAnaliza>analize = new ArrayList<PosebnaAnaliza>();
	static MedicinskiTehničar medicinskiTehničar;
	static FakeZahtevRepository fakeZahtevRepository;
	static StanjeZahteva stanjeZahteva;
	static LocalDate datum;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rp.loadData();
		
		fakeZahtevRepository = new FakeZahtevRepository("...", rp.getPosebnaAnalizaRepo(), rp.getKorisnikRepo(), rp.getStanjeZahtevaRepo(), rp.getDostavaRepo());
		
		datum = LocalDate.parse("2020-08-07");
		pacijent = (Pacijent) rp.getPacijentRepo().getEntityByIdList(30);
		medicinskiTehničar = (MedicinskiTehničar) rp.getMedicinskiTehničarRepo().getEntityByIdList(7); 
		dostava = (Dostava) rp.getDostavaRepo().getEntityByIdList(1);
		stanjeZahteva = (StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdList(1);
		
		analize.add((PosebnaAnaliza) rp.getPosebnaAnalizaRepo().getEntityByIdMap(1));
		analize.add((PosebnaAnaliza) rp.getPosebnaAnalizaRepo().getEntityByIdMap(2));
		analize.add((PosebnaAnaliza) rp.getPosebnaAnalizaRepo().getEntityByIdMap(3));
		
	}
	
	@Test
	public void createEntityAndAddToCollectionTest() {
		Zahtev expected = new Zahtev(1, pacijent, analize, stanjeZahteva, dostava, medicinskiTehničar, datum, 3000.0);
		
		String tokeni[] = {"1", "30", "1,2,3", "1" ,"1", "7", "2020-08-07", "3000.0"};
		
		Zahtev actual = (Zahtev) fakeZahtevRepository.createEntityAndAddToCollection(tokeni);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void parseAnalizeIdsIntoListTest() {
		ArrayList<PosebnaAnaliza>vrednosti = fakeZahtevRepository.parseAnalizeIdsIntoList("1,2,3");
		assertArrayEquals(analize.toArray(), vrednosti.toArray());
	}
	
	@Test
	public void getMedicinskiTehničarFromTokenTest_Kada_nije_odredjen() {
		MedicinskiTehničar expected = new MedicinskiTehničar();
		MedicinskiTehničar actual = fakeZahtevRepository.getMedicinskiTehničarFromToken("0");
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getMedicinskiTehničarFromTokenTest_Kada_je_odredjen() {
		MedicinskiTehničar expected = medicinskiTehničar;
		MedicinskiTehničar actual = fakeZahtevRepository.getMedicinskiTehničarFromToken("7");
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getDostavaFromToken_Kada_nije_odredjena() {
		Dostava expected = new Dostava();
		Dostava actual = fakeZahtevRepository.getDostavaFromToken("0");
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getDostavaFromToken_Kada_je_odredjena() {
		Dostava expected = dostava;
		Dostava actual = fakeZahtevRepository.getDostavaFromToken("1");
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@AfterClass
	public static void afterClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
	}
	
	
}
