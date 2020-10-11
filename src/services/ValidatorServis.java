package services;

public class ValidatorServis {
	
	public ValidatorServis() {
	}
	
	public boolean LBOjeIspravan(String lbo) {
		if (lbo.matches("[0-9]{11}+")) return true;
		return false;
	}
	
	public boolean brojTelefonaJeIspravan(String telefon) {
		if (telefon.matches("[0-9]{8,13}")) return true;
		return false;
	}
	
	public boolean nijeBrojUOdgovarajucemRasponu(double low, double high, String unos) {
		try {
			double vrednost = Double.parseDouble(unos);
			if (vrednost >= low & vrednost <= high) {
				return false;
			}
			return true;
		}catch (Exception e) {
			return true;
		}
	}
	
	
}
