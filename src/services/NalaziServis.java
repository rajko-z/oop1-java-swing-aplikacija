package services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import entity.AnalizaZaObradu;
import entity.GrupaAnaliza;
import entity.Nalaz;
import entity.PosebnaAnaliza;
import entity.StanjeZahteva;
import entity.Zahtev;
import entity.korisnici.Laborant;
import entity.korisnici.Pacijent;
import repositories.RepositoryFactory;

public class NalaziServis {
	
	RepositoryFactory rp = RepositoryFactory.getInstance();
	
	public NalaziServis() {
		
	}
	
	public Nalaz getNalazById(int id) {
		for (Nalaz nalaz: rp.getNalazRepo().getEntitetiList()) {
			if (nalaz.getId() == id) {
				return nalaz;
			}
		}
		return null;
	}
	
	
	public Nalaz kreirajPrazanNalaz(Zahtev zahtev) {
		int nalazId = rp.getNalazRepo().generateIdList();
		ArrayList<AnalizaZaObradu> analizeZaObradu = getNovokreiranePrazneAnalizeZaObradu(zahtev.getAnalize());
		Nalaz nalaz = new Nalaz(nalazId, zahtev, analizeZaObradu);
		rp.getNalazRepo().getEntitetiList().add(nalaz);
		return nalaz;
	}
	
	private ArrayList<AnalizaZaObradu> getNovokreiranePrazneAnalizeZaObradu(List<PosebnaAnaliza> analize) {
		ArrayList<AnalizaZaObradu> retList = new ArrayList<AnalizaZaObradu>();
		for (PosebnaAnaliza a: analize) {
			int id = rp.getAnalizaZaObraduRepo().generateIdList();
			AnalizaZaObradu analizaZaObradu = new AnalizaZaObradu(id, a, false, new Laborant());
			rp.getAnalizaZaObraduRepo().getEntitetiList().add(analizaZaObradu);
			retList.add(analizaZaObradu);
		}
		return retList;
	}
	
	public int getBrojObradjenihAnaliza(Nalaz nalaz) {
		int retVal = 0;
		for (AnalizaZaObradu a: nalaz.getAnalizeZaObradu()) {
			if (a.isJesteObradjena()) {
				retVal += 1;
			}
		}
		return retVal;
	}
	
	public int getBrojPreostalihAnaliza(Nalaz nalaz) {
		return nalaz.getBrojAnaliza() - getBrojObradjenihAnaliza(nalaz);
	}
	
	public void obeleziDatumNalazaAkoJeZavrsenIStaviZahtevUObradjen(Nalaz nalaz) {
		if (nalazJeZavrsen(nalaz)) {
			nalaz.setDatumObrade(LocalDate.now());
			nalaz.getZahtev().setStanjeZahteva((StanjeZahteva) rp.getStanjeZahtevaRepo().getEntityByIdMap(4));
		}
	}
	
	public boolean nalazJeZavrsen(Nalaz nalaz) {
		if (getBrojPreostalihAnaliza(nalaz) == 0) {
			return true;
		}
		return false;
	}
	
	public List<Nalaz> getSviNeobradjeniNalazi() {
		ArrayList<Nalaz>nalazi = new ArrayList<Nalaz>();
		for (Nalaz nalaz: rp.getNalazRepo().getEntitetiList()) {
			if (!nalazJeZavrsen(nalaz)) {
				nalazi.add(nalaz);
			}
		}
		return nalazi;
	}
	
	public ArrayList<Nalaz> getNeobradjeniNalaziZaLaboranta(GrupaAnaliza grupaAnaliza){
		ArrayList<Nalaz>nalazi = new ArrayList<Nalaz>();
		for (Nalaz nalaz: rp.getNalazRepo().getEntitetiList()) {
			for (AnalizaZaObradu aZaObradu: nalaz.getAnalizeZaObradu()) {
				if (aZaObradu.getAnaliza().getGrupaAnaliza().getId() == grupaAnaliza.getId() & !aZaObradu.isJesteObradjena()) {
					nalazi.add(nalaz);
					break;
				}
			}
		}
		return nalazi;
	}

