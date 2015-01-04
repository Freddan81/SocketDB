package no.connectdb.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Draw implements Serializable, HasId{

	@Id
	private Long id;
	
	private Long x;
	private Long y;
	private String color;
	
	public Draw() {
	}

	public Draw(Long x, Long y, String color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Draw [id=" + id + ", x=" + x + ", y=" + y + ", color=" + color
				+ "]";
	}

	public Long getX() {
		return x;
	}

	public Long getY() {
		return y;
	}

	public String getColor() {
		return color;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
