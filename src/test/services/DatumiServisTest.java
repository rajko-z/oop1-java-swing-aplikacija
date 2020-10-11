package test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import org.junit.Before;
import org.junit.Test;

import services.DatumiServis;

public class DatumiServisTest {
	
	static DatumiServis datumiServis;
	
	@Before
	public void setBeforeTest() {
		datumiServis = new DatumiServis();
	}
	
	@Test
	public void datumJeProslostTest_PozitivanSlucaj() {
		
		LocalDate datum1 = LocalDate.parse("2020-08-20");
		assertTrue(datumiServis.datumJeProslost(datum1));
	}
	
	@Test
	public void datumJeProslostTest_NegativnaSlucaj() {
		
		LocalDate datum2 = LocalDate.now();
		assertFalse(datumiServis.datumJeProslost(datum2));
	}
	
	@Test
	public void getDatumPoslednjegDanaUMesecuNekeGodineTest_PozitivanSlucaj() {
		LocalDate datum1 = LocalDate.parse("2020-08-31");
		LocalDate datum2 = LocalDate.parse("2020-07-31");
		LocalDate datum3 = LocalDate.parse("2020-06-30");
		
		assertEquals(datum1, datumiServis.getDatumPoslednjegDanaUMesecuNekeGodine(Year.parse("2020"), Month.AUGUST));
		assertEquals(datum2, datumiServis.getDatumPoslednjegDanaUMesecuNekeGodine(Year.parse("2020"), Month.JULY));
		assertEquals(datum3, datumiServis.getDatumPoslednjegDanaUMesecuNekeGodine(Year.parse("2020"), Month.JUNE));
	}
	
	@Test
	public void getDatumPoslednjegDanaUMesecuNekeGodineTest_NegativanSlucaj() {
		LocalDate datum1 = LocalDate.parse("2020-08-30");
		
		assertNotEquals(datum1, datumiServis.getDatumPoslednjegDanaUMesecuNekeGodine(Year.parse("2020"), Month.AUGUST));
	}
	
	
	@Test
	public void getBrojDanaUMesecuTest_PozitivanSlucaj() {
		assertTrue(datumiServis.getBrojDanaUMesecu(Year.parse("2020"), Month.AUGUST) == 31);
		assertTrue(datumiServis.getBrojDanaUMesecu(Year.parse("2020"), Month.JANUARY) == 31);
		assertTrue(datumiServis.getBrojDanaUMesecu(Year.parse("2019"), Month.NOVEMBER) == 30);
	}
	
	@Test
	public void getBrojDanaUMesecuTest_NegativanSlucaj() {
		assertFalse(datumiServis.getBrojDanaUMesecu(Year.parse("2020"), Month.AUGUST) == 30);
	}
	
	
}
