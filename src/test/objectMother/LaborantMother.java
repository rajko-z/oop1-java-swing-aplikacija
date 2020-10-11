package test.objectMother;

import entity.korisnici.Laborant;

public class LaborantMother {
	
	public static Laborant docaBezSpec(int id) {
		Laborant doca = new Laborant(id);
		doca.setIme("doca");
		doca.setPrezime("docic");
		doca.setUsername("doca.doca");
		doca.setPassword("doca123");
		doca.setValidan(true);
		return doca;
	}
	
	public static Laborant pakiBezSpec(int id) {
		Laborant paki = new Laborant(id);
		paki.setIme("pakson");
		paki.setPrezime("paksic");
		paki.setUsername("paki.paki");
		paki.setPassword("paki123");
		paki.setValidan(true);
		return paki;
	}
	
	public static Laborant mikiBezSpec(int id) {
		Laborant miki = new Laborant(id);
		miki.setIme("miki");
		miki.setPrezime("mikic");
		miki.setUsername("miki.miki");
		miki.setPassword("miki123");
		miki.setValidan(true);
		return miki;
	}
	
	public static Laborant saOtkazom(int id) {
		Laborant o = new Laborant(id);
		o.setIme("laborant");
		o.setPrezime("labic");
		o.setUsername("lab.lab");
		o.setPassword("lab123");
		o.setValidan(false);
		return o;
	}
}
