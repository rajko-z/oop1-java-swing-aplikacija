package repositories;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.DatumiPopustaGrupa;
import entity.Entity;

public class DatumiPopustaGrupaRepository extends GenericRepository<DatumiPopustaGrupa> {

	public DatumiPopustaGrupaRepository(String path) {
		super(path);
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		DatumiPopustaGrupa datumiPopustaGrupa = DatumiPopustaGrupa.getInstance();

		datumiPopustaGrupa.setData(LocalDate.parse(tokens[0]), LocalDate.parse(tokens[1]), getDaniFromString(tokens[2]));
		this.getEntitetiList().add(datumiPopustaGrupa);
		return datumiPopustaGrupa;

	}

	private List<DayOfWeek> getDaniFromString(String string) {
		List<DayOfWeek> retList = new ArrayList<DayOfWeek>();
		String tokeni[] = string.split(",");
		for (String token : tokeni) {
			retList.add(DayOfWeek.valueOf(token.trim()));
		}
		return retList;
	}

}
