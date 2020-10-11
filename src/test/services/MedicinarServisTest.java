package test.services;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Dostava;
import entity.StručnaSprema;
import entity.Zahtev;
import entity.korisnici.Korisnik;
import entity.korisnici.MedicinskiTehničar;
import repositories.RepositoryFactory;
import services.MedicinskiTehničarServis;
import test.objectMother.MedicinarMother;
import test.objectMother.ZahtevMother;

public class MedicinarServisTest {
	
	static MedicinskiTehničarServis medicinarServis;
	
	static List<MedicinskiTehničar>medicinari;
	static MedicinskiTehničar jovana;
	static MedicinskiTehničar sanja;
	static MedicinskiTehničar tijana;
	static MedicinskiTehničar saOtkazom;
	
	static Zahtev zahtev1;
	static Zahtev zahtev2;
	static Zahtev zahtev3;
	static Zahtev zahtev4;
	
	@BeforeClass
	public static void setBeforeClass() {
		medicinari = new ArrayList<MedicinskiTehničar>();
	}
	
	
	@Before
	public void beforeTest() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
		
		medicinarServis = new MedicinskiTehničarServis();
		
		jovana = MedicinarMother.getJovana(1);
		sanja = MedicinarMother.getSanja(2);
		tijana = MedicinarMother.getTijana(3);
		saOtkazom = MedicinarMother.getSaOtkazom(4);
		medicinari.addAll(Arrays.asList(jovana, sanja, tijana, saOtkazom));
	}
	
	@After
	public void setAfterTest() {
		medicinari.clear();
	}
	
	@Test
	public void getZaposleniMedicinariTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getMedicinskiTehničarRepo().getEntitetiList().addAll(medicinari);
		
		List<Korisnik> expected = Arrays.asList(jovana, sanja, tijana);
		List<Korisnik> actual = medicinarServis.getZaposleniMedicinari();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void getMedicinarById() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getMedicinskiTehničarRepo().getEntitetiList().addAll(medicinari);
		
		MedicinskiTehničar expected = jovana;
		MedicinskiTehničar actual = medicinarServis.getById(1);
		assertEquals(expected, actual);
		
		actual = medicinarServis.getById(10);
		assertTrue(actual == null);
	}
	
	@Test
	public void editTest() {
		StručnaSprema sprema = new StručnaSprema();
		MedicinskiTehničar m = new MedicinskiTehničar(10, "ime", "prezime", "username", "lozinka", new StručnaSprema(), true);
		medicinarServis.edit(m, "ime", "prezime", sprema);
		
		assertTrue("Ime nije promenjeno", m.getIme().equals("ime"));
		assertTrue("Prezime nije promenjeno", m.getPrezime().equals("prezime"));
		assertTrue("Sprema nije promenjena", m.getSprema().equals(sprema));
	}
	
	@Test
	public void dajOtkazMTehničaruTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getMedicinskiTehničarRepo().getEntitetiList().addAll(medicinari);
		
		assertTrue(jovana.isValidan());
		medicinarServis.dajOtkazMTehničaru(1);
		assertFalse(jovana.isValidan());
	}
	
	
	private void podesiZahteveSaVremenomIDostavom() {
		zahtev1 = ZahtevMother.medicinarJovana(1, 1);
		zahtev1.setDatumObrade(LocalDate.parse("2020-07-10")); // petak
		zahtev1.setDostava(new Dostava(1, true, LocalDate.parse("2020-07-10")));
		
		zahtev2 = ZahtevMother.medicinarJovana(2, 1);
		zahtev2.setDatumObrade(LocalDate.parse("2020-07-15")); // sreda
		zahtev2.setDostava(new Dostava(2, true, LocalDate.parse("2020-07-10")));
		
		zahtev3 = ZahtevMother.medicinarJovana(3, 1);
		zahtev3.setDatumObrade(LocalDate.parse("2020-08-20")); // cetvrtak
		zahtev3.setDostava(new Dostava(3, false, LocalDate.parse("2020-07-10")));
		
		zahtev4 = ZahtevMother.medicinarSanja(4, 2);
		zahtev4.setDatumObrade(LocalDate.parse("2020-08-20"));
		zahtev4.setDostava(new Dostava(4, true, LocalDate.parse("2020-07-15")));
		
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getZahtevRepo().getEntitetiList().addAll(Arrays.asList(zahtev1, zahtev2, zahtev3, zahtev4));
	}
	
	@Test
	public void getBrojObradjenihZahtevaUZadatomPerioduSaDanimaTest() {
		podesiZahteveSaVremenomIDostavom();
		
		int expected = 2;
		LocalDate pocetak = LocalDate.parse("2020-07-01");
		LocalDate kraj = LocalDate.parse("2020-07-30");
		List<DayOfWeek>dani = Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY);
		
		int actual = medicinarServis.getBrojObradjenihZahtevaUZadatomPerioduSaDanima(jovana, pocetak, kraj,dani);
		assertTrue(expected == actual);
		
		dani = Arrays.asList(DayOfWeek.FRIDAY);
		expected = 1;
		actual = medicinarServis.getBrojObradjenihZahtevaUZadatomPerioduSaDanima(jovana, pocetak, kraj,dani);
		assertTrue(expected == actual);
	}
	
	@Test
	public void getBrojKucnihPosetaUZadatomPerioduSaDanimaTest() {
		podesiZahteveSaVremenomIDostavom();
		
		int expected = 2;
		LocalDate pocetak = LocalDate.parse("2020-07-01");
		LocalDate kraj = LocalDate.parse("2020-07-30");
		List<DayOfWeek>dani = Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY);
		
		int actual = medicinarServis.getBrojKucnihPosetaUZadatomPerioduSaDanima(jovana, pocetak, kraj,dani);
		assertTrue(expected == actual);
		
		pocetak = LocalDate.parse("2020-07-01");
		kraj = LocalDate.parse("2020-10-30");
		dani = Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
		
		expected = 2;
		actual = medicinarServis.getBrojKucnihPosetaUZadatomPerioduSaDanima(jovana, pocetak, kraj,dani);
		assertTrue(expected == actual);
	}
}
