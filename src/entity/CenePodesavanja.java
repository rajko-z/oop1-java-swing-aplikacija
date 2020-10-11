package entity;

public class CenePodesavanja extends Entity{
	
	private double osnova;
	private double dostavaDatum;
	private double dostavaVreme;
	private static CenePodesavanja instanca = null;
	
	private CenePodesavanja() {
	}
	
	public static CenePodesavanja getInstance() {
		if (instanca == null) {
			instanca = new CenePodesavanja();
		}
		return instanca;
	}
	
	public void setData(Double osnova, Double dostavaDatum, Double dostavaVreme) {
		this.osnova = osnova;
		this.dostavaDatum = dostavaDatum;
		this.dostavaVreme = dostavaVreme;
	}

	
	public double getOsnova() {
		return osnova;
	}

	public void setOsnova(double osnova) {
		this.osnova = osnova;
	}

	public double getDostavaDatum() {
		return dostavaDatum;
	}

	public void setDostavaDatum(double dostavaDatum) {
		this.dostavaDatum = dostavaDatum;
	}

	public double getDostavaVreme() {
		return dostavaVreme;
	}

	public void setDostavaVreme(double dostavaVreme) {
		this.dostavaVreme = dostavaVreme;
	}

	@Override
	public String toFileEntity() {
		return String.format("%s  | %s  | %s", this.osnova, this.dostavaDatum, this.dostavaVreme);
	}
	
	
	
	
}
