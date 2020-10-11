package test.objectMother;

import entity.Nalaz;

public class NalazMother {
	
	public static Nalaz nalazPeraBezOstatka(int nalazId, int zahtevId, int pacijentId) {
		Nalaz n = new Nalaz(nalazId);
		n.setZahtev(ZahtevMother.zahtevPeraBezOstatka(zahtevId, pacijentId));
		return n;
	}
	public static Nalaz nalazMikaBezOstatka(int nalazId, int zahtevId, int pacijentId) {
		Nalaz n = new Nalaz(nalazId);
		n.setZahtev(ZahtevMother.zahtevMikaBezOstatka(zahtevId, pacijentId));
		return n;
	}
	
	
}
