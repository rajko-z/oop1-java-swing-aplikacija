package services;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import entity.CenePodesavanja;
import entity.Plata;
import entity.korisnici.Korisnik;
import entity.korisnici.Laborant;
import entity.korisnici.MedicinskiTehničar;
import repositories.RepositoryFactory;

public class PlataServis {
	
	RepositoryFactory rp = RepositoryFactory.getInstance();
	private List<Plata>plate = rp.getPlateRepo().getEntitetiList();
	DatumiServis datumiServis = new DatumiServis();
	
	public PlataServis() {
	}
	
	public List<Plata>getPlateZaKorisnika(Korisnik korisnik){
		ArrayList<Plata>retList = new ArrayList<Plata>();
		for (Plata plata: this.plate) {
			if (plata.getZaposleni().getId() == korisnik.getId()) {
				retList.add(plata);
			}
		}
		return retList;
	}

	public void delete(Plata plata) {
		this.plate.remove(plata);
	}

	public Plata getPlataById(int id) {
		for (Plata p: this.plate) {
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public void promeniBonusPlate(Plata plata, Double noviBonus) {
		plata.setBonus(noviBonus);
		plata.setUkupnaPlata(noviBonus + plata.getRedovnaPlata());
	}

	public boolean plataZaKorisnikaZaZadatiMesecVecPostoji(Year godina, Month mesec, Korisnik korisnik) {
		for (Plata p: this.plate) {
			if(p.getZaposleni().getId() == korisnik.getId()) {
				if (p.getGodina().equals(godina) & p.getMesec().equals(mesec)) {
					return true;
				}
			}
		}
		return false;
	}

	public double getRedovnaPlataZaKorisnika(Korisnik zaposleni) {
		double koeficijent = 0;
		if (zaposleni instanceof Laborant) {
			koeficijent = ((Laborant)zaposleni).getSprema().getKoeficijent();
		}else {
			koeficijent = ((MedicinskiTehničar)zaposleni).getSprema().getKoeficijent();
		}
		return CenePodesavanja.getInstance().getOsnova() * koeficijent;
	}

	public Plata kreirajNovuPlatu(Year godina, Month mesec, double bonus, double redovnaPlata, Korisnik korisnik) {
		int id = rp.getPlateRepo().generateIdList();
		Plata plata = new Plata(id, godina, mesec, korisnik, redovnaPlata, bonus);
		this.plate.add(plata);
		return plata;
	}
	
	private Plata getPlataPoZadatimKriterijumima(Year godina, Month mesec, Korisnik k) {
		for (Plata plata : this.plate) {
			if (plata.getGodina().equals(godina) & plata.getMesec().equals(mesec) & plata.getZaposleni().equals(k)) {
				return plata;
			}
		}
		return null;
	}
	

	public int getPlatuZaposlenogZaOdredjenDan(LocalDate datum, Korisnik k) {
		Year godina = Year.parse(String.valueOf(datum.getYear()));
		Month mesec = datum.getMonth();
		Plata plata = getPlataPoZadatimKriterijumima(godina, mesec, k);
		if (plata == null) {
			return 0;
		}
		double plataUMesecu = plata.getUkupnaPlata();
		int brojDanaUMesecu = datumiServis.getBrojDanaUMesecu(godina, mesec); 
		
		return (int)Math.round(plataUMesecu / brojDanaUMesecu);
	}
	
	
	public double getProcenatIznosaOdUkupnog(double ukupanIznos, double iznos) {
		if (ukupanIznos == 0) {
			return 0;
		}
		double jedanProcenat = ukupanIznos / 100;
		String format = String.format("%.3f", iznos / jedanProcenat);
		return Double.parseDouble(format);
	}
	
	
	
}
