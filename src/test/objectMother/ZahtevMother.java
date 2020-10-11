package test.objectMother;

import entity.Zahtev;

public class ZahtevMother {
	
	public static Zahtev zahtevPeraBezOstatka(int zahtevId, int pacijentId) {
		Zahtev z = new Zahtev(zahtevId);
		z.setPacijent(PacijentMother.getPera(pacijentId));
		return z;
	}
	
	public static Zahtev zahtevMikaBezOstatka(int zahtevId, int pacijentId) {
		Zahtev z = new Zahtev(zahtevId);
		z.setPacijent(PacijentMother.getMika(pacijentId));
		return z;
	}
	
	public static Zahtev medicinarJovana(int zahtevId, int medicinarId) {
		Zahtev z = new Zahtev(zahtevId);
		z.setMedicinskiTehničar(MedicinarMother.getJovana(medicinarId));
		return z;
	}
	
	public static Zahtev medicinarSanja(int zahtevId, int medicinarId) {
		Zahtev z = new Zahtev(zahtevId);
		z.setMedicinskiTehničar(MedicinarMother.getSanja(medicinarId));
		return z;
	}
	
}
