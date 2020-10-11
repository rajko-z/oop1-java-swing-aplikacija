package repositories;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import entity.DatumiPopustaAnaliza;
import entity.Entity;

public class DatumiPopustaAnalizaRepository extends GenericRepository<DatumiPopustaAnaliza>{

	public DatumiPopustaAnalizaRepository(String path) {
		super(path);
	}

	@Override
	protected Entity createEntityAndAddToCollection(String[] tokens) {
		DatumiPopustaAnaliza datumiPopustaAnaliza = DatumiPopustaAnaliza.getInstance();
		
		datumiPopustaAnaliza.setData(LocalDate.parse(tokens[0]), LocalDate.parse(tokens[1]), getDaniFromString(tokens[2]));
		this.getEntitetiList().add(datumiPopustaAnaliza);
		return datumiPopustaAnaliza;
	}
	
	private List<DayOfWeek>getDaniFromString(String string){
		List<DayOfWeek>retList = new ArrayList<DayOfWeek>();
		String tokeni[] = string.split(",");
		for (String token: tokeni) {
			retList.add(DayOfWeek.valueOf(token.trim()));
		}
		return retList;
	}
	

}
