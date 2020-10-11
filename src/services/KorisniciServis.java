package services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import exceptions.KorisnikNijeNadjen;
import repositories.RepositoryFactory;

public class KorisniciServis {
	RepositoryFactory rp = RepositoryFactory.getInstance();
	PlataServis plataServis = new PlataServis();
	
	public Korisnik getRegistrovanKorisnik(String username, String password) throws KorisnikNijeNadjen{
		for (Korisnik k : rp.getKorisnikRepo().getEntitetiList()) {
			if (k.getUsername().equals(username) & k.getPassword().equals(password)) {
				if (!zaposleniDobioOtkaz(k)) {
					return k;
				}
			}
		}
		throw new KorisnikNijeNadjen("Nije pronadjen korisnik");
	}
	
	private boolean zaposleniDobioOtkaz(Korisnik k) {
		if (k instanceof Laborant) {
			Laborant l = (Laborant)k;
			if (!l.isValidan()) return true;
		}
		if (k instanceof MedicinskiTehničar) {
			MedicinskiTehničar m = (MedicinskiTehničar)k;
			if (!m.isValidan()) return true;
		}
		return false;
	}
	
	
	public Korisnik getById(int id) {
		Korisnik korisnik = (Korisnik) rp.getKorisnikRepo().getEntityByIdList(id);
		return korisnik;
	}
	
	private int getNasumicanBroj(int lower, int higher) {
		int randomNum = ThreadLocalRandom.current().nextInt(lower, higher);
		return randomNum;
	}
	
	
	private boolean lozinkaVecPostoji(String lozinka) {
		for (Korisnik k: rp.getKorisnikRepo().getEntitetiList()) {
			if (k.getPassword() != null) {
				if (k.getPassword().equals(lozinka)){
					return true;
				}
			}
		}
		return false;
	}
	
	public String getJedinstvenaLozinka(String imeKorisnika) {
		String lozinka = imeKorisnika.toLowerCase() + String.valueOf(getNasumicanBroj(111, 999));
		while (lozinkaVecPostoji(lozinka)) {
			lozinka = imeKorisnika.toLowerCase() + String.valueOf(getNasumicanBroj(111, 999));
		}
		return lozinka;
	}
	
	public List<Korisnik>getSviZaposleni(){
		List<Korisnik>retList = new ArrayList<Korisnik>();
		for (Korisnik k: rp.getKorisnikRepo().getEntitetiList()) {
			if (k instanceof Laborant | k instanceof MedicinskiTehničar) {
				retList.add(k);
			}
		}
		return retList;
	}

	public double getIznosPlateUZadatomPerioduSaDanima(Korisnik k, LocalDate pocetak, LocalDate kraj,
			List<DayOfWeek> dani) {
		double retVal = 0;
		LocalDate krajPetlje = kraj.plusDays(1);
		for (LocalDate datum = pocetak; datum.isBefore(krajPetlje); datum = datum.plusDays(1))
		{
			DayOfWeek dan = datum.getDayOfWeek();
			if (dani.contains(dan)) {
				retVal += plataServis.getPlatuZaposlenogZaOdredjenDan(datum, k);
			}
		}
		return retVal;
	}
	
	
}
