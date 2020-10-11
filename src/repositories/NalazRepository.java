package repositories;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.AnalizaZaObradu;
import entity.Entity;
import entity.Nalaz;
import entity.Zahtev;

public class NalazRepository extends GenericRepository<Nalaz>{

	private ZahtevRepository zahtevRepo;
	private AnalizaZaObraduRepository analizaZaObraduRepo;
	
	public NalazRepository(String path, ZahtevRepository zahtevRepo, AnalizaZaObraduRepository analizaZaObraduRepo) {
		super(path); 
		this.zahtevRepo = zahtevRepo;
		this.analizaZaObraduRepo = analizaZaObraduRepo;
		
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		int id = Integer.parseInt(tokens[0]);
		Zahtev zahtev = (Zahtev) zahtevRepo.getEntityByIdList(Integer.parseInt(tokens[1]));
		ArrayList<AnalizaZaObradu> analizeZaObradu = parseAnalizeZaObraduIntoList(tokens[2]);
		Nalaz nalaz = null;
		
		if (tokens[3].equals("null")) {
			nalaz = new Nalaz(id, zahtev, analizeZaObradu);
		}
		else {
			nalaz = new Nalaz(id, zahtev, analizeZaObradu, LocalDate.parse(tokens[3]));
		}
		this.getEntitetiList().add(nalaz);
		return nalaz;
	}
	
	private ArrayList<AnalizaZaObradu> parseAnalizeZaObraduIntoList(String string) {
		ArrayList<AnalizaZaObradu> analizeZaObradu = new ArrayList<AnalizaZaObradu>();
		String vrednosti[] = string.split(",");
		for(String v: vrednosti) {
			AnalizaZaObradu a  = (AnalizaZaObradu) this.analizaZaObraduRepo.getEntityByIdList(Integer.parseInt(v.trim()));
			analizeZaObradu.add(a);
		}
		return analizeZaObradu;
	}

}
