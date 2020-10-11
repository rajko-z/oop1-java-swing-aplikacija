package test.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import services.ValidatorServis;

public class ValidatorServisTest {
	
	static ValidatorServis validatorServis;
	
	@Before
	public void beforeTest() {
		validatorServis = new ValidatorServis();
	}
	
	@Test
	public void LBOjeIspravanTest() {
		String lbo1 = "12345678901";
		String lbo2 = "1234561";
		String lbo3 = "fdsfsd";
		
		assertTrue(validatorServis.LBOjeIspravan(lbo1));
		assertFalse(validatorServis.LBOjeIspravan(lbo2));
		assertFalse(validatorServis.LBOjeIspravan(lbo3));
	}
	
	@Test
	public void brojTelefonaJeIspravanTest() {
		String brojTel1 = "065341234";
		String brojTel2 = "fdsfdsfdsf";
		String brojTel3 = "12345678900000";
		
		assertTrue(validatorServis.brojTelefonaJeIspravan(brojTel1));
		assertFalse(validatorServis.brojTelefonaJeIspravan(brojTel2));
		assertFalse(validatorServis.brojTelefonaJeIspravan(brojTel3));
	}
	
	@Test
	public void nijeBrojUOdgovarajucemRasponuTest() {
		double low = 2.3;
		double high = 15.6;
		
		String unos1 = "10.2";
		String unos2 = "2.3";
		String unos3 = "15.6";
		String unos4 = "fdsfds";
		String unos5 = "45.9";
		
		assertTrue(validatorServis.nijeBrojUOdgovarajucemRasponu(low, high, unos4));
		assertTrue(validatorServis.nijeBrojUOdgovarajucemRasponu(low, high, unos5));
		assertFalse(validatorServis.nijeBrojUOdgovarajucemRasponu(low, high, unos1));
		assertFalse(validatorServis.nijeBrojUOdgovarajucemRasponu(low, high, unos2));
		assertFalse(validatorServis.nijeBrojUOdgovarajucemRasponu(low, high, unos3));
	}
}
