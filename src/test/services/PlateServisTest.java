package test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.GrupaAnaliza;
import entity.Plata;
import entity.StručnaSprema;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import repositories.RepositoryFactory;
import services.PlataServis;

public class PlateServisTest {
	
	
	static PlataServis plataServis;
	
	static Laborant laborant1;
	static Laborant laborant2;
	static MedicinskiTehničar tehnicar1;
	static MedicinskiTehničar tehnicar2;
	static Plata plata1;
	static Plata plata2;
	static Plata plata3;
	static Plata plata4;
	static Plata plata5;
	static Plata plata6;
	static Plata plata7;
	static Plata plata8;
	
	static List<Plata>plate;
	static List<Korisnik>zaposleni;
	
	@BeforeClass
	public static void beforeClassSetUp() {
		plate = new ArrayList<Plata>();
		zaposleni = new ArrayList<Korisnik>();
	}
	
	
	@Before
	public void setUpBeforeTest() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// reset singleton
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
		
		// always run with fresh instance
		plataServis = new PlataServis();
		
		GrupaAnaliza grupa1 = new GrupaAnaliza(1, "grupa1", true);
		GrupaAnaliza grupa2 = new GrupaAnaliza(2, "grupa2", true);
		GrupaAnaliza grupa3 = new GrupaAnaliza(3, "grupa3", false);
		GrupaAnaliza grupa4 = new GrupaAnaliza(4, "grupa4", true);
		
		StručnaSprema sprema = new StručnaSprema(1, 2.2, "opis");
		
		laborant1 = new Laborant(1, "laborant1", "prezime1", "username", "lozinka",
				sprema, true, Arrays.asList(grupa1, grupa2));
		laborant2 = new Laborant(2, "laborant2", "prezime1", "username", "lozinka",
				sprema, true, Arrays.asList(grupa3, grupa4));
		tehnicar1 = new MedicinskiTehničar(3, "tehnicar1", "prezime", "username", "loznika",sprema, true);
		tehnicar1 = new MedicinskiTehničar(4, "tehnicar2", "prezime", "username", "loznika",sprema, true);
	
		zaposleni.addAll(Arrays.asList(laborant1, laborant2, tehnicar1, tehnicar2));
		
		Year godina = Year.parse("2020");
		
		plata1 = new Plata(1, godina, Month.JUNE, laborant1, 30000.0, 10000.0);
		plata2 = new Plata(2, godina, Month.JULY, laborant1, 30000.0, 10000.0);
		plata3 = new Plata(3, godina, Month.AUGUST, laborant1, 30000.0, 10000.0);
		plata4 = new Plata(4, godina, Month.SEPTEMBER, laborant1, 30000.0, 10000.0);
		plata5 = new Plata(5, godina, Month.JUNE, tehnicar1, 30000.0, 10000.0);
		plata6 = new Plata(6, godina, Month.JULY, tehnicar1, 30000.0, 10000.0);
		plata7 = new Plata(7, godina, Month.AUGUST, tehnicar1, 30000.0, 10000.0);
		plata8 = new Plata(8, godina, Month.SEPTEMBER, tehnicar1, 30000.0, 10000.0);
		
		plate.addAll(Arrays.asList(plata1, plata2, plata3, plata4, plata5, plata6, plata7, plata8));
	}
	
	@After
	public void cleanAfterTest() {
		plate.clear();
	}
	
	
	@Test
	public void getPlateZaKorisnikaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPlateRepo().getEntitetiList().addAll(plate);
		
		List<Plata>expected = Arrays.asList(plata1, plata2, plata3, plata4);
		List<Plata>actual = plataServis.getPlateZaKorisnika(laborant1);
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}

	
	@Test
	public void removeTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPlateRepo().getEntitetiList().addAll(plate);
		
		assertTrue(rp.getPlateRepo().getEntitetiList().contains(plata1));
		plataServis.delete(plata1);
		assertFalse(rp.getPlateRepo().getEntitetiList().contains(plata1));
	}
	
	
	@Test
	public void getPlataByIdTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPlateRepo().getEntitetiList().addAll(plate);
		
		Plata expected = plata1;
		Plata actual = plataServis.getPlataById(1);
		assertThat(expected).isEqualToComparingFieldByField(actual);
		
		actual = plataServis.getPlataById(10000000);
		assertTrue(actual == null);
	}
	
	@Test
	public void promeniBonusPlateTest() {
		plata1 = new Plata(1, Year.parse("2020"), Month.MARCH, laborant1, 30000, 5000);
		plataServis.promeniBonusPlate(plata1, 10000.0);
		assertTrue(plata1.getBonus() == 10000.0);
		assertTrue(plata1.getUkupnaPlata() == 40000.0);
	}
	
	@Test
	public void plataZaKorisnikaZaZadatiMesecVecPostojiTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPlateRepo().getEntitetiList().addAll(plate);
		
		assertTrue(plataServis.plataZaKorisnikaZaZadatiMesecVecPostoji(Year.parse("2020"), Month.AUGUST, laborant1));
		assertFalse(plataServis.plataZaKorisnikaZaZadatiMesecVecPostoji(Year.parse("2019"), Month.AUGUST, laborant1));
	}
	
	@Test
	public void kreirajNovuPlatuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPlateRepo().getEntitetiList().addAll(plate);
		
		Plata expected = new Plata(9, Year.parse("2020"), Month.NOVEMBER, laborant1, 40000.0, 5000.0);
		Plata actual = plataServis.kreirajNovuPlatu(Year.parse("2020") ,Month.NOVEMBER, 5000.0, 40000.0, laborant1);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getPlatuZaposlenogZaOdredjenDan() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPlateRepo().getEntitetiList().addAll(plate);
		
		Plata plata = new Plata(9, Year.parse("2020"), Month.NOVEMBER, laborant1, 40000.0, 5000.0);
		rp.getPlateRepo().getEntitetiList().add(plata);
		double expected = (40000.0 + 5000.0) / 30;
		double actual = plataServis.getPlatuZaposlenogZaOdredjenDan(LocalDate.parse("2020-11-05"), laborant1);
		assertTrue(expected == actual);
		
		actual = plataServis.getPlatuZaposlenogZaOdredjenDan(LocalDate.parse("2021-11-05"), laborant1);
		assertTrue(actual == 0);
	}
	
	@Test
	public void getProcenatIznosaOdUkupnogTest() {
		double ukupanIznos = 10000;
		double iznos = 2000;
		double expected = 20;
		double actual = plataServis.getProcenatIznosaOdUkupnog(ukupanIznos, iznos);
		assertTrue(expected == actual);
		
		actual = plataServis.getProcenatIznosaOdUkupnog(0.0, 2000);
		assertTrue(actual == 0);
	}
	
}
