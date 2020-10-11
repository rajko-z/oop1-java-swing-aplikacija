package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import entity.Nalaz;

public class IzvestajNalazaServis {
	public IzvestajNalazaServis() {
	}
	
	public boolean kreirajIzvestajUFolderuIzvestaji(String dataForFile, Nalaz nalaz) {
		try {
			String filePath = "./izvestaji/";
			filePath += generisiImeFajlaZaNalaz(nalaz);
			PrintWriter pw = new PrintWriter(
								new OutputStreamWriter(
									new FileOutputStream(
										new File(filePath)), "utf-8"));
			pw.print(dataForFile);
			pw.close();
			return true;
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			return false;
		}
	}
	
	
	
	
	public boolean kreirajIzvestajNaKompuKorisnika(File file, String nalazPodaci) {
		try {
			PrintWriter pw = new PrintWriter(
								new OutputStreamWriter(
									new FileOutputStream(file),"utf-8"));
			pw.print(nalazPodaci);
			pw.close();
			return true;
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			return false;
		}
	}
	
	
	
	
	public String generisiImeFajlaZaNalaz(Nalaz nalaz) {
		StringBuilder sb = new StringBuilder();
		sb.append(nalaz.getZahtev().getPacijent().getIme());
		sb.append("_");
		sb.append(nalaz.getZahtev().getPacijent().getPrezime());
		sb.append("_");
		
		LocalDate localDate = LocalDate.now();
		String tokens[] = localDate.toString().split("-");
		
		sb.append(tokens[2]);
		sb.append("_");
		sb.append(tokens[1]);
		sb.append("_");
		sb.append(tokens[0]);
		sb.append("_");
		
		sb.append(String.valueOf(nalaz.getId()));
		sb.append(".txt");
		
		return sb.toString();
	}

	
}
