package repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import entity.Dostava;
import entity.Entity;

public class DostavaRepository extends GenericRepository<Dostava>{

	public DostavaRepository(String path) {
		super(path);
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		Dostava dostava = null;
		int id = Integer.parseInt(tokens[0]);
		if (tokens[1].equals("false")) {
			dostava = new Dostava(id, false, LocalDate.parse(tokens[2]));
		}
		else if(tokens[1].equals("true")) {
			if (tokens[3].equals("null")) {
				dostava = new Dostava(id, true, LocalDate.parse(tokens[2]));
			}
			else {
				dostava = new Dostava(id, true, LocalDate.parse(tokens[2]), LocalTime.parse(tokens[3]));
			}
		}
		//this.getEntitetiMap().put(id, dostava);
		this.getEntitetiList().add(dostava);
		return dostava;
	}
	

}
