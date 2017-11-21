package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
import java.util.ArrayList;
import java.util.List;

public class TextInputUnit {
	public String textInputType;
	public List<TextComponent> components;

	public TextInputUnit() {
		this.textInputType = "MULTI_LINE_TEXT";
		this.components = new ArrayList<TextComponent>();
	}

	public String getTextInputType() {
		return textInputType;
	}

	public void setTextInputType(String textInputType) {
		this.textInputType = textInputType;
	}

	public List<TextComponent> getComponents() {
		return components;
	}

	public void setComponents(List<TextComponent> components) {
		this.components = components;
	}

}
