package services;

import entity.StručnaSprema;

public class StručnaSpremaServis {
	public StručnaSpremaServis() {
	}
	public boolean setKoeficijent(Double vrednost, StručnaSprema sprema) {
		sprema.setKoeficijent(vrednost);
		return true;
	}
}
