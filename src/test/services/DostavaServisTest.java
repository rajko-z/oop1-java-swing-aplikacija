package test.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import entity.Dostava;
import repositories.RepositoryFactory;
import services.DostavaServis;

public class DostavaServisTest {
	
	static DostavaServis dostavaServis;
	
	
	@Before
	public void setBeforeTets() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field instanca = RepositoryFactory.class.getDeclaredField("instanca");
		instanca.setAccessible(true);
		instanca.set(null,null);
		
		dostavaServis = new DostavaServis();
	}
	
	@Test
	public void kreirajDostavuBezKucnePoseteTest() {
		LocalDate datum = LocalDate.parse("2020-07-03");
		Dostava expected = new Dostava(1, false, datum);
		
		Dostava actual = dostavaServis.kreirajDostavuBezKucnePosete(datum);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void kreirajKucnuDostavuBezVremenaTest() {
		LocalDate datum = LocalDate.parse("2020-07-03");
		Dostava expected = new Dostava(1, true, datum);
		
		Dostava actual = dostavaServis.kreirajKucnuDostavuBezVremena(datum);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
	
	@Test
	public void kreirajKucnuDostavuSaVremenomTest(){
		LocalDate datum = LocalDate.parse("2020-07-03");
		LocalTime vreme = LocalTime.parse("12:00");
		Dostava expected = new Dostava(1, true, datum, vreme);
		
		Dostava actual = dostavaServis.kreirajKucnuDostavuSaVremenom(datum, vreme);
		assertThat(expected).isEqualToComparingFieldByField(actual);
	}
}
