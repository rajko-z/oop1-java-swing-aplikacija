package entity;

public class DatumiPopustaGrupa extends DatumIDaniZaPopuste{
	private static DatumiPopustaGrupa instanca = null;

	public DatumiPopustaGrupa() {
	}
	
	public static DatumiPopustaGrupa getInstance() {
		if (instanca == null) {
			instanca = new DatumiPopustaGrupa();
		}
		return instanca;
	}
}
