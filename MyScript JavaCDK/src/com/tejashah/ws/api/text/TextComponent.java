package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
import java.util.ArrayList;
import java.util.List;

public class TextComponent {
	public String type;
	public List<Float> x;
	public List<Float> y;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Float> getX() {
		return x;
	}

	public void setX(List<Float> x) {
		this.x = x;
	}

	public List<Float> getY() {
		return y;
	}

	public void setY(List<Float> y) {
		this.y = y;
	}

	public TextComponent() {
		this.type = "stroke";
		this.x = new ArrayList<Float>();
		this.y = new ArrayList<Float>();
	}
}
