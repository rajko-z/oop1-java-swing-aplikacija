package services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import entity.DatumiPopustaAnaliza;
import entity.DatumiPopustaGrupa;

public class DatumiVazenjaPopustaServis {
	
	DatumiPopustaGrupa datumiPopustaGrupa = DatumiPopustaGrupa.getInstance();
	DatumiPopustaAnaliza datumiPopustaAnaliza = DatumiPopustaAnaliza.getInstance();
	
	public DatumiVazenjaPopustaServis() {
	}

	public void editDatumiAnalize(LocalDate pocetak, LocalDate kraj, List<DayOfWeek> dani) {
		datumiPopustaAnaliza.setData(pocetak, kraj, dani);
	}

	public void editDatumiGrupe(LocalDate pocetak, LocalDate kraj, List<DayOfWeek> dani) {
		datumiPopustaGrupa.setData(pocetak, kraj, dani);
	}
	
	public boolean danasSuAnalizeNaPopustu() {
		LocalDate pocetak = datumiPopustaAnaliza.getPocetak().minusDays(1);
		LocalDate kraj = datumiPopustaAnaliza.getKraj().plusDays(1);
		LocalDate danas = LocalDate.now();
		DayOfWeek danasnjiDan = danas.getDayOfWeek();
		if (danas.isBefore(kraj) & danas.isAfter(pocetak)) {
			if (datumiPopustaAnaliza.getDani().contains(danasnjiDan)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean danasSuGrupeNaPopustu() {
		LocalDate pocetak = datumiPopustaGrupa.getPocetak().minusDays(1);
		LocalDate kraj = datumiPopustaGrupa.getKraj().plusDays(1);
		LocalDate danas = LocalDate.now();
		DayOfWeek danasnjiDan = danas.getDayOfWeek();
		if (danas.isBefore(kraj) & danas.isAfter(pocetak)) {
			if (datumiPopustaGrupa.getDani().contains(danasnjiDan)) {
				return true;
			}
		}
		return false;
	}
}
