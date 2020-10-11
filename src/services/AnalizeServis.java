package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.AnalizaPopust;
import entity.GrupaAnaliza;
import entity.GrupaPopust;
import entity.PosebnaAnaliza;
import repositories.RepositoryFactory;

public class AnalizeServis {

	private RepositoryFactory rp = RepositoryFactory.getInstance();
	
	public AnalizeServis() {
	}

	public ArrayList<String> getValidneGrupeAnalizaNazivi() {
		ArrayList<String> grupeAnalizaNazivi = new ArrayList<String>();
		for (GrupaAnaliza gp : rp.getGrupeAnalizaRepo().getEntitetiList()) {
			if (gp.isValidna()) {
				grupeAnalizaNazivi.add(gp.getNaziv());
			}
		}
		return grupeAnalizaNazivi;
	}

	public PosebnaAnaliza getPosebnaAnalizaById(int id) {
		PosebnaAnaliza pa = (PosebnaAnaliza) rp.getPosebnaAnalizaRepo().getEntityByIdMap(id);
		return pa;
	}

	public GrupaAnaliza getGrupaAnalizeById(int id) {
		GrupaAnaliza grupa = (GrupaAnaliza) rp.getGrupeAnalizaRepo().getEntityByIdMap(id);
		return grupa;
	}

	public List<PosebnaAnaliza> getValidneAnalize() {
		ArrayList<PosebnaAnaliza> retList = new ArrayList<PosebnaAnaliza>();
		for (PosebnaAnaliza analiza : rp.getPosebnaAnalizaRepo().getEntitetiList()) {
			if (analiza.isValidna()) {
				retList.add(analiza);
			}
		}
		return retList;
	}

	public List<PosebnaAnaliza> getObrisaneAnalize() {
		ArrayList<PosebnaAnaliza> retList = new ArrayList<PosebnaAnaliza>();
		for (PosebnaAnaliza analiza : rp.getPosebnaAnalizaRepo().getEntitetiList()) {
			if (!analiza.isValidna()) {
				retList.add(analiza);
			}
		}
		return retList;
	}

	public List<GrupaAnaliza> getValidneGrupeAnaliza() {
		List<GrupaAnaliza> retList = new ArrayList<GrupaAnaliza>();
		for (GrupaAnaliza ga : rp.getGrupeAnalizaRepo().getEntitetiList()) {
			if (ga.isValidna()) {
				retList.add(ga);
			}
		}
		return retList;
	}

	public List<GrupaAnaliza> getObrisaneGrupeAnaliza() {
		List<GrupaAnaliza> retList = new ArrayList<GrupaAnaliza>();
		for (GrupaAnaliza ga : rp.getGrupeAnalizaRepo().getEntitetiList()) {
			if (!ga.isValidna()) {
				retList.add(ga);
			}
		}
		return retList;
	}

	public boolean obrisiAnalizu(PosebnaAnaliza analiza) {
		analiza.setValidna(false);
		return true;
	}

	public boolean obrisiGrupuAnalize(GrupaAnaliza grupa) {
		obrisiSveAnalizeIzGrupe(grupa);
		grupa.setValidna(false);
		return true;
	}

	private void obrisiSveAnalizeIzGrupe(GrupaAnaliza grupa) {
		for (PosebnaAnaliza analiza : rp.getPosebnaAnalizaRepo().getEntitetiList()) {
			if (analiza.getGrupaAnaliza().equals(grupa)) {
				analiza.setValidna(false);
			}
		}
	}

	public void editAnaliza(PosebnaAnaliza analiza, String naziv, GrupaAnaliza selektovanaGrupa, double cena,
			double donjaRef, double gornjaRef, String jedinicaMere) {
		analiza.setNaziv(naziv);
		analiza.setGrupaAnaliza(selektovanaGrupa);
		analiza.setCena(cena);
		analiza.setDonjaRefVrednost(donjaRef);
		analiza.setGornjaRefVrednost(gornjaRef);
		analiza.setJedinicnaVrednost(jedinicaMere);
	}

	public PosebnaAnaliza kreirajNovuAnalizu(String naziv, GrupaAnaliza selektovanaGrupa, double cena, double donjaRef,
			double gornjaRef, String jedinicaMere) {
		int id = rp.getPosebnaAnalizaRepo().generateIdList();
		PosebnaAnaliza analiza = new PosebnaAnaliza(id, naziv, selektovanaGrupa, cena, donjaRef, gornjaRef, jedinicaMere, true);
		rp.getPosebnaAnalizaRepo().getEntitetiList().add(analiza);
		rp.getPosebnaAnalizaRepo().getEntitetiMap().put(id, analiza);
		return analiza;
	}

