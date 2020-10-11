package services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.AnalizaZaObradu;
import entity.CenePodesavanja;
import entity.Dostava;
import entity.GrupaAnaliza;
import entity.PosebnaAnaliza;
import entity.StanjeZahteva;
import entity.Zahtev;
import entity.korisnici.MedicinskiTehničar;
import entity.korisnici.Pacijent;
import repositories.RepositoryFactory;

public class ZahteviServis {

	RepositoryFactory rp = RepositoryFactory.getInstance();
	private NalaziServis nalaziServis = new NalaziServis();

	public ZahteviServis() {

	}

	public Zahtev kreirajZahtevOdMedicinskogTeh(MedicinskiTehničar m, Pacijent p, ArrayList<PosebnaAnaliza> analize, double cena) {
		int zahtevId = rp.getZahtevRepo().generateIdList();
		StanjeZahteva stanjeZahteva = (StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdMap(3);
		Dostava dostava = new Dostava();
		Zahtev zahtev = new Zahtev(zahtevId, p, analize, stanjeZahteva, dostava, m, LocalDate.now(), cena);
		rp.getZahtevRepo().getEntitetiList().add(zahtev);
		nalaziServis.kreirajPrazanNalaz(zahtev);    
		return zahtev;
	}

	public Zahtev kreirajZahtevOdStranePacijenta(ArrayList<PosebnaAnaliza> analize, Pacijent pacijent,
			Dostava dostava, double cena) {
		int id = rp.getZahtevRepo().generateIdList();
		StanjeZahteva stanjeZahteva = (StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdMap(1);
		Zahtev zahtev = new Zahtev(id, pacijent, analize, stanjeZahteva, dostava, new MedicinskiTehničar(),
				LocalDate.now(), cena);
		rp.getZahtevRepo().getEntitetiList().add(zahtev);
		return zahtev;
	}
	
	public List<Zahtev> getZahteviSaKucnomDostavom() {
		List<Zahtev> zahtevi = new ArrayList<Zahtev>();
		for (Zahtev zahtev : rp.getZahtevRepo().getEntitetiList()) {
			if (zahtev.getDostava().isKućnaDostava() & zahtev.getStanjeZahteva().getId() == 1) {
				zahtevi.add(zahtev);
			}
		}
		return zahtevi;
	}

	public Zahtev getZahtevById(int id) {
		for (Zahtev zahtev : rp.getZahtevRepo().getEntitetiList()) {
			if (zahtev.getId() == id) {
				return zahtev;
			}
		}
		return null;
	}

	public void oznaciZahtevNaStanjePreuzimanja(Zahtev zahtev, MedicinskiTehničar mTehničar) {
		StanjeZahteva stanjeZahteva = (StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdMap(2);
		zahtev.setStanjeZahteva(stanjeZahteva);
		zahtev.setMedicinskiTehničar(mTehničar);
	}

	public void oznaciZahtevNaPocetnoStanje(Zahtev zahtev) {
		StanjeZahteva stanjeZahteva = (StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdMap(1);
		zahtev.setStanjeZahteva(stanjeZahteva);
		zahtev.setMedicinskiTehničar(new MedicinskiTehničar());
	}

	public void oznaciZahtevNaStanjeObrade(Zahtev zahtev, MedicinskiTehničar mTehničar) {
		StanjeZahteva stanjeZahteva = (StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdMap(3);
		zahtev.setStanjeZahteva(stanjeZahteva);
		zahtev.setMedicinskiTehničar(mTehničar);
		nalaziServis.kreirajPrazanNalaz(zahtev);
	}

	public List<Zahtev> getZahteviTrenutnihPosetaMediciskogTehnicara(MedicinskiTehničar korisnik) {
		List<Zahtev> zahtevi = new ArrayList<Zahtev>();
		for (Zahtev zahtev : rp.getZahtevRepo().getEntitetiList()) {
			if (zahtev.getMedicinskiTehničar().equals(korisnik) & zahtev.getStanjeZahteva().getId() == 2) {
				zahtevi.add(zahtev);
			}
		}
		return zahtevi;
	}

	public Zahtev getZahtevPacijentaKojiNijeGotov(Pacijent pacijent) {
		for (Zahtev zahtev : rp.getZahtevRepo().getEntitetiList()) {
			if (zahtev.getPacijent().equals(pacijent) & (zahtev.getStanjeZahteva().getId() != 4)) {
				return zahtev;
			}
		}
		return null;
	}
	
	public Zahtev getZahtevPacijentaKojiNijeStavljenUObradu(Pacijent pacijent) {
		for (Zahtev zahtev : rp.getZahtevRepo().getEntitetiList()) {
			int idZahteva = zahtev.getStanjeZahteva().getId();
			if (zahtev.getPacijent().equals(pacijent) & idZahteva != 3 & idZahteva != 4) {
				return zahtev;
			}
		}
		return null;
	}
	
	public boolean zahtevPripadaMedicinaru(MedicinskiTehničar mTehničar, Zahtev zahtev) {
		if (zahtev.getStanjeZahteva().getId() == 2 & zahtev.getMedicinskiTehničar().equals(mTehničar)) {
			return true;
		}
		return false;
	}
	
	public boolean uzorakNijePreuzet(Zahtev zahtev) {
		if (zahtev.getDostava().isKućnaDostava() & zahtev.getStanjeZahteva().getId() == 1) {
			return true;
		}
		return false;
	}
	
	public List<Zahtev>getZahteviUVremenskomPerioduSaDanima(LocalDate pocetak, LocalDate kraj, List<DayOfWeek>dani){
		ArrayList<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev z: rp.getZahtevRepo().getEntitetiList()) {
			
			LocalDate datum = z.getDatumObrade();
			DayOfWeek dan = datum.getDayOfWeek();
			
			if (datum.compareTo(pocetak) >= 0 & datum.compareTo(kraj) <= 0) {
				for (DayOfWeek d: dani) {
					if (dan.equals(d)) {
						retList.add(z);
						break;
					}
				}
			}
		}
		return retList;
	}

	public List<Zahtev> filtrirajPoKolicini(int selektovanuKolicinu, List<Zahtev> zahtevi) {
		List<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev z: zahtevi) {
			if (z.getAnalize().size() > selektovanuKolicinu) {
				retList.add(z);
			}
		}
		return retList;
	}

	public List<Zahtev> filtrirajPoAnalizama(List<GrupaAnaliza> selektovaneAnalize, List<Zahtev> zahtevi) {
		List<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev z: zahtevi) {
			int temp = 0;
			for(GrupaAnaliza grupa: selektovaneAnalize) {
				for(PosebnaAnaliza analiza: z.getAnalize()) {
					if(analiza.getGrupaAnaliza().getId() == grupa.getId()) {
						retList.add(z);
						temp += 1;
						break;
					}
				}
				if (temp == 1) break;
			}
		}
		return retList;
	}

	public List<Zahtev> filtrirajPoStanju(StanjeZahteva selektovanoStanje, List<Zahtev> zahtevi) {
		List<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev z: zahtevi) {
			if (z.getStanjeZahteva().getId() == selektovanoStanje.getId()) {
				retList.add(z);
			}
		}
		return retList;
	}

	public List<Zahtev> filtrirajPoKucnojPoseti(boolean oznacenaJekucnaPoseta, List<Zahtev> zahtevi) {
		List<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev z: zahtevi) {
			if (!oznacenaJekucnaPoseta) {
				if (z.getDostava().isBezZahteva() | !z.getDostava().isKućnaDostava()) {
					retList.add(z);
				}
			}else if (z.getDostava().isKućnaDostava()) {
				retList.add(z);
			}
		}
		return retList;
	}
	
	
	public List<Zahtev> getSviZahteviNaCekanju(){
		List<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev zahtev: rp.getZahtevRepo().getEntitetiList()) {
			if (zahtev.getStanjeZahteva().getId() == 1 |  zahtev.getStanjeZahteva().getId() == 2) {
				retList.add(zahtev);
			}
		}
		return retList;
	}
	
	

	

}
