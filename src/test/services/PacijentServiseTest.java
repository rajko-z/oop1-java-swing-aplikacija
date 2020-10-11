package test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import entity.Nalaz;
import entity.Zahtev;
import entity.korisnici.Pacijent;
import repositories.RepositoryFactory;
import services.PacijentServis;
import test.objectMother.NalazMother;
import test.objectMother.PacijentMother;
import test.objectMother.ZahtevMother;

public class PacijentServiseTest {

	static PacijentServis pacijentServis;
	
	static Pacijent pacijent1;
	static Pacijent pacijent2;
	static Pacijent pacijent3;
	static Pacijent pacijentNeRegistrovan;

	static List<Pacijent> pacijenti;
	static List<Nalaz> nalazi;

	@BeforeClass
	public static void BeforeClass() {
		pacijenti = new ArrayList<Pacijent>();
		nalazi = new ArrayList<Nalaz>();
	}

	@Before
	public void setBeforeMethod() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// reset singleton
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null, null);
		
		// always run with fresh instance
		pacijentServis = new PacijentServis();
		
		pacijent1 = new Pacijent(1, "ime1", "prezime1", "username", "password", "12345", "muški", LocalDate.parse("2000-08-07"), "12345678","adresa1");
		pacijent2 = new Pacijent(2, "ime2", "prezime1", "username", "password", "12345678", "muški", LocalDate.parse("2000-08-07"), "12345678","adresa1");
		pacijent3 = new Pacijent(3, "ime3", "prezime1", "username", "password", "0000", "muški", LocalDate.parse("2000-08-07"), "12345678","adresa1");
		pacijentNeRegistrovan = new Pacijent(4, "ime4", "prezime1", "123455", "muški", LocalDate.parse("2000-08-07"), "12345678","adresa1");
		
		pacijenti.addAll(Arrays.asList(pacijent1, pacijent2, pacijent3, pacijentNeRegistrovan));
	
	}

	@Test
	public void getByIdTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPacijentRepo().getEntitetiList().addAll(pacijenti);
		
		Pacijent expected = pacijent1;
		Pacijent actual = pacijentServis.getById(1);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void LBOjeJedinstvenTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPacijentRepo().getEntitetiList().addAll(pacijenti);
		
		assertTrue(pacijentServis.LBOjeJedinstven("12345", pacijent1));
		assertTrue(pacijentServis.LBOjeJedinstven("12345555", pacijent1));
		assertFalse(pacijentServis.LBOjeJedinstven("0000", pacijent1));
	}
	
	@Test
	public void editTest() {
		pacijentServis.edit(pacijent1, "novoIme", "novoPrezime", "muski", "1234567", "12345678", "adresa", LocalDate.parse("2020-10-07"));
		
		assertEquals("Ime nije promenjeno", "novoIme", pacijent1.getIme());
		assertEquals("Prezime nije promenjeno", "novoPrezime", pacijent1.getPrezime());
		assertEquals("Pol greska", "muski", pacijent1.getPol());
		assertEquals("LBO broj nije promenjen", "1234567", pacijent1.getLBO());
		assertEquals("Broj telefona nije promenjen", "12345678", pacijent1.getTelefon());
		assertEquals("Adresa nije promenjena", "adresa", pacijent1.getAdresa());
		assertEquals("Datum rodjena greska pri promeni", LocalDate.parse("2020-10-07"), pacijent1.getDatumRodjena());
	}
	
	@Test
	public void addTest() {
		Pacijent expected = new Pacijent(1, "ime", "prezime", "0000", "muški", LocalDate.parse("2000-08-07"), "12345678","adresa1");
		Pacijent actual = pacijentServis.add("ime", "prezime", "muški", "0000", "12345678", "adresa1", LocalDate.parse("2000-08-07"));
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void getPacijentSaZadatimLBObrojemTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPacijentRepo().getEntitetiList().addAll(pacijenti);
		
		Pacijent expected = pacijent1;
		Pacijent actual = pacijentServis.getPacijentSaZadatimLBObrojem("12345");
		assertThat(expected).isEqualToComparingFieldByField(actual);
		
		actual = pacijentServis.getPacijentSaZadatimLBObrojem("fdsfdsfdsfdsfds");
		assertTrue(actual == null);
	}
	
	@Test
	public void getPacijentSaZadatimLBObrojemKojiNijeRegistrovanTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPacijentRepo().getEntitetiList().addAll(pacijenti);
		
		Pacijent expected = pacijentNeRegistrovan;
		Pacijent actual = pacijentServis.getPacijentSaZadatimLBObrojem("123455");
		assertThat(expected).isEqualToComparingFieldByField(actual);
		
		actual = pacijentServis.getPacijentSaZadatimLBObrojem("fdsfdsfdsfdsfds");
		assertTrue(actual == null);
	}
	
	@Test
	public void registrujPacijentaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getPacijentRepo().getEntitetiList().addAll(pacijenti);
		
		assertTrue(pacijentNeRegistrovan.getUsername() == null);
		assertTrue(pacijentNeRegistrovan.getPassword() == null);
		pacijentServis.registrujPacijenta(pacijentNeRegistrovan);
		assertTrue(pacijentNeRegistrovan.getUsername() != null);
		assertTrue(pacijentNeRegistrovan.getPassword() != null);
	}
	
	@Test
	public void getBrojNalazaZaPacijentaTest() {
		RepositoryFactory rp = RepositoryFactory.getInstance();
		rp.getNalazRepo().getEntitetiList().addAll(Arrays.asList(NalazMother.nalazPeraBezOstatka(1, 1, 1),
												   	NalazMother.nalazPeraBezOstatka(2, 2, 1),
												   	NalazMother.nalazPeraBezOstatka(3, 3, 1),
												   	NalazMother.nalazPeraBezOstatka(4, 4, 2)));
		int expected = 3;
		int actual = pacijentServis.getBrojNalazaZaPacijenta(PacijentMother.getPera(1), rp.getNalazRepo().getEntitetiList());
		assertTrue(expected == actual);
	}
	
	@Test
	public void getUkupnaCenaNalazaTest() {
		Nalaz nalazPera1 = NalazMother.nalazPeraBezOstatka(1, 1, 1);
		nalazPera1.getZahtev().setCena(1000.0);
		
		Nalaz nalazPera2 = NalazMother.nalazPeraBezOstatka(2, 2, 1);
		nalazPera2.getZahtev().setCena(1000.0);
		
		Nalaz nalazPera3 = NalazMother.nalazPeraBezOstatka(3, 3, 1);
		nalazPera3.getZahtev().setCena(2500.0);
		
		
		double expected = 4500.0;
		double actual = pacijentServis.getUkupnaCenaNalaza(PacijentMother.getPera(1), Arrays.asList(nalazPera1, nalazPera2, nalazPera3));
		System.out.println(actual);
		
		assertTrue(expected == actual);
	}
}
