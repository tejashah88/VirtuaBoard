package com.tejashah.ws.api.text;
/**
 * MyScript Cloud Sample
 */
import java.util.ArrayList;
import java.util.List;

public class TextInput {
	public TextParameter textParameter;
	public List<TextInputUnit> inputUnits;

	public TextInput() {
		this.textParameter = new TextParameter();
		this.textParameter.textProperties = new TextProperties();
		this.inputUnits = new ArrayList<TextInputUnit>();
		this.inputUnits.add(new TextInputUnit());
	}

	public void addComponent() {
		int lastIndex = this.inputUnits.get(0).components.size() - 1;
		if (this.inputUnits.get(0).components.size() > 0 && this.inputUnits.get(0).components.get(lastIndex).x.size() < 1)
			return;
		this.inputUnits.get(0).components.add(new TextComponent());
	}

	public void addComponentPoint(float x, float y) {
		if (this.inputUnits.get(0).components.size() < 1)
			this.addComponent();
		this.inputUnits.get(0).components.get(this.inputUnits.get(0).components .size() - 1).x.add(x);
		this.inputUnits.get(0).components.get(this.inputUnits.get(0).components	.size() - 1).y.add(y);
	}

	public void clearComponents() {
		this.inputUnits.get(0).components.clear();
	}

	public TextParameter getTextParameter() {
		return textParameter;
	}

	public void setTextParameter(TextParameter textParameter) {
		this.textParameter = textParameter;
	}

	public List<TextInputUnit> getInputUnits() {
		return inputUnits;
	}

	public void setInputUnits(List<TextInputUnit> inputUnits) {
		this.inputUnits = inputUnits;
	}

}
