package services.grafici;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeMap;

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.korisnici.Pacijent;
import services.NalaziServis;
import services.PacijentServis;
import utils.DTOselektovaniDatumiIOpsegGodina;

public class GrafikBrojPacijenataServis {
	
	private DTOselektovaniDatumiIOpsegGodina dto;
	private NalaziServis nalaziServis = new NalaziServis();
	private PacijentServis pacijentServis = new PacijentServis();
	
	public GrafikBrojPacijenataServis(DTOselektovaniDatumiIOpsegGodina dto) {
		this.dto = dto;
	}
	
	private HashSet<Pacijent>getPacijentiUVremenskomPerioduUrasponuGodina(LocalDate pocetak, LocalDate kraj){
		HashSet<Pacijent>retSet = new HashSet<Pacijent>();
		for (Nalaz nalaz: nalaziServis.getNalaziUVremenskomPeriodu(pocetak, kraj)) {
			for (AnalizaZaObradu stavka: nalaz.getAnalizeZaObradu()) {
				if (stavka.getAnaliza().equals(dto.getAnaliza())) {
					int brojGodina = pacijentServis.getGodine(nalaz.getZahtev().getPacijent());
					if (brojGodina >= dto.getDonjaGranicaGodina() & brojGodina <= dto.getGornjaGranicaGodina()) {
						retSet.add(nalaz.getZahtev().getPacijent());
					}
				}
			}
		}
		return retSet;
	}
	
	private Integer[] getBrojMuskihIZenskihOdListe(LocalDate pocetak, LocalDate kraj) {
		HashSet<Pacijent>pacijenti = getPacijentiUVremenskomPerioduUrasponuGodina(pocetak, kraj);
		Integer [] retNiz = new Integer[3];
		int muskiBrojac = 0;
		int zenskiBrojac = 0;
		for (Pacijent p: pacijenti) {
			if (p.jeMuško()) {
				muskiBrojac ++;
			}else {
				zenskiBrojac++;
			}
		}
		retNiz[0] = muskiBrojac;
		retNiz[1] = zenskiBrojac;
		return retNiz;
	}
	
	
	public TreeMap<Date, Integer[]>getPodaciZaGrafik(){
		TreeMap<Date, Integer[]> retMap = new TreeMap<Date, Integer[]>();
		
		if (razlikaJeManjaOdDveNedelje(dto.getPocetak(), dto.getKraj())) {
			Date date = Date.from(dto.getKraj().atStartOfDay(ZoneId.systemDefault()).toInstant());
			retMap.put(date, getBrojMuskihIZenskihOdListe(dto.getPocetak(), dto.getKraj()));
			return retMap;
		}
		
		LocalDate krajPetlje = dto.getKraj().plusDays(1);
		for (LocalDate datum = dto.getPocetak(); datum.isBefore(krajPetlje); datum = datum.plusDays(14))
		{
			LocalDate gornjaGranica = datum.plusDays(14);
			if (!gornjaGranica.isBefore(krajPetlje)) {
				break;
			}else {
				Date date = Date.from(gornjaGranica.atStartOfDay(ZoneId.systemDefault()).toInstant());
				retMap.put(date, getBrojMuskihIZenskihOdListe(datum, gornjaGranica));
			}
		}
		return retMap;
	}
	
	private boolean razlikaJeManjaOdDveNedelje(LocalDate pocetak, LocalDate kraj) {
		 long result = ChronoUnit.DAYS.between(pocetak, kraj);
		 if (result < 14) {
			 return true;
		 }return false;
	}
}
