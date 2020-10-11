package test.services;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import entity.StručnaSprema;
import services.StručnaSpremaServis;

public class StrucnaSpremaServisTest {
	
	static StručnaSpremaServis spremaServis;
	
	@Before
	public void setBeforeTest() {
		spremaServis = new StručnaSpremaServis();
	}
	
	@Test
	public void setKoeficijentTest() {
		StručnaSprema sprema = new StručnaSprema(1, 2.3, "opis spreme");
		
		spremaServis.setKoeficijent(2.5, sprema);
		
		assertTrue("Promena koeficijenta nije uspela!", sprema.getKoeficijent() == 2.5);
	}

}
