package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DTOselektovaniDaniIDatumi {
	
	private LocalDate pocetak;
	private LocalDate kraj;
	private List<DayOfWeek>dani;
	
	public DTOselektovaniDaniIDatumi() {
	}
	
	public DTOselektovaniDaniIDatumi(LocalDate pocetak, LocalDate kraj, List<DayOfWeek> dani) {
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.dani = dani;
	}

	public LocalDate getPocetak() {
		return pocetak;
	}

	public LocalDate getKraj() {
		return kraj;
	}

	public List<DayOfWeek> getDani() {
		return dani;
	}

}