	public AnalizaZaObradu getAnalizuZaObraduById(int id) {
		for (AnalizaZaObradu a: rp.getAnalizaZaObraduRepo().getEntitetiList()) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	public boolean laborantNeMozeDaRadiStavkuNalaza(Laborant laborant, AnalizaZaObradu a) {
		for (GrupaAnaliza grupa: laborant.getGrupeAnaliza()) {
			if (grupa.getId() == a.getAnaliza().getGrupaAnaliza().getId()) {
				return false;
			}
		}
		return true;
	}

	public double obradiIvratiVrednostStavkeNalaza(Laborant laborant, AnalizaZaObradu a) {
		double razlika = a.getAnaliza().getGornjaRefVrednost() - a.getAnaliza().getDonjaRefVrednost();
		double gornjaGranica = a.getAnaliza().getGornjaRefVrednost() + razlika;
		double donjaGranica = a.getAnaliza().getDonjaRefVrednost() - razlika;
		if (donjaGranica < 0) {
			donjaGranica = 0.0;
		}
		double randomValue = ThreadLocalRandom.current().nextDouble(donjaGranica, gornjaGranica);
		double retVal = Double.parseDouble(String.format("%.3f", randomValue));
		a.setIzmerenaVrednost(retVal);
		a.setLaborant(laborant);
		a.setJesteObradjena(true);
		
		return retVal;
	}

	public List<Nalaz> getNalaziZaPacijenta(Pacijent pacijent) {
		ArrayList<Nalaz>retLista = new ArrayList<Nalaz>();
		for (Nalaz nalaz: rp.getNalazRepo().getEntitetiList()) {
			if (nalazJeZavrsen(nalaz) & nalaz.getZahtev().getPacijent().equals(pacijent)) {
				retLista.add(nalaz);
			}
		}
		return retLista;
	}
	
	
	public List<Nalaz> getSviZavrseniNalazi() {
		ArrayList<Nalaz>nalazi = new ArrayList<>();
		for (Nalaz nalaz: rp.getNalazRepo().getEntitetiList()) {
			if (nalazJeZavrsen(nalaz)) {
				nalazi.add(nalaz);
			}
		}
		return nalazi;
	}
	
	
	public List<Nalaz>getNalaziUVremenskomPeriodu(LocalDate pocetak, LocalDate kraj){
		ArrayList<Nalaz>retList = new ArrayList<Nalaz>();
		for (Nalaz n: getSviZavrseniNalazi()) {
			LocalDate datum = n.getDatumObrade();
			if (datum.compareTo(pocetak) >= 0 & datum.compareTo(kraj) <= 0) {
				retList.add(n);
			}
		}
		return retList;
	}
	
	
	public List<Nalaz>getNalaziUVremenskomPerioduSaDanima(LocalDate pocetak, LocalDate kraj, List<DayOfWeek>dani){
		ArrayList<Nalaz>retList = new ArrayList<Nalaz>();
		for (Nalaz n: getSviZavrseniNalazi()) {
			
			LocalDate datum = n.getDatumObrade();
			DayOfWeek dan = datum.getDayOfWeek();
			
			if (datum.compareTo(pocetak) >= 0 & datum.compareTo(kraj) <= 0) {
				for (DayOfWeek d: dani) {
					if (dan.equals(d)) {
						retList.add(n);
						break;
					}
				}
			}
		}
		return retList;
	}
	
	public List<Nalaz> filtrirajPoAnalizama(List<GrupaAnaliza> selektovaneAnalize, List<Nalaz> nalazi) {
		List<Nalaz>retList = new ArrayList<Nalaz>();
		for (Nalaz n: nalazi) {
			int temp = 0;
			for(GrupaAnaliza grupa: selektovaneAnalize) {
				for(PosebnaAnaliza analiza: n.getZahtev().getAnalize()) {
					if(analiza.getGrupaAnaliza().getId() == grupa.getId()) {
						retList.add(n);
						temp += 1;
						break;
					}
				}
				if (temp == 1) break;
			}
		}
		return retList;
	}

	public boolean izmerenaStavkaJeVanOpsega(AnalizaZaObradu stavka) {
		if (stavka.getIzmerenaVrednost() > stavka.getAnaliza().getGornjaRefVrednost() |
				stavka.getIzmerenaVrednost() < stavka.getAnaliza().getDonjaRefVrednost()) {
			return true;
		}
		return false;
	}
	

}