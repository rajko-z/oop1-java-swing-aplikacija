package services.grafici;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.AnalizaZaObradu;
import entity.GrupaAnaliza;
import entity.Nalaz;
import services.PlataServis;

public class GrafikPrihodaServis {
	
	private List<Nalaz>nalazi;
	private PlataServis plataServis = new PlataServis();
	
	public GrafikPrihodaServis(List<Nalaz>nalazi) {
		this.nalazi = nalazi;
	}
	
	private HashMap<GrupaAnaliza, Integer>getBrojPojavljivanjaGrupa(){
		HashMap<GrupaAnaliza, Integer>retMap = new HashMap<GrupaAnaliza, Integer>();
		for (Nalaz nalaz : this.nalazi) {
			for (AnalizaZaObradu stavka: nalaz.getAnalizeZaObradu()) {
				GrupaAnaliza ga = stavka.getAnaliza().getGrupaAnaliza();
				if (retMap.containsKey(ga)) {
					retMap.put(ga, retMap.get(ga) + 1);
				}else {
					retMap.put(ga, 1);
				}
			}
		}
		return retMap;
	}
	
	private int getBrojSvihStavki() {
		int retVal = 0;
		for (Nalaz nalaz: this.nalazi) {
			retVal += nalaz.getAnalizeZaObradu().size();
		}
		return retVal;
	}
	
	public HashMap<GrupaAnaliza, Double>getProcenatPrihodaOdSvakeGrupe(){
		int brojSvihStavki = getBrojSvihStavki();
		HashMap<GrupaAnaliza, Double> retMap = new HashMap<GrupaAnaliza, Double>();
		HashMap<GrupaAnaliza, Integer> grupe = getBrojPojavljivanjaGrupa();
		
		for (Map.Entry<GrupaAnaliza, Integer> par : grupe.entrySet()) {
			double vrednost = plataServis.getProcenatIznosaOdUkupnog(brojSvihStavki, par.getValue());
			retMap.put(par.getKey(), vrednost);
		}
		return retMap;
	}
	
}
