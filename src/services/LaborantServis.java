package services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.AnalizaZaObradu;
import entity.GrupaAnaliza;
import entity.Nalaz;
import entity.StručnaSprema;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import repositories.RepositoryFactory;

public class LaborantServis {
	
	RepositoryFactory rp = RepositoryFactory.getInstance();
	KorisniciServis ks = new KorisniciServis();
	NalaziServis nalaziServis = new NalaziServis();
	
	public LaborantServis() {
	}
	
	public List<Korisnik>getZaposleniLaboranti(){
		List<Korisnik>retList = new ArrayList<Korisnik>();
		for (Laborant l: rp.getLaborantRepo().getEntitetiList()) {
			if (l.isValidan()) {
				retList.add(l);
			}
		}
		return retList;
	}

	public Laborant getById(int id) {
		for(Laborant l : rp.getLaborantRepo().getEntitetiList()) {
			if (l.getId() == id) {
				return l;
			}
		}
		return null;
	}

	public void addLaborant(String ime, String prezime, StručnaSprema sprema,
			ArrayList<GrupaAnaliza> specijalizacije) {
		int id = rp.getKorisnikRepo().generateIdList();
		String username = ime.toLowerCase() + '.' + prezime.toLowerCase();
		String lozinka = ks.getJedinstvenaLozinka(ime);
		Laborant l = new Laborant(id, ime, prezime, username, lozinka, sprema, true, specijalizacije);
		rp.getLaborantRepo().getEntitetiList().add(l);
		rp.getKorisnikRepo().getEntitetiList().add(l);
	}
	
	public void edit(Laborant laborant, String ime, String prezime, StručnaSprema sprema,
			ArrayList<GrupaAnaliza> specijalizacije) {
		laborant.setIme(ime);
		laborant.setPrezime(prezime);
		laborant.setSprema(sprema);
		laborant.setGrupeAnaliza(specijalizacije);
	}

	public void dajOtkazLaborantu(int id) {
		Laborant laborant = getById(id);
		laborant.setValidan(false);
	}

	public int getBrojObradjenihNalazUZadatomPeriouduSaDanima(Laborant laborant, LocalDate pocetak, LocalDate kraj, List<DayOfWeek>dani) {
		int retVal = 0;
		for (Nalaz nalaz: nalaziServis.getNalaziUVremenskomPerioduSaDanima(pocetak, kraj, dani)) {
			for (AnalizaZaObradu stavka: nalaz.getAnalizeZaObradu()) {
				if (stavka.getLaborant().equals(laborant)) {
					retVal += 1;
				}
			}
		}
		return retVal;
	}

	

	
	
}
