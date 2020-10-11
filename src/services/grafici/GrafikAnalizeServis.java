package services.grafici;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.PosebnaAnaliza;
import entity.korisnici.Pacijent;
import services.NalaziServis;

public class GrafikAnalizeServis {
	NalaziServis nalaziServis = new NalaziServis();
	
	private Pacijent pacijent;
	private PosebnaAnaliza analiza;
	
	public GrafikAnalizeServis(Pacijent pacijent, PosebnaAnaliza analiza) {
		this.pacijent = pacijent;
		this.analiza = analiza;
	}
	
	public TreeMap<Date, Double>getPodaciZaGrafik(){
		TreeMap<Date, Double>retMap = new TreeMap<Date, Double>();
		List<Nalaz>nalazi = nalaziServis.getNalaziZaPacijenta(this.pacijent);
		
		for (Nalaz nalaz: nalazi) {
			for (AnalizaZaObradu stavka: nalaz.getAnalizeZaObradu()) {
				if (stavka.getAnaliza().equals(this.analiza)) {
					Date datum = Date.from(nalaz.getDatumObrade().atStartOfDay(ZoneId.systemDefault()).toInstant());
					retMap.put(datum, stavka.getIzmerenaVrednost());
				}
			}
		}
		return retMap;
	}
	
}
