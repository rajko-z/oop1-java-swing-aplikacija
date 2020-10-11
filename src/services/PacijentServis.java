package services;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.AnalizaZaObradu;
import entity.Nalaz;
import entity.PosebnaAnaliza;
import entity.korisnici.Pacijent;
import repositories.RepositoryFactory;

public class PacijentServis {
	
	RepositoryFactory rp = RepositoryFactory.getInstance();
	
	KorisniciServis ks = new KorisniciServis();
	NalaziServis nalaziServis = new NalaziServis();
	
	public PacijentServis() {
	}
	
	public Pacijent getById(int id) {
		for(Pacijent p: rp.getPacijentRepo().getEntitetiList()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	
	public List<Pacijent> getPacijenti() {
		return RepositoryFactory.getInstance().getPacijentRepo().getEntitetiList();
	}
	
	
	public boolean LBOjeJedinstven(String LBO, Pacijent pacijent) {
		for(Pacijent p: rp.getPacijentRepo().getEntitetiList()) {
			if (p.getLBO().equals(LBO) & !p.equals(pacijent)) {
				return false;
			}
		}
		return true;
	}

	public void edit(Pacijent p, String ime, String prezime, String pol, String lBO, String brojTel, String adresa,
			LocalDate datum) {
		p.setIme(ime);
		p.setPrezime(prezime);
		p.setLBO(lBO);
		p.setPol(pol);
		p.setTelefon(brojTel);
		p.setAdresa(adresa);
		p.setDatumRodjena(datum);
	}

	public Pacijent add(String ime, String prezime, String pol, String LBO, String telefon, String adresa, LocalDate datum) {
		int id = rp.getKorisnikRepo().generateIdList();
		Pacijent p = new Pacijent(id, ime, prezime, LBO, pol, datum, telefon, adresa);
		rp.getPacijentRepo().getEntitetiList().add(p);
		rp.getKorisnikRepo().getEntitetiList().add(p);
		return p;
	}

	public Pacijent getPacijentSaZadatimLBObrojem(String lBO) {
		for (Pacijent p: rp.getPacijentRepo().getEntitetiList()) {
			if (p.getLBO().equals(lBO)) {
				return p;
			}
		}
		return null;
	}

	public Pacijent getPacijentSaZadatimLBObrojemKojiNijeRegistrovan(String lBO) {
		for (Pacijent p: rp.getPacijentRepo().getEntitetiList()) {
			if (p.getLBO().equals(lBO) & p.getUsername() == null) {
				return p;
			}
		}
		return null;
	}

	public void registrujPacijenta(Pacijent pacijent) {
		pacijent.setUsername(pacijent.getIme().toLowerCase() + '.' + pacijent.getPrezime().toLowerCase());
		String lozinka = ks.getJedinstvenaLozinka(pacijent.getIme());
		pacijent.setPassword(lozinka);
	}
	
	public int getBrojNalazaZaPacijenta(Pacijent pacijent, List<Nalaz>nalazi) {
		int retVal = 0;
		for (Nalaz n: nalazi) {
			if (n.getZahtev().getPacijent().equals(pacijent)) {
				retVal += 1;
			}
		}
		return retVal;
	}
	
	public double getUkupnaCenaNalaza(Pacijent pacijent, List<Nalaz>nalazi) {
		double retVal = 0;
		for (Nalaz n: nalazi) {
			if (n.getZahtev().getPacijent().equals(pacijent)) {
				retVal += n.getZahtev().getCena();
			}
		}
		return retVal;
	}
	
	public int getGodine(Pacijent p) {
		long godine = ChronoUnit.YEARS.between(p.getDatumRodjena(), LocalDate.now());
		return (int)godine;
	}
	
	
	public List<PosebnaAnaliza>getAnalizeSaPovisenomVrednostuUposlednjaDvaMerenja(Pacijent pacijent){
		List<PosebnaAnaliza>retList = new ArrayList<PosebnaAnaliza>();
		HashMap<PosebnaAnaliza, Integer>mapa = getAnalizeSaBrojemPojavljivanja(pacijent);
		for (Map.Entry<PosebnaAnaliza, Integer>par : mapa.entrySet()) {
			if (par.getValue() >= 2) {
				retList.add(par.getKey());
			}
		}
		return retList;
	}

	private HashMap<PosebnaAnaliza, Integer> getAnalizeSaBrojemPojavljivanja(Pacijent pacijent) {
		List<Nalaz>nalazi = sortirajNalazePacijentaPoDatumu(nalaziServis.getNalaziZaPacijenta(pacijent));
		HashMap<PosebnaAnaliza, Integer>retMap = new HashMap<PosebnaAnaliza, Integer>();
		for (Nalaz nalaz: nalazi) {
			for (AnalizaZaObradu stavka: nalaz.getAnalizeZaObradu()) {
				if (nalaziServis.izmerenaStavkaJeVanOpsega(stavka)) {
					if (retMap.containsKey(stavka.getAnaliza())) {
						Integer vrednost = retMap.get(stavka.getAnaliza());
						vrednost += 1;
						retMap.put(stavka.getAnaliza(), vrednost);
					}else {
						retMap.put(stavka.getAnaliza(), 1);
					}
				}else {
					if (retMap.containsKey(stavka.getAnaliza())) {
						retMap.remove(stavka.getAnaliza());
					}
				}
			}
		}
		return retMap;
	}

	private List<Nalaz> sortirajNalazePacijentaPoDatumu(List<Nalaz> nalaziZaPacijenta) {
		Collections.sort(nalaziZaPacijenta);
		return nalaziZaPacijenta;
	}
	
	
	
}
