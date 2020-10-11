package test.services;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.DatumiPopustaAnaliza;
import entity.DatumiPopustaGrupa;
import services.DatumiVazenjaPopustaServis;

public class DatumiVazenjaPopustaServisTest {
	
	static DatumiVazenjaPopustaServis datumiVazenjaPopustaServis;
	
	@Before
	public void setBeforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instancaDatumiAnalize = DatumiPopustaAnaliza.class.getDeclaredField("instanca");
		instancaDatumiAnalize.setAccessible(true);
		instancaDatumiAnalize.set(null, null);
		
		Field instancaDatumiGrupe = DatumiPopustaGrupa.class.getDeclaredField("instanca");
		instancaDatumiGrupe.setAccessible(true);
		instancaDatumiGrupe.set(null, null);
		
		datumiVazenjaPopustaServis = new DatumiVazenjaPopustaServis();
		
	}
	
	@Test
	public void editDatumiAnalizeTest() {
		DatumiPopustaAnaliza datumiPopustaAnaliza = DatumiPopustaAnaliza.getInstance();
		
		LocalDate pocetak = LocalDate.parse("2020-08-05");
		LocalDate kraj = LocalDate.parse("2020-09-05");
		List<DayOfWeek>dani = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
		
		datumiVazenjaPopustaServis.editDatumiAnalize(pocetak, kraj, dani);
		
		assertEquals("Pocetni datum se ne podudara",pocetak, datumiPopustaAnaliza.getPocetak());
		assertEquals("Krajni datum se ne podudara",kraj, datumiPopustaAnaliza.getKraj());
		assertArrayEquals("Dani se ne podudaraju",dani.toArray(), datumiPopustaAnaliza.getDani().toArray());
	}
	
	@Test
	public void editDatumiGrupeTest() {
		DatumiPopustaGrupa datumiPopustaAnaliza = DatumiPopustaGrupa.getInstance();
		
		LocalDate pocetak = LocalDate.parse("2020-08-05");
		LocalDate kraj = LocalDate.parse("2020-09-05");
		List<DayOfWeek>dani = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
		
		datumiVazenjaPopustaServis.editDatumiGrupe(pocetak, kraj, dani);
		
		assertEquals("Pocetni datum se ne podudara",pocetak, datumiPopustaAnaliza.getPocetak());
		assertEquals("Krajni datum se ne podudara",kraj, datumiPopustaAnaliza.getKraj());
		assertArrayEquals("Dani se ne podudaraju",dani.toArray(), datumiPopustaAnaliza.getDani().toArray());
	}
	
	@Test
	public void danasSuAnalizeNaPopustu() {
		DatumiPopustaAnaliza datumiPopustaAnaliza = DatumiPopustaAnaliza.getInstance();
		
		LocalDate pocetak = LocalDate.now().minusDays(10);
		LocalDate kraj = LocalDate.now().plusDays(10);
		List<DayOfWeek>dani = Arrays.asList(LocalDate.now().getDayOfWeek());
		datumiPopustaAnaliza.setData(pocetak, kraj, dani);
		
		assertTrue(datumiVazenjaPopustaServis.danasSuAnalizeNaPopustu());
		
		pocetak = LocalDate.now().plusDays(10);
		kraj = LocalDate.now().plusDays(20);
		datumiPopustaAnaliza.setData(pocetak, kraj, dani);
		
		assertFalse(datumiVazenjaPopustaServis.danasSuAnalizeNaPopustu());
	}
	
	@Test
	public void danasSuGrupeNaPopustu() {
		DatumiPopustaGrupa datumiPopustaGrupa = DatumiPopustaGrupa.getInstance();
		
		LocalDate pocetak = LocalDate.now().minusDays(10);
		LocalDate kraj = LocalDate.now().plusDays(10);
		List<DayOfWeek>dani = Arrays.asList(LocalDate.now().getDayOfWeek());
		datumiPopustaGrupa.setData(pocetak, kraj, dani);
		
		assertTrue(datumiVazenjaPopustaServis.danasSuGrupeNaPopustu());
		
		pocetak = LocalDate.now().plusDays(10);
		kraj = LocalDate.now().plusDays(20);
		datumiPopustaGrupa.setData(pocetak, kraj, dani);
		
		assertFalse(datumiVazenjaPopustaServis.danasSuGrupeNaPopustu());
	}
}
