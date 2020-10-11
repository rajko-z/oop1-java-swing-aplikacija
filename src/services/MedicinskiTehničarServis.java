package services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.GrupaAnaliza;
import entity.StručnaSprema;
import entity.Zahtev;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import repositories.RepositoryFactory;

public class MedicinskiTehničarServis {
	
	RepositoryFactory rp = RepositoryFactory.getInstance();
	KorisniciServis ks = new KorisniciServis();
	ZahteviServis zahteviServis = new ZahteviServis();
	
	public MedicinskiTehničarServis() {
	}
	
	public List<Korisnik> getZaposleniMedicinari(){
		List<Korisnik>retList = new ArrayList<Korisnik>();
		for (MedicinskiTehničar m: rp.getMedicinskiTehničarRepo().getEntitetiList()) {
			if (m.isValidan()) {
				retList.add(m);
			}
		}
		return retList;
	}
	
	public MedicinskiTehničar getById(int id) {
		for(MedicinskiTehničar m : rp.getMedicinskiTehničarRepo().getEntitetiList()) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}
	
	
	public void addMTehničar(String ime, String prezime, StručnaSprema sprema) {
		int id = rp.getKorisnikRepo().generateIdList();
		String username = ime.toLowerCase() + '.' + prezime.toLowerCase();
		String lozinka = ks.getJedinstvenaLozinka(ime);
		MedicinskiTehničar m = new MedicinskiTehničar(id, ime, prezime, username, lozinka, sprema, true);
		rp.getMedicinskiTehničarRepo().getEntitetiList().add(m);
		rp.getKorisnikRepo().getEntitetiList().add(m);
	}
	
	public void edit(MedicinskiTehničar mTehničar, String ime, String prezime, StručnaSprema sprema) {
		mTehničar.setIme(ime);
		mTehničar.setPrezime(prezime);
		mTehničar.setSprema(sprema);
	}
	
	
	public void dajOtkazMTehničaru(int id) {
		MedicinskiTehničar medicinskiTehničar = getById(id);
		medicinskiTehničar.setValidan(false);
	}
	
	
	public int getBrojObradjenihZahtevaUZadatomPerioduSaDanima(MedicinskiTehničar mTehničar, LocalDate pocetak, LocalDate kraj,  List<DayOfWeek>dani) {
		return getZahteviMedicinaraUOdredjenomPerioduSaDanima(mTehničar, pocetak, kraj, dani).size();
	}
	
	private List<Zahtev>getZahteviMedicinaraUOdredjenomPerioduSaDanima(MedicinskiTehničar mTehničar, LocalDate pocetak, LocalDate kraj,  List<DayOfWeek>dani){
		List<Zahtev>retList = new ArrayList<Zahtev>();
		for (Zahtev zahtev: rp.getZahtevRepo().getEntitetiList()) {
			LocalDate datum = zahtev.getDatumObrade();
			DayOfWeek dan = datum.getDayOfWeek();
			if (datum.compareTo(pocetak) >= 0 & datum.compareTo(kraj) <= 0) {
				for (DayOfWeek d: dani) {
					if (dan.equals(d) & zahtev.getMedicinskiTehničar().equals(mTehničar)) {
						retList.add(zahtev);
						break;
					}
				}
			}
		}
		return retList;

	}
	public int getBrojKucnihPosetaUZadatomPerioduSaDanima(MedicinskiTehničar mTehničar, LocalDate pocetak, LocalDate kraj,  List<DayOfWeek>dani) {
		int retVal = 0;
		List<Zahtev>zahtevi = getZahteviMedicinaraUOdredjenomPerioduSaDanima(mTehničar, pocetak, kraj, dani);
		for (Zahtev zahtev: zahtevi) {
			if (zahtev.getDostava().isKućnaDostava()) {
				retVal += 1;
			}
		}
		return retVal;
	}

	
	
}
