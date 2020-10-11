package entity;

public class DatumiPopustaAnaliza extends DatumIDaniZaPopuste{
	private static DatumiPopustaAnaliza instanca = null;

	public DatumiPopustaAnaliza() {
	}
	public static DatumiPopustaAnaliza getInstance() {
		if (instanca == null) {
			instanca = new DatumiPopustaAnaliza();
		}
		return instanca;
	}
}
