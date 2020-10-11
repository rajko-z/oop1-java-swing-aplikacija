package services;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public class DatumiServis {
	
	
	public DatumiServis() {
	}
	
	public ArrayList<LocalTime> getVremena() {
		int podeok =  30 ;  
	    int size = (int) Duration.ofHours( 24 ).toMinutes() / podeok;
	    ArrayList<LocalTime> times = new ArrayList<>();
	    LocalTime time = LocalTime.MIN ;  
	    for( int i = 1 ; i <= size ; i ++ ) {
	        times.add(time) ;
	        time = time.plusMinutes(podeok) ;
	    }
	    return times;
	}
	
	
	public boolean datumJeProslost(LocalDate localDate) {
		LocalDate today = LocalDate.now();
		if (today.isAfter(localDate)) {
			return true;
		}
		return false;
	}
	
	public LocalDate getDatumPoslednjegDanaUMesecuNekeGodine(Year year, Month mesec) {
		int godina = Integer.parseInt(year.toString());
		int brojDanaUMesecu = getBrojDanaUMesecu(year, mesec);
		LocalDate retVal = LocalDate.of(godina, mesec, brojDanaUMesecu);
		return retVal;
	}
	
	public int getBrojDanaUMesecu(Year year, Month mesec) {
		YearMonth ym = YearMonth.of(Integer.parseInt(year.toString()), mesec);
		return ym.lengthOfMonth();
	}
	
	
	public Month[] getMeseci() {
		return new Month[] {Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY,
                Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER,
                Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER};
	}
	public List<DayOfWeek>getDaniUNedelji(){
		return Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
	}

	
}
