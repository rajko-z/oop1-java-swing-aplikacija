package entity;

public abstract class Entity {
	private int id;

	public Entity() {
	}
	
	public Entity(int id) {
		this();
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public abstract String toFileEntity();
}
