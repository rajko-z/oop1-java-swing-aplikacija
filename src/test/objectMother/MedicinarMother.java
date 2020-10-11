package test.objectMother;

import entity.StručnaSprema;
import entity.korisnici.MedicinskiTehničar;

public class MedicinarMother {
	public static MedicinskiTehničar getJovana(int id) {
		return new MedicinskiTehničar(id , "Jovana","Jovic", "joca", "joca", new StručnaSprema(), true);
	}
	public static MedicinskiTehničar getSanja(int id) {
		return new MedicinskiTehničar(id , "Sanja","Sanic", "sanja", "sanja", new StručnaSprema(), true);
	}
	public static MedicinskiTehničar getTijana(int id) {
		return new MedicinskiTehničar(id , "Tijana","Tijanic", "tina", "tina", new StručnaSprema(), true);
	}
	public static MedicinskiTehničar getSaOtkazom(int id) {
		return new MedicinskiTehničar(id , "Milana","Milic", "mila", "mila", new StručnaSprema(), false);
	}
}