	public boolean grupaAnalizeSaZadatimImenomVecPostoji(String naziv) {
		for (GrupaAnaliza grupaAnaliza: rp.getGrupeAnalizaRepo().getEntitetiList()) {
			if (grupaAnaliza.getNaziv().equals(naziv)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean analizaSaUnetimImenomVecPostoji(String naziv) {
		for (PosebnaAnaliza posebnaAnaliza: rp.getPosebnaAnalizaRepo().getEntitetiList()) {
			if (posebnaAnaliza.getNaziv().equals(naziv)) {
				return true;
			}
		}
		return false;
	}

	public void editGrupa(GrupaAnaliza grupa, String naziv) {
		grupa.setNaziv(naziv);
	}

	public GrupaAnaliza kreirajNovuGrupu(String naziv) {
		int id = rp.getGrupeAnalizaRepo().generateIdList();
		GrupaAnaliza grupa = new GrupaAnaliza(id, naziv, true);
		rp.getGrupeAnalizaRepo().getEntitetiList().add(grupa);
		rp.getGrupeAnalizaRepo().getEntitetiMap().put(id, grupa);
		return grupa;
	}

	public void vratiAnalizuNazadUPonudu(PosebnaAnaliza analiza) {
		analiza.setValidna(true);
	}

	public void vratiGrupuNazadUPonudu(GrupaAnaliza grupa) {
		for (PosebnaAnaliza analiza : rp.getPosebnaAnalizaRepo().getEntitetiList()) {
			if (analiza.getGrupaAnaliza().equals(grupa)) {
				analiza.setValidna(true);
			}
		}
		grupa.setValidna(true);
	}

	public int getPopustZaAnalizu(PosebnaAnaliza analiza) {
		AnalizaPopust analizaPopust = rp.getAnalizaPopustRepo().getEntitetiMap().get(analiza.getId());
		if (analizaPopust == null) {
			return 0;
		}else {
			return analizaPopust.getPopust();
		}
	}
	public double getCenaSaPopustom(PosebnaAnaliza analiza) {
		int popust = getPopustZaAnalizu(analiza);
		double umanjenik = Math.round((analiza.getCena()/100) * popust);
		return analiza.getCena() - umanjenik;
	}

	public int getPopustZaGrupu(GrupaAnaliza grupa) {
		GrupaPopust grupaPopust = rp.getGrupaPopustRepo().getEntitetiMap().get(grupa.getId());
		if (grupaPopust == null) {
			return 0;
		}else {
			return grupaPopust.getPopust();
		}
	}
	
	public boolean editPopustAnaliza(PosebnaAnaliza analiza, double noviPopust) {
		int popust = (int)noviPopust;
		AnalizaPopust analizaPopust = rp.getAnalizaPopustRepo().getEntitetiMap().get(analiza.getId());
		if (analizaPopust == null) {
			if (popust != 0) {
				AnalizaPopust nova = new AnalizaPopust(analiza, popust);
				rp.getAnalizaPopustRepo().getEntitetiMap().put(analiza.getId(), nova);
			}
		}else {
			if (popust == 0) {
				rp.getAnalizaPopustRepo().getEntitetiMap().remove(analiza.getId());
			}else {
				analizaPopust.setPopust(popust);
			}
		}
		return true;
	}
	
	public boolean editPopustGrupa(GrupaAnaliza grupaAnaliza, double noviPopust) {
		int popust = (int)noviPopust;
		GrupaPopust grupaPopust = rp.getGrupaPopustRepo().getEntitetiMap().get(grupaAnaliza.getId());
		if (grupaPopust == null) {
			if (popust != 0) {
				GrupaPopust nova = new GrupaPopust(grupaAnaliza, popust);
				rp.getGrupaPopustRepo().getEntitetiMap().put(grupaAnaliza.getId(), nova);
			}
		}else {
			if (popust == 0) {
				rp.getGrupaPopustRepo().getEntitetiMap().remove(grupaAnaliza.getId());
			}else {
				grupaPopust.setPopust(popust);
			}
		}
		return true;
	}

	public double analizeGetIznosPopustaOstvarenNadGrupama(List<PosebnaAnaliza> analize) {
		double retVal = 0;
		HashMap<GrupaAnaliza, Double[]>mapa = getZahtevaneGrupeSaIznosima(analize);
		
		for (Map.Entry<GrupaAnaliza, Double[]>par : mapa.entrySet()) {
			if (par.getValue()[0] >= 3) {
				retVal += iznosPopustaZaGrupu(par.getKey(), par.getValue()[1]);
			}
		}
		return retVal;
	}

	private double iznosPopustaZaGrupu(GrupaAnaliza grupa, Double pocetniIznos) {
		int popust = getPopustZaGrupu(grupa);
		return (pocetniIznos / 100) * popust;
	}

	private HashMap<GrupaAnaliza, Double[]> getZahtevaneGrupeSaIznosima(List<PosebnaAnaliza>analize) {
		HashMap<GrupaAnaliza, Double[]>retMap = new HashMap<GrupaAnaliza, Double[]>();
		for (PosebnaAnaliza analiza: analize) {
			GrupaAnaliza grupa = analiza.getGrupaAnaliza();
			if (retMap.containsKey(grupa)) {
				Double[] vrednost = retMap.get(grupa);
				vrednost[0] += 1;
				vrednost[1] += getCenaSaPopustom(analiza);
				retMap.put(grupa, vrednost);
			}else {
				Double[]vrednosti = new Double[3];
				vrednosti[0] = 1.0;
				vrednosti[1] = getCenaSaPopustom(analiza);
				retMap.put(grupa, vrednosti);
			}
		}
		return retMap;
	}

	
}
