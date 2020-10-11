package utils;

public class FileSettings {
	
	// ne cuvam promene u fajl, jer sam isprobavao sa jar fajlom, samo citam
	
	/*private String cenePodesavanjaFileName = "./res/res/data/cene_podesavanja.txt";
	private String korisniciFileName = "res/res/data/korisnici.txt";
	private String grupeAnalizaFileName = "./res/res/data/grupe_analiza.txt";
	private String posebneAnalizeFileName = "./res/res/data/posebne_analize.txt";
	private String stručnaSpremaFileName = "./res/res/data/stručna_sprema.txt";
	private String stanjaZahtevaFileName = "./res/res/data/stanja_zahteva.txt";
	private String dostavaFileName = "./res/res/data/dostave.txt";
	private String zahteviFileName = "./res/res/data/zahtevi.txt";
	private String nalaziFileName = "./res/res/data/nalazi.txt";
	private String analizaZaObraduFileName = "./res/res/data/analize_za_obradu.txt";
	private String plateFileName = "./res/res/data/plate.txt";
	private String datumiPopustaAnalizaFileName = "./res/res/data/datumi_popusta_analiza.txt";
	private String datumiPopustaGrupaFileName = "./res/res/data/datumi_popusta_grupa.txt";
	private String analizaPopustFileName = "./res/res/data/analiza_popust.txt";
	private String grupaPopustFileName = "./res/res/data/grupa_popust.txt";*/

	
	private String cenePodesavanjaFileName = "cene_podesavanja.txt";
	private String korisniciFileName = "korisnici.txt";
	private String grupeAnalizaFileName = "grupe_analiza.txt";
	private String posebneAnalizeFileName = "posebne_analize.txt";
	private String stručnaSpremaFileName = "stručna_sprema.txt";
	private String stanjaZahtevaFileName = "stanja_zahteva.txt";
	private String dostavaFileName = "dostave.txt";
	private String zahteviFileName = "zahtevi.txt";
	private String nalaziFileName = "nalazi.txt";
	private String analizaZaObraduFileName = "analize_za_obradu.txt";
	private String plateFileName = "plate.txt";
	private String datumiPopustaAnalizaFileName = "datumi_popusta_analiza.txt";
	private String datumiPopustaGrupaFileName = "datumi_popusta_grupa.txt";
	private String analizaPopustFileName = "analiza_popust.txt";
	private String grupaPopustFileName = "grupa_popust.txt";

	
	
	public FileSettings() {
	}

	public String getAnalizaPopustFileName() {
		return analizaPopustFileName;
	}

	public String getGrupaPopustFileName() {
		return grupaPopustFileName;
	}

	public String getDatumiPopustaAnalizaFileName() {
		return datumiPopustaAnalizaFileName;
	}

	public String getDatumiPopustaGrupaFileName() {
		return datumiPopustaGrupaFileName;
	}

	public String getPlateFileName() {
		return plateFileName;
	}

	public String getZahteviFileName() {
		return zahteviFileName;
	}

	public String getAnalizaZaObraduFileName() {
		return analizaZaObraduFileName;
	}

	public String getNalaziFileName() {
		return nalaziFileName;
	}

	public String getStanjaZahtevaFileName() {
		return stanjaZahtevaFileName;
	}

	public String getKorisniciFileName() {
		return korisniciFileName;
	}

	public String getGrupeAnalizaFileName() {
		return grupeAnalizaFileName;
	}

	public String getPosebneAnalizeFileName() {
		return posebneAnalizeFileName;
	}

	public String getStručnaSpremaFileName() {
		return stručnaSpremaFileName;
	}

	public String getDostavaFileName() {
		return dostavaFileName;
	}

	public String getCenePodesavanjaFileName() {
		return cenePodesavanjaFileName;
	}
}
