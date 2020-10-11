package services;

import java.time.LocalDate;
import java.time.LocalTime;

import entity.Dostava;
import repositories.RepositoryFactory;

public class DostavaServis {
	
	RepositoryFactory rp = RepositoryFactory.getInstance(); 
	
	public DostavaServis() {
	}
	
	public Dostava kreirajDostavuBezKucnePosete(LocalDate datum) {
		int id = rp.getDostavaRepo().generateIdList(); 
		Dostava dostava = new Dostava(id, false, datum);
		rp.getDostavaRepo().getEntitetiList().add(dostava);
		return dostava;
	}
	
	public Dostava kreirajKucnuDostavuBezVremena(LocalDate datum) {
		int id = rp.getDostavaRepo().generateIdList(); 
		Dostava dostava = new Dostava(id, true, datum);
		rp.getDostavaRepo().getEntitetiList().add(dostava);
		return dostava;
	}
	
	public Dostava kreirajKucnuDostavuSaVremenom(LocalDate datum, LocalTime vreme) {
		int id = rp.getDostavaRepo().generateIdList(); 
		Dostava dostava = new Dostava(id, true, datum, vreme);
		rp.getDostavaRepo().getEntitetiList().add(dostava);
		return dostava;
	}
	
}
