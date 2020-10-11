package test.objectMother;

import java.time.LocalDate;

import entity.korisnici.Pacijent;

public class PacijentMother {
	
	public static Pacijent getPera(int id) {
		return new Pacijent(id, "Pera", "Peric", "pera", "perke22", "12345", "muški", LocalDate.parse("2000-10-08"), "0653146021", "adresa");
	}
	public static Pacijent getMika(int id) {
		return new Pacijent(id, "Mika", "Mikic", "mika", "micko22", "0000", "muški", LocalDate.parse("1980-11-08"), "064321134", "adresaaa");
	}
	public static Pacijent getVaske(int id) {
		return new Pacijent(id, "Vaske", "Vaske", "vaske", "vakse22", "2222", "muški", LocalDate.parse("2000-10-08"), "08434323323", "adresada");
	}
	public static Pacijent getDzoni(int id) {
		return new Pacijent(id, "Dzoni", "Dzoni", "dzoni", "dzoni22", "3333", "muški", LocalDate.parse("1990-10-08"), "0634324324", "adresica");
	}
	
}
