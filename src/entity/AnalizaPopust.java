package entity;

public class AnalizaPopust extends Entity {

	private PosebnaAnaliza analiza;
	private int popust;

	public AnalizaPopust(PosebnaAnaliza analiza, int popust) {
		this.analiza = analiza;
		this.popust = popust;
	}

	
	public PosebnaAnaliza getAnaliza() {
		return analiza;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-4s| %s", this.analiza.getId(), this.popust);
	}


	@Override
	public String toString() {
		return "AnalizaPopust [analiza=" + analiza + ", popust=" + popust + "]";
	}
	
	
	
}
