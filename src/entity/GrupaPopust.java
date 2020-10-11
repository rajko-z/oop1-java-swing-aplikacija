package entity;

public class GrupaPopust extends Entity{
	
	private GrupaAnaliza GrupaAnaliza;
	private int popust;
	
	public GrupaPopust() {
	}
	
	public GrupaPopust(GrupaAnaliza grupaAnaliza, int popust) {
		super();
		GrupaAnaliza = grupaAnaliza;
		this.popust = popust;
	}

	public GrupaAnaliza getGrupaAnaliza() {
		return GrupaAnaliza;
	}
	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	@Override
	public String toFileEntity() {
		return String.format("%-3s| %s", this.getGrupaAnaliza().getId(), this.getPopust());
	}

	@Override
	public String toString() {
		return "GrupaPopust [GrupaAnaliza=" + GrupaAnaliza + ", popust=" + popust + "]";
	}
	
	
	
}
