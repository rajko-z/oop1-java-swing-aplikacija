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

import entity.AnalizaZaObradu;
import entity.GrupaAnaliza;
import entity.Nalaz;
import entity.StručnaSprema;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import repositories.RepositoryFactory;
import services.LaborantServis;
import test.objectMother.AnalizaZaObraduMother;
import test.objectMother.LaborantMother;

public class LaborantServisTest {

	
	static LaborantServis laborantServis;
	
	static List<Laborant>laboranti;
	static Laborant doca;
	static Laborant paki;
	static Laborant miki;
	static Laborant saOtkazom;
	
	@BeforeClass
	public static void setBeforeClass() {
		laboranti = new ArrayList<Laborant>();
	}
	
	@Before
	public void setBeforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
		
		laborantServis = new LaborantServis();
		
		doca = LaborantMother.docaBezSpec(1);
		paki = LaborantMother.pakiBezSpec(2);
		miki = LaborantMother.mikiBezSpec(3);
		saOtkazom = LaborantMother.saOtkazom(4);
		
		laboranti.addAll(Arrays.asList(doca, paki, miki, saOtkazom));
	}
	
	@After
	public void setAfterTest() {
		laboranti.clear();
	}
	
	@Test
	public void getZaposleniLaborantiTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getLaborantRepo().getEntitetiList().addAll(laboranti);
		
		List<Korisnik> expected = Arrays.asList(doca, paki, miki);
		List<Korisnik> actual = laborantServis.getZaposleniLaboranti();
		
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void getByIdTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getLaborantRepo().getEntitetiList().addAll(laboranti);
		
		Laborant expected = doca;
		Laborant actual = laborantServis.getById(1);
		assertEquals(expected, actual);
		
		actual = laborantServis.getById(10);
		assertTrue(actual == null);
	}
	
	@Test
	public void dajOtkazLaborantuTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getLaborantRepo().getEntitetiList().addAll(laboranti);
		
		assertTrue(doca.isValidan());
		laborantServis.dajOtkazLaborantu(1);
		assertFalse(doca.isValidan());
	}
	
	@Test
	public void editTest() {
		GrupaAnaliza grupa1 = new GrupaAnaliza(1, "grupa1", true);
		GrupaAnaliza grupa2 = new GrupaAnaliza(2, "grupa2", true);
		ArrayList<GrupaAnaliza>spec = new ArrayList<GrupaAnaliza>();
		spec.add(grupa1);
		spec.add(grupa2);
		
		doca.setGrupeAnaliza(spec);
		laborantServis.edit(doca, "novo ime", "novo prezime", new StručnaSprema(), spec);
		assertTrue("ime nije promenjeno", doca.getIme().equals("novo ime"));
		assertTrue("prezime nije promenjeno", doca.getPrezime().equals("novo prezime"));
		assertArrayEquals(doca.getGrupeAnaliza().toArray(), spec.toArray());
	}
	
	@Test
	public void getBrojObradjenihNalazUZadatomPeriouduSaDanimaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		
		AnalizaZaObradu stavka1 = AnalizaZaObraduMother.getStavkaSaLaborantom(1, doca);
		AnalizaZaObradu stavka2 = AnalizaZaObraduMother.getStavkaSaLaborantom(2, doca);
		AnalizaZaObradu stavka3 = AnalizaZaObraduMother.getStavkaSaLaborantom(3, paki);
		AnalizaZaObradu stavka4 = AnalizaZaObraduMother.getStavkaSaLaborantom(4, doca);
		
		ArrayList<AnalizaZaObradu>stavkeZaPrviNalaz = new ArrayList<AnalizaZaObradu>();
		ArrayList<AnalizaZaObradu>stavkeZaDrugiNalaz = new ArrayList<AnalizaZaObradu>();
		
		stavkeZaPrviNalaz.add(stavka1);
		stavkeZaPrviNalaz.add(stavka2);
		stavkeZaDrugiNalaz.add(stavka3);
		stavkeZaDrugiNalaz.add(stavka4);
		
		
		LocalDate pocetak = LocalDate.parse("2020-08-10");
		LocalDate kraj = LocalDate.parse("2020-08-20");
		List<DayOfWeek>dani = Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.SATURDAY);
		
		Nalaz nalaz1 = new Nalaz(1);
		nalaz1.setAnalizeZaObradu(stavkeZaPrviNalaz);
		nalaz1.setDatumObrade(LocalDate.parse("2020-08-15")); //subota
		nalaz1.setBrojAnaliza(2);
		rp.getNalazRepo().getEntitetiList().add(nalaz1);
		
		Nalaz nalaz2 = new Nalaz(2);
		nalaz2.setAnalizeZaObradu(stavkeZaDrugiNalaz);
		nalaz2.setDatumObrade(LocalDate.parse("2020-08-18")); // utorak
		nalaz2.setBrojAnaliza(2);
		rp.getNalazRepo().getEntitetiList().add(nalaz2);
		
		int expected = 3;
		int actual = laborantServis.getBrojObradjenihNalazUZadatomPeriouduSaDanima(doca, pocetak, kraj, dani);
		assertTrue(expected == actual);
	}
	
}
