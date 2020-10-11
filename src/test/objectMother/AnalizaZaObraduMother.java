package test.objectMother;

import entity.AnalizaZaObradu;
import entity.korisnici.Laborant;

public class AnalizaZaObraduMother {
	
	public static AnalizaZaObradu getStavkaSaLaborantom(int idStavke, Laborant laborant) {
		AnalizaZaObradu retVal = new AnalizaZaObradu(idStavke);
		retVal.setLaborant(laborant);
		retVal.setIzmerenaVrednost(5.0);
		retVal.setJesteObradjena(true);
		return retVal;
	}
	
}	
